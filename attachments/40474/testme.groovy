job("foobar") {

  logRotator(-1, 5, -1, -1)

  wrappers {
    timestamperBuildWrapper()
  }
  steps {
    shell {
      command("ls -l")
    }
  }

  publishers {
    postBuildScript {
      buildSteps {
        postBuildStep {
          role('SLAVE')
          results(["SUCCSESS", "FAILURE", "UNSTABLE"])
          buildSteps {
            shell {
              command("echo hello")
            }
          }
        }
      }
      markBuildUnstable(false)
    }

  }
}

