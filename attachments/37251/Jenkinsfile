#!groovy

//see https://jenkins-ci.org/blog/2015/12/16/workflow-best-practices-and-examples-repo-on-github/ and https://github.com/jenkinsci/workflow-examples/blob/master/docs/BEST_PRACTICES.md

//https://groups.google.com/forum/#!msg/jenkinsci-users/P7VMQQuMdsY/bHfBDSn9GgAJ
//import groovy.transform.Field
//@Field def INTEGRATION_BRANCH = 'develop'

//good docks on milestone and locks: https://jenkins.io/blog/2016/10/16/stage-lock-milestone/


static boolean isFeatureBranch(env) {
  return env.BRANCH_NAME ==~ /feature.*/
}

static boolean isIntegrationBranch(env) {
   return env.BRANCH_NAME ==~ /develop/
}

def tag() {
  stage ('tag') {
    sh 'git tag -fa integrationQa -m "Passed QA"'
    sh 'git push --force origin refs/tags/integrationQa:refs/tags/integrationQa'
  }
}


def soapuiTest() {
  try {
    mvn( '-f jfr-srv-soapui/pom.xml clean test -Dsoapui-test=true' )
  }
  finally {
    junit( allowEmptyResults: true, testResults: '**/target/surefire-reports/TEST-*.xml' )
    archive( includes: 'jfr-srv-soapui/target/soapui/**' )
  }
}

pipeline {
  agent {
    label 'docker'
  }
  options {
    buildDiscarder(logRotator(numToKeepStr: '10'))
    timeout(time: 1, unit: 'HOURS')
    timestamps()
  }
  tools {
    jdk 'Oracle-JDK8-64bit'
    maven 'apache-maven-3.x'
  }

  stages {
/*
    stage('checkout') {
      //checkout the branch so we're not "detached" which is default for scm checkout in parent script:
      sh "git checkout $env.BRANCH_NAME"
      //then sync with remote
      sh "git pull --rebase"
    }
*/

    stage('rebase') {
      when {
        //if it is a feature branch - rebase in changes from develop branch - the branch we target integration into:
        expression { isFeatureBranch(env) }
      }
      steps {
        sh "git rebase origin/develop"
      }
    }

    stage('unit-test') {
      steps {
        mvn('clean install -DskipDocker')
      }
    }

    stage('integration-test') {
      steps {
        timeout(30) {
          dockerCompose('docker-compose.yml', 'oracle') {
            sleep(90) //wait for oracle to get up
            mvn('clean')
            mvn('--am --projects jfr-srv-batch,jfr-srv-ws install -Denv=ci -Dflyway=true -Dintegration-test=true -Dbatch-test=true')
          }
        }
      }

      post {
        always {
          junit(allowEmptyResults: true, testResults: '**/target/surefire-reports/TEST-*.xml')
        }
      }
      //checkpoint('integration-test')
    }

      /*
     for lock and milestone, see: https://wiki.jenkins-ci.org/display/JENKINS/Pipeline+Milestone+Step+Plugin
     https://github.com/jenkinsci/workflow-aggregator-plugin/blob/master/demo/repo/Jenkinsfile
    */

      stage('interfaceTest') {
        steps {
          //milestone(label: 'deployAndTest')
          script {
            sh('docker pull fsnexus.mycompany.com:8085/mycompany/jetty:latest')
            mvn("-f jfr-srv-dist/pom.xml clean install -Dpackaging=true -Pdocker") //build the docker image
            dockerCompose('docker-compose.yml jfr-srv-dist/docker-compose.yml', 'oracle wmq wxs jfr rest_esb') {
              /*
              sleep(120) //wait for things to come up
              def envToDeploy = 'ci'
              load('deploy.groovy').prepDatabaseSchema(envToDeploy)
              load('deploy.groovy').dbunit(envToDeploy)
              soapuiTest()
              */
            }
          }
        }

        post {
          always {
            archive(includes: 'dockerlogs/**')
            archive(includes: 'restesb-logs/**')
          }
        }
      }

//    stage('lalalala') {
//      when {
//        //only develop branch get's deployed to maven repo, installed to servers and tested:
//        expression { isIntegrationBranch(env) }
//      }
//      steps {
//        script {
//        //use lock so only one instance of job can enter here
//        lock(resource: 'tacJfrDevDeploy', inversePrecedence: true) {
//          stage('deployToMavenRepo') {
//            // have to actually rebuild in order to be able to deploy...:
//            mvn('-Dpackaging=true -Dmaven.test.skip=true clean deploy -DdeployAtEnd=true')
//            //http://maven.apache.org/plugins/maven-deploy-plugin/deploy-mojo.html#deployAtEnd
//            archive(includes: '**/target/*.tar.gz')
//            checkpoint('deployedToNexus')
//          }
//
//          stage('deployToServers') {
//            //have the deployment script as a separate workflow so we can invoke isolated, but also call it directly here to deploy
//            load('deploy.groovy').rollout('g-d1')
//            checkpoint('deployedToServers')
//          }
//
//          stage('soapUi test') {
//            soapuiTest()
//            checkpoint('soapUiTested')
//          }
//
//          stage('runBatchJobs') {
//            def banks = ['7001']
//            for (bank in banks) {
//              dir('salt') {
//                stdout = sh(returnStdout: true,
//                        script: "salt-ssh --priv=/var/lib/jenkins/.ssh/id_rsa -i ebs-g-d1 state.sls batchebs.runDhub pillar='{ 'overrides': {'batchebs': {'bank': '$bank'}}}'")
//                if (!(stdout ==~ /(?msx)^.*Failed:.*0.*$/)) {
//                  echo(stdout)
//                  error("Did not have error-free execution of batch")
//                }
//              }
//            }
//            checkpoint('batchTested')
//          }
//
//          tag() //if we came to this point all is well and we tag it
//        }
//      }
//      }
//    }

  }

}
