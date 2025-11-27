
parallel (

    "using-dir": {
        node ('windows') {
            dir("tmp") { bat "ping 127.0.0.1 -n 2" }
            dir("tmp") { bat "ping 127.0.0.1 -n 2" }
            dir("tmp") { bat "ping 127.0.0.1 -n 2" }
        }
    },

    "using-with-env": {
        node ('windows') {
            def myenv = ["BLAH=true"]
            withEnv(myenv) { bat "ping 127.0.0.1 -n 2" }
            withEnv(myenv) { bat "ping 127.0.0.1 -n 2" }
            withEnv(myenv) { bat "ping 127.0.0.1 -n 2" }
        }
    },

    "using-timeout": {
        node ('windows') {
            timeout(10) { bat "ping 127.0.0.1 -n 2" }
            timeout(10) { bat "ping 127.0.0.1 -n 2" }
            timeout(10) { bat "ping 127.0.0.1 -n 2" }
        }
    },

    //  Using a closure directly doesn't seem to overwrite the logging.
    "using-closure": {
        node ('windows') {
            def closure = {
                bat "ping 127.0.0.1 -n 2"
            }
            closure.call()
            closure.call()
            closure.call()
        }
    }

)
