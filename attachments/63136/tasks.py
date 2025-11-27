import json
import logging
import os
import shutil
import tempfile
from typing import Dict, List, Tuple

import boto3
import jenkins
from aws_requests_auth.aws_auth import AWSRequestsAuth
from cordite_askec2.cordite_askec2_cli import flattenOutput
from cordite_common_python.els_utils import config
from cordite_common_python.els_utils.elsloan import Ec2LoanService, LoanRecommendationRequest

from ec2_cordite_jarvvis.agent.utils import (
    ask_ec2_output_to_json,
    emit_jarvvis_ask_ec2_metric,
    get_askec2_droplet_schema,
    get_askec2_server_schema,
    get_celery_app,
    get_droplet_finder,
    get_key_pair,
    get_proxy,
    loan_to_json,
    punch_heartbeat,
    run_cmd,
)
from ec2_cordite_jarvvis.vision import JARVVIS_ACCOUNT_ID, PVRE_PROD_HOST
from ec2_cordite_jarvvis.vision.qual_lambda.qual_const import QUAL_EXECTR_JENKINS_ODIN_MS
from ec2_cordite_jarvvis.vision.utils.instance_query_utils import ask_ec2 as query_instances
from ec2_cordite_jarvvis.vision.utils.pvre_utils import (
    compute_snapshot,
    download_chunks,
    parse_pvre_report,
)

logger = logging.getLogger()
logger.setLevel(logging.INFO)


app = get_celery_app("tasks", "7200")

##########
# ask ec2
##########


@app.task(bind=True)
def query_server(self, region, ip):
    droplet_finder = get_droplet_finder(region)
    describe_output_ips = droplet_finder.findServersByIp(
        [ip], property_list=get_askec2_server_schema()
    )
    emit_jarvvis_ask_ec2_metric(region=region)
    return ask_ec2_output_to_json(describe_output_ips)


@app.task(bind=True)
def query_droplet(self, region, ip):
    droplet_finder = get_droplet_finder(region)
    describe_output_ips = droplet_finder.findDropletsByIp(
        [ip], property_list=get_askec2_droplet_schema()
    )
    emit_jarvvis_ask_ec2_metric(region=region)
    return ask_ec2_output_to_json(describe_output_ips)


@app.task(bind=True)
def query_droplets(self, region, stack, usage="testing"):
    droplet_finder = get_droplet_finder(region)
    droplet_ips_by_stack_container = droplet_finder.findDropletsByStack(usage=usage, stack=stack)
    output, index, rel = [], 0, None
    emit_jarvvis_ask_ec2_metric(region=region)
    return flattenOutput(droplet_ips_by_stack_container.resources_container, output, index, rel)


##########
# els
##########


@app.task(bind=True)
def els_create(self, *args, **kwargs):
    """create loan from els service

    Args:
        region (str): region name
        stack (str): stack name
        usage (str): usage name
        count (int, optional): number of droplet, defaults to 1.
        zone (str, optional): zone name
        instance_size (str, optional): instance size
        host_type (str, optional): host type

    Returns:
        [list]: list of loan id(s)
    """
    logger.info(f"Got args: {args}, kwargs: {kwargs}")
    region = kwargs["region"]
    stack = kwargs["stack"]
    usage = kwargs["usage"]
    count = kwargs.get("count", 1)
    zone = None if kwargs.get("zone") == "null" else kwargs.get("zone")
    instance_size = None if kwargs.get("instance_size") == "null" else kwargs.get("instance_size")
    host_type = None if kwargs.get("host_type") == "null" else kwargs.get("host_type")
    loan_recommendation_requests = []
    loan_recommendation_requests.append(
        LoanRecommendationRequest(instance_size, host_type, zone, count)
    )
    els = Ec2LoanService(region, config.opts.material_set, stack, usage)
    recommendations = els.get_loan_recommendation(loan_recommendation_requests)
    return els.create_loans(recommendations)


@app.task(bind=True)
def els_get_all(self, *args, **kwargs):
    logger.info(f"Got args: {args}, kwargs: {kwargs}")
    # default to pdx to query loans
    region = kwargs.get("region", "pdx")
    stack = kwargs.get("stack", "prodction")
    usage = kwargs.get("usage", "testing")
    limit = int(kwargs.get("limit", "1000"))
    offset = int(kwargs.get("offset", "0"))

    # construct loan states
    ACTIVE_STATES = ["ACTIVE", "CREATED"]
    OTHER_STATES = ["FAILED", "RECLAIMED", "RETURNED", "PENDING_ASSET", "EXPIRED"]
    if not kwargs.get("loan_states"):
        loan_states = ACTIVE_STATES
        if kwargs.get("all_states"):
            loan_states.extend(OTHER_STATES)
    else:
        loan_states = kwargs["loan_states"]

    # default to query group account
    if kwargs.get("user_id") is None or kwargs["user_id"] == "null":
        user_id = config.opts.user_id
        user_type = config.opts.user_type
    else:
        user_id = kwargs["user_id"]
        user_type = kwargs["user_type"]

    els = Ec2LoanService(region, config.opts.material_set, stack, usage)
    logger.info(f"Run query for {user_id}:{user_type}, state {loan_states} ...")
    current_loans = els.describe_loans_by_account(user_id, user_type, loan_states, filter=False)
    return [loan_to_json(loan) for loan in current_loans][offset : offset + limit]


@app.task(bind=True)
def els_get_one(self, loan_id, **kwargs):
    logger.info(f"Query loan state, id: {loan_id}")
    # default to pdx to query prodction testing loans
    region = kwargs.get("region", "pdx")
    stack = kwargs.get("stack", "prodction")
    usage = kwargs.get("usage", "testing")
    els = Ec2LoanService(region, config.opts.material_set, stack, usage)
    loan = els.describe_loan_by_id(loan_id=loan_id)
    return [loan_to_json(loan)]


##########
# ldap
##########


@app.task(bind=True)
def is_member_of_group(self, username: str, group_name: str) -> bool:
    """check membership for giving user, return True if it is part of the group"""
    # LDAP server is not accessible in the NAWS
    from bender.aldap.client import Ldap

    ldap_client = Ldap()
    return ldap_client.is_member_of_posix_group(
        username, group_name
    ) or ldap_client.is_member_of_ldap_group(username, group_name)


@app.task(bind=True)
def search_login(self, login: str) -> bool:
    """search for login, return False this login doesn't exist"""
    # LDAP server is not accessible in the NAWS
    from bender.aldap.client import LDAP_GROUP_BASE, POSIX_GROUP_BASE, Ldap

    ldap_client = Ldap()
    if ldap_client.search_login(login):
        return True
    if ldap_client.search(f"cn={login}", base=LDAP_GROUP_BASE, onlyone=False):
        return True
    if ldap_client.search(f"cn={login}", base=POSIX_GROUP_BASE, onlyone=False):
        return True
    return False


############
# mjollnir
############


@app.task(bind=True)
def mjollnir_declinker(self, region, ip):
    mj_bin = "/apollo/env/Mjollnir/bin/mjollnir"
    global_mj_cfg = os.path.join(os.path.dirname(os.path.realpath(__file__)), "mjollnir.cfg")
    with tempfile.TemporaryDirectory() as td:
        local_mj_cfg = os.path.join(td, "mjollnir.cfg")
        shutil.copy2(global_mj_cfg, local_mj_cfg)
        cmd = f"{mj_bin} -f {local_mj_cfg} -r {region} declinker terminate {ip}"
        logger.info(f"running cmd {cmd}")
        return run_cmd(cmd.split())


############
# heartbeat
############


@app.task(bind=True)
def heartbeat(self):
    punch_heartbeat()
    return True


########################
# cordite team account
########################


@app.task(bind=True)
def scout_sanity_check(self, jenkins_server_url: str, scout_arn: str) -> Tuple[bool, List]:
    """
    scout_sanity_check verifis if scout is configured in the test account or not
    """
    host = get_proxy(jenkins_server_url)
    # arn:aws:outposts:us-west-2:013728493316:outpost/op-026a0153d8d842a80
    scout_id = scout_arn.split("/")[-1]
    cmd = f"aws ec2 describe-subnets --region us-west-2 | jq '.Subnets[] | select(.OutpostArn | strings | contains(\"{scout_id}\"))'"
    host.cmd(cmd)
    for line in host.stdout:
        if scout_id in line:
            return True, host.stdout
    return False, host.stderr


@app.task(bind=True)
def terminate_instance(self, itar, instance_id, dry_run):
    # RIP is not avliable in lambda
    from rip_python_helper import RIPHelper

    access_id, secret_key = get_key_pair(QUAL_EXECTR_JENKINS_ODIN_MS)
    region = RIPHelper.get_region(itar).region_name
    session = boto3.session.Session(
        aws_access_key_id=access_id, aws_secret_access_key=secret_key, region_name=region
    )
    client = session.client("ec2")

    try:
        return client.terminate_instances(InstanceIds=[instance_id], DryRun=dry_run)
    except Exception as e:
        if not "Request would have succeeded, but DryRun flag is set" in str(e):
            raise
        return {
            "TerminatingInstances": [
                {"CurrentState": {}, "InstanceId": instance_id, "PreviousState": {}},
            ]
        }


#################
# longborecli
#################


# TODO: consolidate this function with get_longbore_keg
@app.task(bind=True)
def get_longbore_pipeline_metadata(
    self, jenkins_server_url: str, keg_type: str, app_name: str
) -> Tuple[bool, Dict]:
    longbore_pipeline_map = {
        "pre-gamma": {
            "default": {"pipeline": "CorditeRelease-GP", "stage": "Alpha"},
            "Carbon": {"pipeline": "CBPKegsDeployment", "stage": "Beta"},
        },
        "gamma": {"default": {"pipeline": "EC2NitroDeployment", "stage": "Gamma"}},
        "prod": {"default": {"pipeline": "EC2NitroProdDeployment", "stage": "Prod"}},
    }
    pipeline_cfg = (
        longbore_pipeline_map[keg_type].get(app_name) or longbore_pipeline_map[keg_type]["default"]
    )
    pipeline, stage = pipeline_cfg["pipeline"], pipeline_cfg["stage"]
    cmd = f"/apollo/env/EC2LongBoreTools/bin/longborecli --profile ~/.aws/credentials \
        --role EC2CorditeJarvvis metadata download -p {pipeline} -s {stage} --active"
    host = get_proxy(jenkins_server_url)
    host.cmd(cmd)
    if host.exit_status == 0:
        try:
            return True, json.loads("\n".join(host.stdout))
        except Exception as e:
            msg = f"fail to parse stdout, error: {e}"
            logger.warning(msg)
            return False, {"error": msg}
    msg = f"fail to get pipeline manifest: {host.stderr}"
    logger.warning(msg)
    return False, {"error": msg}


@app.task(bind=True)
def get_longbore_keg(self, jenkins_server_url: str, pipeline: str, stage: str) -> Tuple[bool, Dict]:
    cmd = f"/apollo/env/EC2LongBoreTools/bin/longborecli --profile ~/.aws/credentials \
        --role EC2CorditeJarvvis metadata download -p {pipeline} -s {stage} --active"
    host = get_proxy(jenkins_server_url)
    host.cmd(cmd)
    logger.info(f"{host} {type(host)}")
    if host.exit_status == 0:
        try:
            return True, json.loads("\n".join(host.stdout))
        except Exception as e:
            msg = f"fail to parse stdout, error: {e}"
            logger.warning(msg)
            return False, {"error": msg}
    msg = f"fail to get pipeline manifest: {host.stderr}"
    logger.warning(msg)
    return False, {"error": msg}


#################
# jenkins
#################


# https://tiny.amazon.com/127wy3dqn/stacques5121jenk
os.environ.setdefault("PYTHONHTTPSVERIFY", "0")
os.environ.setdefault("MUTUAL_AUTHENTICATION", "3")


@app.task(bind=True, rate_limit="50/s")
def jenkins_upsert_job(self, jenkins_server_url, job_name, job_config) -> Tuple[bool, str]:
    try:
        _, password = get_key_pair(QUAL_EXECTR_JENKINS_ODIN_MS)
        jenkins_server = jenkins.Jenkins(
            jenkins_server_url, username="svc-cordite", password=password
        )
        if jenkins_server.job_exists(job_name):
            jenkins_server.reconfig_job(job_name, job_config)
            msg = f"updated existing job {job_name}"
        else:
            jenkins_server.create_job(job_name, job_config)
            msg = f"created job {job_name}"
        logger.info(msg)
        return True, msg
    except Exception as e:
        return False, f"failed to upsert job {job_name}, error {e}"


@app.task(bind=True, rate_limit="50/s")
def jenkins_delete_job(self, jenkins_server_url, job_name) -> Tuple[bool, str]:
    try:
        _, password = get_key_pair(QUAL_EXECTR_JENKINS_ODIN_MS)
        jenkins_server = jenkins.Jenkins(
            jenkins_server_url, username="svc-cordite", password=password
        )
        if jenkins_server.job_exists(job_name):
            jenkins_server.delete_job(job_name)
            msg = f"deleted job {job_name}"
        else:
            msg = f"receive task to delete {job_name}, but job does not exist"
        logger.info(msg)
        return True, msg
    except Exception as e:
        return False, f"failed to delete job {job_name}, error {e}"


#################
# pvre
#################


def get_aws_request_auth():
    # pvre only allow jarvvis role EC2CorditeDev
    access_id, secret_key = get_key_pair(
        f"com.amazon.credentials.isengard.{JARVVIS_ACCOUNT_ID}.user/jarvvis"
    )
    session = boto3.Session(aws_access_key_id=access_id, aws_secret_access_key=secret_key)
    sts_client = session.client("sts")
    assumed_role = sts_client.assume_role(
        RoleArn=f"arn:aws:iam::{JARVVIS_ACCOUNT_ID}:role/EC2CorditeDev",
        RoleSessionName="JarvvisInstanceCollector",
    )
    credentials = assumed_role["Credentials"]
    return AWSRequestsAuth(
        aws_access_key=credentials["AccessKeyId"],
        aws_secret_access_key=credentials["SecretAccessKey"],
        aws_token=credentials["SessionToken"],
        aws_host=PVRE_PROD_HOST,
        aws_region="us-east-1",
        aws_service="execute-api",
    )


@app.task(bind=True)
def query_pvre(self):
    auth = get_aws_request_auth()
    logger.info("compute snapshot ...")
    query_id, total_chunks = compute_snapshot(auth)
    pvre_report = download_chunks(auth, query_id, total_chunks)
    region_to_instances_map = parse_pvre_report(results=pvre_report, running_only=True)
    # emit to ask ec2 metric for giving region
    for region in region_to_instances_map:
        emit_jarvvis_ask_ec2_metric(region=region)
    logger.info("query instances ...")
    return query_instances(
        filename=None, running_only=True, instance_id_map=region_to_instances_map
    )
