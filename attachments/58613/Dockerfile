FROM jenkins/jenkins:2.306
USER root
RUN apt-get update && apt-get install -y apt-transport-https  ca-certificates  curl  gnupg  lsb-release && \
    curl -fsSL https://download.docker.com/linux/debian/gpg | gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg && \
    echo "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian $(lsb_release -cs) stable" | tee /etc/apt/sources.list.d/docker.list && \
    apt-get update && apt-get install -y "docker-ce-cli=5:18.09.9~3-0~debian-buster" && groupadd -g 1001 docker && \
    usermod -a -G docker jenkins
USER jenkins
ENV JENKINS_UC_DOWNLOAD 'http://ftp-nyc.osuosl.org/pub/jenkins'
ENV CASC_JENKINS_CONFIG /opt/attenti/docker/jenkins-attenti/configuration.yaml
# See https://github.com/jenkinsci/plugin-installation-manager-tool#plugin-input-format
RUN jenkins-plugin-cli --plugins ace-editor:1.1 \
    ant:475.vf34069fef73c \
    antisamy-markup-formatter:2.7 \
    apache-httpcomponents-client-4-api:4.5.13-1.0 \
    authentication-tokens:1.4 \
    bootstrap4-api:4.6.0-5 \
    bootstrap5-api:5.1.3-6 \
    bouncycastle-api:2.25 \
    branch-api:2.1046.v0ca_37783ecc5 \
    build-timeout:1.20 \
    caffeine-api:2.9.3-65.v6a_47d0f4d1fe \
    checks-api:1.7.4 \
    cloudbees-folder:6.722.v8165b_a_cf25e9 \
    command-launcher:81.v9c2cb_cb_db_392 \
    configuration-as-code:1512.vb_79d418d5fc8 \
    credentials:2.6.1.1 \
    credentials-binding:1.27.1 \
    display-url-api:2.3.5 \
    docker-commons:1.19 \
    docker-workflow:1.28 \
    durable-task:496.va67c6f9eefa7 \
    echarts-api:5.3.2-1 \
    email-ext:2.89 \
    file-parameters:205.vf6ce13b_e5dee \
    font-awesome-api:6.0.0-1 \
    git:4.11.4 \
    git-client:3.11.1 \
    git-server:1.11 \
    github:1.34.3.1 \
    github-api:1.303-400.v35c2d8258028 \
    github-branch-source:2.11.4 \
    gradle:1.39.4 \
    handlebars:3.0.8 \
    jackson2-api:2.13.3-285.vc03c0256d517 \
    javax-activation-api:1.2.0-3 \
    javax-mail-api:1.6.2-6 \
    jaxb:2.3.6-1 \
    jdk-tool:1.5 \
    jjwt-api:0.11.5-77.v646c772fddb_0 \
    jquery3-api:3.6.0-3 \
    jsch:0.1.55.2 \
    junit:1.54.2 \
    ldap:2.9 \
    lockable-resources:2.16 \
    mailer:414.vcc4c33714601 \
    matrix-auth:3.1.5 \
    matrix-project:772.v494f19991984 \
    mina-sshd-api-common:2.8.0-30.vf9df64641cb_d \
    mina-sshd-api-core:2.8.0-30.vf9df64641cb_d \
    momentjs:1.1.1 \
    okhttp-api:4.9.3-105.vb96869f8ac3a \
    pam-auth:1.8 \
    pipeline-build-step:2.18 \
    pipeline-github-lib:38.v445716ea_edda_ \
    pipeline-graph-analysis:195.v5812d95a_a_2f9 \
    pipeline-groovy-lib:612.v84da_9c54906d \
    pipeline-input-step:449.v77f0e8b_845c4 \
    pipeline-milestone-step:101.vd572fef9d926 \
    pipeline-model-api:1.9.3 \
    pipeline-model-definition:1.9.3 \
    pipeline-model-extensions:1.9.3 \
    pipeline-rest-api:2.24 \
    pipeline-stage-step:293.v200037eefcd5 \
    pipeline-stage-tags-metadata:1.9.3 \
    pipeline-stage-view:2.24 \
    pipeline-utility-steps:2.13.0 \
    plain-credentials:1.8 \
    plugin-util-api:2.16.0 \
    popper-api:1.16.1-3 \
    popper2-api:2.11.5-1 \
    rebuild:1.34 \
    resource-disposer:0.19 \
    scm-api:608.vfa_f971c5a_a_e9 \
    script-security:1138.v8e727069a_025 \
    snakeyaml-api:1.30.2-76.vc104f7ce9870 \
    ssh-credentials:277.v95c2fec1c047 \
    ssh-slaves:1.806.v2253cedd3295 \
    sshd:3.242.va_db_9da_b_26a_c3 \
    structs:324.va_f5d6774f3a_d \
    timestamper:1.18 \
    token-macro:308.v4f2b_ed62b_b_16 \
    trilead-api:1.0.13 \
    uno-choice:2.6.1 \
    variant:1.4 \
    workflow-aggregator:590.v6a_d052e5a_a_b_5 \
    workflow-api:1153.vb_912c0e47fb_a_ \
    workflow-basic-steps:2.24 \
    workflow-cps:2660.vb_c0412dc4e6d \
    workflow-cps-global-lib:588.v576c103a_ff86 \
    workflow-durable-task-step:1139.v252a_e12e8463 \
    workflow-job:1145.v7f2433caa07f \
    workflow-multibranch:716.vc692a_e52371b_ \
    workflow-scm-step:400.v6b_89a_1317c9a_ \
    workflow-step-api:639.v6eca_cd8c04a_a_ \
    workflow-support:813.vb_d7c3d2984a_0 \
    ws-cleanup:0.42 && \
    echo 2.0 > /usr/share/jenkins/ref/jenkins.install.UpgradeWizard.state