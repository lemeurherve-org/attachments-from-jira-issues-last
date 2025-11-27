#!/usr/bin/env groovy

def call(body) {

    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    
    def artifactName = 'extractor'
    def artifactExt = '.war'
    def artifactVersion = '0.0.1'

    def buildPath = 'target/'
    def warFile = artifactName + '-' + artifactVersion + artifactExt
    def warPath = buildPath + warFile
    def warNoVersion = artifactName + artifactExt

    def deployPath = '/var/lib/tomcat8/webapps/'
    def deployFile = deployPath + warNoVersion
    
        
    node {
        // Clean workspace before doing anything
        //deleteDir()

        try {

            stage ('Code Checkout') {
                git branch: 'master',
                    credentialsId: '-------------------------',
                    url: 'ssh://git@bitbucket.org/xxxxxxx/xxxxxxx'
            }

            stage ('Configure Application') {
                configFileProvider([configFile(fileId: config.confiFileId, variable: 'CONFIG_FILE_PATH')]) {
                    sh 'cp $CONFIG_FILE_PATH resources/config.properties'
                }
                sh "echo 'job_name: $JOB_NAME' > WebContent/version.txt"
                sh "echo 'job_number: $BUILD_NUMBER' >> WebContent/version.txt"
            }
            
            stage ('Run Unitests') {
                sh 'mvn test'
            }
            
            /*stage ('SonarQube analysis') {
              withSonarQubeEnv(' Azure Sonarqube') {
                sh 'mvn sonar:sonar'
              }  
            }*/
            
            stage ('Compile and Build WAR') {
                sh 'mvn clean compile war:war'
            }
            
            stage ('Upload war to Artifactory') {
                withCredentials([usernamePassword(credentialsId: 'artifactory', usernameVariable: 'user', passwordVariable: 'password')])
                //withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'artifactory', usernameVariable: 'user', passwordVariable: 'pass']])
                sh "sudo curl -u $user:$password -T $warPath 'https://artifactory.xxxxxx.com:443/artifactory/form/$warFile'"

            }


/*            stage ('Deploy WAR to server') {
                sh "scp $warPath jenkins@${config.serverIp}:/tmp/$warNoVersion"
                sh "ssh jenkins@${config.serverIp} 'sudo mv /tmp/$warNoVersion $deployFile'"
            }

            stage ('Restart Tomcat') {
                sh "ssh jenkins@${config.serverIp} 'sudo service tomcat8 force-reload'"
            }

            stage ('Send Notifications') {
                notifyBuild('SUCCESS', config.slackChannel)
            } */

        } catch (err) {
            notifyBuild('FAILURE', config.slackChannel)
            throw err
        }
    }
}


def notifyBuild(String buildResult, String channelName) {
    def colorCode = '#FF0000'
    def textBody = ''

    if (buildResult == 'SUCCESS') {
        colorCode = '#00FF00'
    }

    textBody += "${buildResult} => "
    textBody += "Job: `${env.JOB_NAME} #${env.BUILD_NUMBER}` | "
    textBody += "URL: ${env.BUILD_URL}"

    slackSend(message: textBody, color: colorCode, channel: channelName)
}