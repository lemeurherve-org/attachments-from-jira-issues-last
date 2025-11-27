pipeline {
	agent {
	    node {
	        label 'node-label'
	    }
	}
	stages {
		stage('Inner Stage') {
			steps {
				build propagate: false, job: "ProjectDownStream"
			}
		}
	}
	post {
        success {
            script {
            	println("success 0")
            }
        }
        cleanup {
        	script {
        		println("cleanup 0")
            	deleteDir()
        	}
        }
        failure {
            script {
            	println("failure 0")
            }
        }
        unstable {
            script {
            	println("unstable 0")
            }
        }
        unsuccessful {
            script {
            	println("unsuccessful 0")
            }
        }
    }
}