pipeline {
    agent none
    
    options {
        buildDiscarder logRotator(numToKeepStr: '20')
    }

    environment {
        BRANCH_NAME = 'hotfix/test'
    }

    stages {
        stage('Checkout') {
            agent any
            environment {
                env1 = 'abc'
            }
            steps {
                echo 'Checkout'
            }
        }
        stage('NuGet') {
            agent any
            environment {
                env2 = 'abc'
            }
            steps {
                echo 'NuGet'
            }
        }
        stage('Build') {
            agent any
            environment {
                env3 = 'abc'
            }
            steps {
                echo 'Build'
            }
        }
        stage('Test') {
            agent any
            environment {
                env4 = 'abc'
            }
            steps {
                script {
                    def testAssemblies = '**/*.Tests.dll'
                }
            }
        }
        stage('Environment A') {
            agent none
            when {
                branch 'release/**'
            }
            environment {
                buildConfiguration = 'A'
            }
            stages {
                stage('Build A') {
                    agent any
                    steps {
                        echo 'Build'
                    }
                }
                stage('Input A') {
                    agent none
                    options {
                        timeout time: 15, unit: 'MINUTES'
                    }
                    steps {
                        input message: 'Waiting for input', submitter: ''
                    }
                }
                stage('Deploy A') {
                    agent any
                    steps {
                        echo 'Deploy'
                    }
                }
            }
        }
        stage('Environment B') {
            agent none
            when {
                anyOf {
                    branch 'release/**'
                    branch 'hotfix/**'
                }
            }
            environment {
                buildConfiguration = 'B'
            }
            stages {
                stage('Build B') {
                    agent any
                    environment {
                        envB1 = 'abc'
                    }
                    steps {
                        echo 'Build'
                    }
                }
                stage('Input B') {
                    agent none
                    options {
                        timeout time: 15, unit: 'MINUTES'
                    }
                    steps {
                        input message: 'Waiting for input', submitter: ''
                    }
                }
                stage('Deploy B') {
                    agent any
                    steps {
                        echo 'Deploy'
                    }
                }
            }
        }
    }
}
