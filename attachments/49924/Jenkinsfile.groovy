pipeline {
   
   // ommitted
	
    stages {

        // ommitted
     
        stage ('Run Unit Tests') {
            stages {
                stage ('Run Unit Tests - Release Build') {
                    steps {
                        // run gtest and upload test results to Jenkins
                        bat "echo release unit tests"
                    }
                }
                
                stage ('Run Unit Tests - Special Build') {
                    steps {
                        // run gtest and upload test results to Jenkins
                        bat "echo special unit test"
                    }
                }

                stage ('Run Unit Tests - Debug Build') {
                    steps {
                        // run gtest and upload test results to Jenkins
                        bat "echo debug unit tests"
                    }
                }
            }
        }
    }
}