}
pipeline {
 agent any
 stages {

  stage('Prepare Workflow') {
   steps {
    script {
     //get Reference
     println("Getting References")
    }
   }
  }

  stage('Generate Debug Job') {
   steps {
    script {
     println("Generate Debug Job")
    }
   }
  }

  stage('Pushing 2 Tests and 2 Debug jobs') {
   //failFast true
   parallel {

    stage('TE1') {
     stages {
      stage('PUSH TE1') {
       steps {
        script {
         println("PUSH TE1")
        }
       }
      }
      stage('Archive Logs') {
       steps {
        script {
         println("Archive TE1 Logs")
        }
       }
      }
      stage('Insert Metrics') {
       steps {
        script {
         println("Insert TE1 Metrics in DB")
        }
       }
      }
      stage('Compare Metrics') {
       steps {
        script {
         println("Compare Metrics TE1 vs REF")
        }
       }
      }
     }
    }

    stage('TE2') {
     stages {
      stage('PUSH TE2') {
       steps {
        script {
         println("PUSH TE2")
        }
       }
      }

      stage('Archive Logs') {
       steps {
        script {
         println("Archive TE2 Logs")
        }
       }
      }
      stage('Insert Metrics') {
       steps {
        script {
         println("Insert TE2 Metrics in DB")
        }
       }
      }
      stage('Compare Metrics') {
       steps {
        script {
         println("Compare Metrics TE2 vs REF")
        }
       }
      }
     }
    }

    stage('HEAD DEBUG') {
     stages {
      stage('PUSH HEAD DEBUG') {
       steps {
        script {
         println("PUSH HEAD DEBUG")
        }
       }
      }
      stage('DEPLOY CLIENT HEAD') {
       steps {
        script {
         println("Deploying Client HEAD")
        }
       }
      }
     }
    }

    stage('REF DEBUG') {
     stages {
      stage('PUSH REF DEBUG') {
       steps {
        script {
         println("PUSH REF DEBUG")
        }
       }
      }
      stage('DEPLOY CLIENT REF') {
       steps {
        script {
         println("Deploying Client REF")
        }
       }
      }
     }
    }

   }
  }

  stage('CrossChecking') {
   steps {
    script {
     println("CrossChecking")
    }
   }
  }

  stage('SetReferences') {
   steps {
    script {
     println("Set References")
    }
   }
  }

  stage('Head vs Head-1') {
   steps {
    script {
     println("Head vs Head-1")
    }
   }
  }


  stage('Send MAIL and Insert JIRA') {

   parallel {
    stage('Sending Mail') {
     stages {
      stage('Send MAIL') {
       steps {
        script {
         println("Sending Mail")
        }
       }
      }
     }
    }

    stage('Inserting Jira') {
     stages {
      stage('Insert JIRA') {
       steps {
        script {
         println "Inserting MXIT Jira"
        }
       }
      }
     }
    }
   }
  }

  stage('Dichotomy and Profiling') {

   parallel {
    stage('Start Dichotomy') {
     stages {
      stage('Dichotomy') {
       steps {
        script {
         println("Dichotomy")
        }
       }
      }
     }
    }

    stage('Start Profiling Workflow') {
     stages {
      stage('Profiling Workflow') {
       steps {
        script {
         println("Profiling Workflow")
        }
       }
      }
     }
    }
   }
  }


 }
}