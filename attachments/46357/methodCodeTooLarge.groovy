/*
This script reproduces the error "Method Code too Large"
Removing, for example the last stage, will fix the problem.
*/

//Enter your slave label
def slaveMgmt = "cgkWindowsMgmtPrd"
def testWhen = true
def testWhen2 = true
pipeline{
    agent{ node{label slaveMgmt}}
    options{
        timestamps()
    }
    environment{
        VERBOSE = true
    }
    stages{
        stage("s1"){
            when{
                allOf {
                    expression { testWhen }
                    expression { testWhen2 }
                }
            }
            steps{
                script{
					try {
						echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
					}catch(err){
						error("Bad")
					}
                }
            }
        }
        stage("s2"){
            when{
                allOf {
                    expression { testWhen }
                    expression { testWhen2 }
                }
            }
            steps{
                script{
                    try {
						echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
					}catch(err){
						error("Bad")
					}
                }
            }
        }
        stage("s3"){
            when{
                allOf {
                    expression { testWhen }
                    expression { testWhen2 }
                }
            }
            steps{
                script{
                    try {
						echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
					}catch(err){
						error("Bad")
					}
                }
            }
        }
        stage("s4"){
            when{
                allOf {
                    expression { testWhen }
                    expression { testWhen2 }
                }
            }
            steps{
                script{
                    try {
						echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
					}catch(err){
						error("Bad")
					}
                }
            }
        }
        stage("s5"){
            when{
                allOf {
                    expression { testWhen }
                    expression { testWhen2 }
                }
            }
            steps{
                script{
                    try {
						echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
					}catch(err){
						error("Bad")
					}
                }
            }
        }
        stage("s6"){
            when{
                allOf {
                    expression { testWhen }
                    expression { testWhen2 }
                }
            }
            steps{
                script{
                    try {
						echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
					}catch(err){
						error("Bad")
					}
                }
            }
        }
        stage("s7"){
            when{
                allOf {
                    expression { testWhen }
                    expression { testWhen2 }
                }
            }
            steps{
                script{
                    try {
						echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
					}catch(err){
						error("Bad")
					}
                }
            }
        }
        stage("s8"){
            when{
                allOf {
                    expression { testWhen }
                    expression { testWhen2 }
                }
            }
            steps{
                script{
                    try {
						echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
					}catch(err){
						error("Bad")
					}
                }
            }
        }
        stage("s9"){
            when{
                allOf {
                    expression { testWhen }
                    expression { testWhen2 }
                }
            }
            steps{
                script{
                    try {
						echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
					}catch(err){
						error("Bad")
					}
                }
            }
        }
        stage("s10"){
            when{
                allOf {
                    expression { testWhen }
                    expression { testWhen2 }
                }
            }
            steps{
                script{
                    try {
						echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
					}catch(err){
						error("Bad")
					}
                }
            }
        }
        stage("s11"){
            when{
                allOf {
                    expression { testWhen }
                    expression { testWhen2 }
                }
            }
            steps{
                script{
                    try {
						echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
					}catch(err){
						error("Bad")
					}
                }
            }
        }
        stage("s12"){
            when{
                allOf {
                    expression { testWhen }
                    expression { testWhen2 }
                }
            }
            steps{
                script{
                    try {
						echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
					}catch(err){
						error("Bad")
					}
                }
            }
        }
        stage("s13"){
            when{
                allOf {
                    expression { testWhen }
                    expression { testWhen2 }
                }
            }
            steps{
                script{
                    try {
						echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
					}catch(err){
						error("Bad")
					}
                }
            }
        }
        stage("s14"){
            when{
                allOf {
                    expression { testWhen }
                    expression { testWhen2 }
                }
            }
            steps{
                script{
                    try {
						echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
					}catch(err){
						error("Bad")
					}
                }
            }
        }
        stage("s15"){
            when{
                allOf {
                    expression { testWhen }
                    expression { testWhen2 }
                }
            }
            steps{
                script{
                    try {
						echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
					}catch(err){
						error("Bad")
					}
                }
            }
        }
        stage("s16"){
            when{
                allOf {
                    expression { testWhen }
                    expression { testWhen2 }
                }
            }
            steps{
                script{
                    try {
						echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
					}catch(err){
						error("Bad")
					}
                }
            }
        }
        
        
        stage("para1"){
            parallel {
                stage("some stuff"){
                    steps{
                        script{
                            try {
								echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
							}catch(err){
								error("Bad")
							}
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("some more stuff"){
                    steps{
                        script{
							try{
								echo "calling more scripts"
							}catch(err){
								error("bad")
							}
                            echo "calling more scripts"
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("even more stuff"){
                    steps{
                        script{
							try{
								echo "calling even more scripts"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("this too"){
                    steps{
                        script{
							try{
								echo "calling last script"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
            }
        }
        stage("para2"){
            parallel {
                stage("some stuff"){
                    steps{
                        script{
                            try {
								echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
							}catch(err){
								error("Bad")
							}
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("some more stuff"){
                    steps{
                        script{
							try{
								echo "calling more scripts"
							}catch(err){
								error("bad")
							}
                            echo "calling more scripts"
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("even more stuff"){
                    steps{
                        script{
							try{
								echo "calling even more scripts"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("this too"){
                    steps{
                        script{
							try{
								echo "calling last script"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
            }
        }
        stage("para3"){
            parallel {
                stage("some stuff"){
                    steps{
                        script{
                            try {
								echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
							}catch(err){
								error("Bad")
							}
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("some more stuff"){
                    steps{
                        script{
							try{
								echo "calling more scripts"
							}catch(err){
								error("bad")
							}
                            echo "calling more scripts"
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("even more stuff"){
                    steps{
                        script{
							try{
								echo "calling even more scripts"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("this too"){
                    steps{
                        script{
							try{
								echo "calling last script"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
            }
        }
        stage("para4"){
            parallel {
                stage("some stuff"){
                    steps{
                        script{
                            try {
								echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
							}catch(err){
								error("Bad")
							}
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("some more stuff"){
                    steps{
                        script{
							try{
								echo "calling more scripts"
							}catch(err){
								error("bad")
							}
                            echo "calling more scripts"
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("even more stuff"){
                    steps{
                        script{
							try{
								echo "calling even more scripts"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("this too"){
                    steps{
                        script{
							try{
								echo "calling last script"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
            }
        }
        stage("para5"){
            parallel {
                stage("some stuff"){
                    steps{
                        script{
                            try {
								echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
							}catch(err){
								error("Bad")
							}
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("some more stuff"){
                    steps{
                        script{
							try{
								echo "calling more scripts"
							}catch(err){
								error("bad")
							}
                            echo "calling more scripts"
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("even more stuff"){
                    steps{
                        script{
							try{
								echo "calling even more scripts"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("this too"){
                    steps{
                        script{
							try{
								echo "calling last script"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
            }
        }
        stage("para6"){
            parallel {
                stage("some stuff"){
                    steps{
                        script{
                            try {
								echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
							}catch(err){
								error("Bad")
							}
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("some more stuff"){
                    steps{
                        script{
							try{
								echo "calling more scripts"
							}catch(err){
								error("bad")
							}
                            echo "calling more scripts"
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("even more stuff"){
                    steps{
                        script{
							try{
								echo "calling even more scripts"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("this too"){
                    steps{
                        script{
							try{
								echo "calling last script"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
            }
        }
        stage("para7"){
            parallel {
                stage("some stuff"){
                    steps{
                        script{
                            try {
								echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
							}catch(err){
								error("Bad")
							}
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("some more stuff"){
                    steps{
                        script{
							try{
								echo "calling more scripts"
							}catch(err){
								error("bad")
							}
                            echo "calling more scripts"
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("even more stuff"){
                    steps{
                        script{
							try{
								echo "calling even more scripts"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("this too"){
                    steps{
                        script{
							try{
								echo "calling last script"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
            }
        }
        stage("para8"){
            parallel {
                stage("some stuff"){
                    steps{
                        script{
                            try {
								echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
							}catch(err){
								error("Bad")
							}
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("some more stuff"){
                    steps{
                        script{
							try{
								echo "calling more scripts"
							}catch(err){
								error("bad")
							}
                            echo "calling more scripts"
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("even more stuff"){
                    steps{
                        script{
							try{
								echo "calling even more scripts"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("this too"){
                    steps{
                        script{
							try{
								echo "calling last script"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
            }
        }
        stage("para9"){
            parallel {
                stage("some stuff"){
                    steps{
                        script{
                            try {
								echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
							}catch(err){
								error("Bad")
							}
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("some more stuff"){
                    steps{
                        script{
							try{
								echo "calling more scripts"
							}catch(err){
								error("bad")
							}
                            echo "calling more scripts"
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("even more stuff"){
                    steps{
                        script{
							try{
								echo "calling even more scripts"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("this too"){
                    steps{
                        script{
							try{
								echo "calling last script"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
            }
        }
        stage("para10"){
            parallel {
                stage("some stuff"){
                    steps{
                        script{
                            try {
								echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
							}catch(err){
								error("Bad")
							}
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("some more stuff"){
                    steps{
                        script{
							try{
								echo "calling more scripts"
							}catch(err){
								error("bad")
							}
                            echo "calling more scripts"
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("even more stuff"){
                    steps{
                        script{
							try{
								echo "calling even more scripts"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("this too"){
                    steps{
                        script{
							try{
								echo "calling last script"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
            }
        }
        stage("para11"){
            parallel {
                stage("some stuff"){
                    steps{
                        script{
                            try {
								echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
							}catch(err){
								error("Bad")
							}
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("some more stuff"){
                    steps{
                        script{
							try{
								echo "calling more scripts"
							}catch(err){
								error("bad")
							}
                            echo "calling more scripts"
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("even more stuff"){
                    steps{
                        script{
							try{
								echo "calling even more scripts"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("this too"){
                    steps{
                        script{
							try{
								echo "calling last script"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
            }
        }
        stage("para12"){
            parallel {
                stage("some stuff"){
                    steps{
                        script{
                            try {
								echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
							}catch(err){
								error("Bad")
							}
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("some more stuff"){
                    steps{
                        script{
							try{
								echo "calling more scripts"
							}catch(err){
								error("bad")
							}
                            echo "calling more scripts"
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("even more stuff"){
                    steps{
                        script{
							try{
								echo "calling even more scripts"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("this too"){
                    steps{
                        script{
							try{
								echo "calling last script"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
            }
        }
        stage("para13"){
            parallel {
                stage("some stuff"){
                    steps{
                        script{
                            try {
								echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
							}catch(err){
								error("Bad")
							}
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("some more stuff"){
                    steps{
                        script{
							try{
								echo "calling more scripts"
							}catch(err){
								error("bad")
							}
                            echo "calling more scripts"
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("even more stuff"){
                    steps{
                        script{
							try{
								echo "calling even more scripts"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("this too"){
                    steps{
                        script{
							try{
								echo "calling last script"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
            }
        }
        stage("para14"){
            parallel {
                stage("some stuff"){
                    steps{
                        script{
                            try {
								echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
							}catch(err){
								error("Bad")
							}
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("some more stuff"){
                    steps{
                        script{
							try{
								echo "calling more scripts"
							}catch(err){
								error("bad")
							}
                            echo "calling more scripts"
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("even more stuff"){
                    steps{
                        script{
							try{
								echo "calling even more scripts"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("this too"){
                    steps{
                        script{
							try{
								echo "calling last script"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
            }
        }
        stage("para15"){
            parallel {
                stage("some stuff"){
                    steps{
                        script{
                            try {
								echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
							}catch(err){
								error("Bad")
							}
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("some more stuff"){
                    steps{
                        script{
							try{
								echo "calling more scripts"
							}catch(err){
								error("bad")
							}
                            echo "calling more scripts"
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("even more stuff"){
                    steps{
                        script{
							try{
								echo "calling even more scripts"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("this too"){
                    steps{
                        script{
							try{
								echo "calling last script"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
            }
        }
        stage("para16"){
            parallel {
                stage("some stuff"){
                    steps{
                        script{
                            try {
								echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
							}catch(err){
								error("Bad")
							}
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("some more stuff"){
                    steps{
                        script{
							try{
								echo "calling more scripts"
							}catch(err){
								error("bad")
							}
                            echo "calling more scripts"
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("even more stuff"){
                    steps{
                        script{
							try{
								echo "calling even more scripts"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("this too"){
                    steps{
                        script{
							try{
								echo "calling last script"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
            }
        }
        stage("para17"){
            parallel {
                stage("some stuff"){
                    steps{
                        script{
                            try {
								echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
							}catch(err){
								error("Bad")
							}
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("some more stuff"){
                    steps{
                        script{
							try{
								echo "calling more scripts"
							}catch(err){
								error("bad")
							}
                            echo "calling more scripts"
							echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("even more stuff"){
                    steps{
                        script{
							try{
								echo "calling even more scripts"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
                stage("this too"){
                    steps{
                        script{
							try{
								echo "calling last script"
							}catch(err){
								error("bad")
							}
							 echo "Some example text to make method code large - There once was a pig that could fly - It was called spiderpig"
                        }
                    }
                }
            }
        }
        
        
        
    }
    post{
        success{
            script{
                echo "all ok"
            }
        }
        failure{
            script{
                echo "not ok"
            }
        }
        always {
            deleteDir()
        }
    }
}