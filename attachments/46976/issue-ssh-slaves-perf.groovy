pipeline {
	agent { label params.LABEL }
	options {
		buildDiscarder(logRotator(numToKeepStr:'300'))

		timestamps()
	}
	parameters {
		string(	name: 'LABEL',
		       	defaultValue: 'fyre',
			description: 'Which node to use')
		string ( name: 'ITERATIONS', defaultValue: '10', description: 'How many times to run the loop')
	}
	stages {
	    stage ('One') {
	        steps {
	            script {
	                currentBuild.displayName = params.LABEL
	                for ( i = 0; i < params.ITERATIONS.toInteger(); i++) {
	                    def hostname = sh (script: "hostname", returnStdout: true)
	                    echo hostname
	                }

	            }
	        }
	    }
	}
}
