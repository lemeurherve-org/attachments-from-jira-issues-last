import groovy.json.JsonSlurper

 

file = "$WORKSPACE/$PIPELINE_CONFIG"
println "Reading config file: " + file
contents = new File(file).text
def description = new JsonSlurper().parseText(contents)

 

accessKey = null
secretKey = null

 

TEMPLATE_MAP = [

 

        "mvn-build": 'template-sm-ci-build',
        "mvn-release":'template-sm-mvn-release',
        "app-verify": 'template-sm-app-verify',
        "code-deploy-war": 'template-sm-app-deploy',
        "code-deploy-war-wp": 'wp-app-deploy-DEV',

 

        "config-checker": 'template-sm-config-checker',
        "resource-checker": 'template-sm-resource-checker',

 

        "db migration":'sm-db-template-job',
        "gate": 'template-sm-gate',

 

        "join": 'dsl-template-join',

 

        "dummy": 'dsl-template-dummy'
]

 

//jobs = []
description.jobs.each {
    def config = it

 


    config = injectKeysIntoConfig(config)
    config = injectBlockOnIntoConfig(config)

 

    if (!TEMPLATE_MAP.containsKey(config.type)) {
        throw new Exception("Unknown job type: $config.type")
    }

 

    template = TEMPLATE_MAP.get(config.type)

 

    if (config.type.toLowerCase().contains('mvn-')) {
        job = MavenJobBuilder(config, template)
    } else {
        job = JobBuilder(config, template)
    }
    job.withDownstreamJob()
            .withBlockOn()
//            .withSvnUrl()
            .withBuildTrigger()
            .withMaven()
            .build()
  
  
}

 

description.views.each {
    def config = it
    switch(config.type) {
        case "view-build-pipeline":
            viewBuildPipeline(config)
            break
        default:
            throw new Exception("Unknown view type: $config.type")
    }
}

 

 

/*
    Configuration setup
 */
def injectKeysIntoConfig(config) {

 

    switch(config.environment) {
        case "dev":
            iamRoleArn = "$jenkins_dev_iamrole"
            externalId = "$jenkins_aws_external_id"
            break
       

 

        case "dev2":
            iamRoleArn = "$jenkins_dev_iamrole"
            externalId = "$jenkins_aws_external_id"
            break

 

        default:
            throw new Exception("Unknown environment type: $config.environment")
            break
    }

 

    config.accessKey = accessKey
    config.secretKey = secretKey
    config.iamRoleArn = iamRoleArn
    config.externalId = externalId
    return config
}

 

def injectBlockOnIntoConfig(config) {
    blockOn = config.blockOn
    if (blockOn == null) {
        config.blockOn = []
    }
    return config
}

 

/*
    Job creation
 */
def JobBuilder(config, template) {
    _job = job(config.name) {
        using(template)
    }
    config._job = _job
    return new _JobBuilder(config)
}

 

def MavenJobBuilder(config, template) {
    _job = mavenJob(config.name) {
        using(template)
    }
    config._job = _job
    return new _JobBuilder(config)
}

 

public class _JobBuilder {

 

    def job
    def config
    def envVars

 

    _JobBuilder(config) {
        this.config = config
        this.job = config._job

 

        this.envVars = [:]

 

        withEnvVar('app_env', this.config.environment)

 

        // add some custom setup to specific jobs
        // TODO this should really be improved... I'm not sure how though.
        switch(config.type) {
            case "ui-build":
            case "ui-release":
            case "ui-deploy":
                withEnvVar('Application', 'data-manager-ui')
                withEnvVar('Version', '$ui_version')
                withEnvVar('Environment', '$app_env')
                break

 

            case "jssdk-build":
            case "jssdk-release":
            case "jssdk-deploy":
                withEnvVar('Application', 'dhap-dm-jssdk')
                withEnvVar('Version', '$jssdk_version')
                withEnvVar('Environment', '$app_env')
                break
        }
    }

 

    def build() {
        def props = new Properties()
        if (this.job.node?.properties[0]?.'EnvInjectJobProperty'[0]) {
            def templateVars = this.job.node.properties[0].'EnvInjectJobProperty'[0].info[0].propertiesContent[0].value().get(0)
            props.load(new StringReader(templateVars))
        }

 

        this.envVars.each { key, value ->
            props.setProperty(key, value)
        }

 

        this.job.with {
            environmentVariables {
                envs(props)
            }
        }
    }

 

    def withDownstreamJob() {
        this.job.with {
            publishers {
                if (this.config.nextJob) {
                    downstream(this.config.nextJob)
                } else if (this.config.nextParameterizedJob) {
                    downstreamParameterized {
                        trigger(this.config.nextParameterizedJob.name) {
                            currentBuild()
                            if (this.config.nextParameterizedJob.parameterFile) {
                                propertiesFile(this.config.nextParameterizedJob.parameterFile, true)
                            }
                            if (this.config.nextParameterizedJob.predefinedParameters) {
                                this.config.nextParameterizedJob.predefinedParameters.each {
                                    key, value ->
                                        predefinedProp(key, value)
                                }
                            }
                        }
                    }
                } else if (this.config.nextManualJob) {
                    buildPipelineTrigger(this.config.nextManualJob) {
                        parameters {
                            currentBuild()
                        }
                    }
                }
            }
        }
        return this
    }

 

    def withBlockOn() {
        this.job.with {
            if (!this.config.blockOn.empty) {
                blockOn(this.config.blockOn)
            }
        }
        return this
    }

 

 

    def withBuildTrigger() {
        this.job.with {
            if (this.config.withTrigger) {
                triggers {
                    scm("H/5 * * * *")
                }
            }
        }
        return this
    }

 

    def withMaven() {
        this.job.with {
            if (this.config.maven) {
                if (this.config.maven.version) {
                    mavenInstallation(this.config.maven.version)
                }
                if (this.config.maven.pom) {
                    rootPOM(this.config.maven.pom)
                }
                if (this.config.maven.goals) {
                    goals(this.config.maven.goals)
                }
                if (this.config.maven.opts) {
                    mavenOpts(this.config.maven.opts)
                }
            }
        }
        return this
    }

 

 

    def withEnvVar(name, value) {
        this.envVars.put(name, value)
        return this
    }

 

 

 

}
  def viewBuildPipeline(config) {
    buildPipelineView(config.name) {
        selectedJob(config.firstJob)
        alwaysAllowManualTrigger()
        showPipelineParameters()
        showPipelineParametersInHeaders()
        displayedBuilds(config.displayedJobs)
        showPipelineDefinitionHeader()
    }
}

 

 

/*
    Specific View types
 */