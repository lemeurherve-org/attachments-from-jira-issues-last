pipeline {
  agent any
  stages {

  stage('Iniciandoooo') {
            steps {
              sh '''
                echo "hola"
              '''
            }
      }

    stage('Inicio') {
      steps {
         script {
          withEnv(['HUBOT_URL=http://52.2.27.172:9999','HUBOT_DEFAULT_ROOM=pull-requests','HUBOT_FAIL_ON_ERROR=false']) {
  	hubotSend message: 'building job ${env.BUILD_NUMBER} - ${env.RUN_DISPLAY_URL} - $BLUE_OCEAN_URL - $BUILD_URL'
  	hubotApprove message: 'Proceed with building this job?'
	}
          }

        sh '''hubotSend message: \'Releasing Test project.\'
hubotSend message: "*Release Started*. \\n Releasing Test Project. :sunny: \\n<!here> <!channel> <@nrayapati> ", tokens: "BUILD_NUMBER,BUILD_ID", status: \'STARTED\''''
      }
    }
    stage('Build') {
          steps {
            sh '''
              echo "hola"
              hubotSend message: "*Release Completed*. \\n Releaseing Test Project.", tokens: "BUILD_NUMBER,BUILD_ID", status: \'SUCCESS\'
            '''
          }
    }
    stage('aprobacion') {
      steps {
        sh '''hubotApprove message: \'Promote to Staging?\', tokens: "BUILD_NUMBER, BUILD_DURATION", status: \'ABORTED\'
'''
      }
    }
    stage('inicio pre') {
      steps {
        sh '''hubotSend message: "*Staging Deployment Successful...* \\n Deployed Test Project to 192.168.1.175 node.", tokens: "BUILD_NUMBER,BUILD_ID", status: \'SUCCESS\'
'''
      }
    }
    stage('test') {
      steps {
        sh 'echo "test"'
      }
    }
    stage('a prod?') {
      steps {
        sh '''hubotApprove message: \'Promote to Production?\', tokens: "BUILD_NUMBER, BUILD_DURATION", status: \'ABORTED\'
'''
      }
    }
    stage('fin') {
      steps {
        sh 'hubotSend message: "*Hooray! Went to Prod... :satisfied:* \\n Deployed Test Project to prod(10.12.1.191) node.", tokens: "BUILD_NUMBER,BUILD_ID", status: \'SUCCESS\''
      }
    }
  }
}
