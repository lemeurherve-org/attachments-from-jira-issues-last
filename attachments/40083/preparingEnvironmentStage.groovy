#!groovy
def call(String type) { 
	echo """Testing preparing environment through pipeline...
		"""
	env.IS_LIBRARY = (type=='library') 
	
	pipeline { 
		agent none		
		options { buildDiscarder(logRotator(numToKeepStr: '10')) }
		tools { 
	        maven 'maven 3.3.9' //Usamos maven 3.3.9
	        jdk 'java1.8' //Compilamos con Java 1.8
	    }
	 	stages { 
	      stage('Preparing environment') {
			  agent any
	          steps {
	              prepareEnvironment isLibrary: env.IS_LIBRARY
	           }
	      }
	    }
	}
}