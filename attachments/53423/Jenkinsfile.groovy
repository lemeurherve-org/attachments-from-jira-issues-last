pipeline {
    agent {
        docker {
            image "python:3.9.0-buster"
        }
    }
    stages {
        stage("Pipeline Docker agent") {
            steps {
                sh "python --version --version"
            }
        }
        stage("Custom agent by label") {
            agent {
                label 'labeled-agent'
            }
            steps {
                sh 'echo we are here'
            }
        }
    }
}
