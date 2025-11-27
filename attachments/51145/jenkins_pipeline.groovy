// Project parameters
def workDir = "${JOB_BASE_NAME}"
def serviceName = "myApp"
def clusterName = "test"
def isSonarRequired = "true"

//AWS parameters
def aws_account = "${AWS_ACCOUNT}"
def aws_region = "${AWS_REGION}"
def awsBaseUrl = "${aws_account}.dkr.ecr.${aws_region}.amazonaws.com"
def awsAccountUrl = "https://${awsBaseUrl}"
def env = "dev"

// Git parameters
def gitCredential = "${GIT_CREDENTIAL}"
def gitBranch ="${DEV_TYPE_BRANCH}"
def gitUrl ="${GIT_PROJECT_URL}"

//Docker parameters
def dockerImage = "${awsBaseUrl}/my-gradle-alpine"

// Sonar Parameters
def sonarCredentialId = "${SONAR_CREDENTIAL_ID}"
def sonarSettingsXML = "/jenkins/.m2/settings.xml"
def sonarProjectkey = "$env-$clusterName-$serviceName"
def sonarProjectName =  "$env-$clusterName-$serviceName"

pipeline{
   agent {
        label 'master'
    }
    options{
        timestamps()
    }
    stages{
        stage('aws login && pull image'){
            steps{
                cleanWs()
                withDockerRegistry(credentialsId: "ecr:${aws_region}:aws_${env}_account", url: awsAccountUrl ) {
                    sh "docker pull ${dockerImage}:latest"
                }
            }
        }
        stage('application'){
            agent {
                docker {
                    image "${dockerImage}"
                    args "-v $M2_HOME/:/jenkins/.m2/"
                }
            }
            stages{
                stage('checkout SCM'){
                    steps{
                        script{
                            dir(workDir){
                                println "Stage: ${STAGE_NAME}"
                                cleanWs()
                                checkout([$class: 'GitSCM', branches: [[name: gitBranch ]], doGenerateSubmoduleConfigurations: false, userRemoteConfigs: [[credentialsId: gitCredential, url: gitUrl ]]])
                            }
                        }
                    }
                }
                stage('Execute build'){
                    steps{
                        script{
                            println "Stage: ${STAGE_NAME}"
                            dir(workDir){
                                sh "./gradlew clean build --console=plain --no-daemon -PmavenSettingsPath=${sonarSettingsXML}"
                            }
                        }
                    }
                }
                stage("Execute Sonar Qube"){
                    when{
                        expression{
                            isSonarRequired.equals("true")
                        }
                    }
                    steps{
                        timestamps{
                            script{
                                println "Stage: ${STAGE_NAME}"
                                dir(workDir){
                                    withCredentials([string(credentialsId: 'accesskey_sonar', variable: 'secret')]) {
                                        sh "./gradlew sonarqube --debug \
                                        -PmavenSettingsPath=${sonarSettingsXML} \
                                        -Dsonar.login=${secret} \
                                        -Dsonar.projectKey=${sonarProjectkey} \
                                        -Dsonar.projectName=${sonarProjectName}" 
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}