pipeline {
    agent none
    stages {
        stage('nothing') {
            agent none
            steps {
                echo "y"
            }
        }
    }
}