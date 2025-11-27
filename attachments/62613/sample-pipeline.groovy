pipeline {
    agent any
    stages {
        stage('Timeout') {
            steps {
                script {
                    try {
                        timeout(time: 3, unit: 'SECONDS') {
                            input message: 'Click "Proceed" to skip the wait', submitter: 'username'
                        }
                    } catch (err) {
                        def user = err.getCauses()[0].getUser()
                        if('SYSTEM' == user.toString()) { //timeout
                            echo "Input timeout expired, continuing with the next stages"
                            currentBuild.result = "SUCCESS"
                        } else {
                            echo "Input aborted by: [${user}]"
                            currentBuild.result = "ABORTED"
                            return
                        }
                    }   
                }
            }
        }
    }
}
