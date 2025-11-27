#!groovy
//
// This is written using the "Scripted Pipeline" (https://jenkins.io/doc/book/pipeline/syntax/#scripted-pipeline)
// not the declarative DSL. We break out to "Workflow CPS (aka Groovy DSL)" with `script {}` blocks
// as needed.
//
// Useful docs: https://jenkins.io/doc/pipeline/steps/workflow-basic-steps

def testJobsPaths = []
def lockerVersion = ""

stage('publish') {
    node("public") {
        try {
            setDisplayName()
            scalaCheckout()
            def scalaVersion1 = ""
            if (!params.skipLocker) {
                ansiColor('xterm') {
                    runScript("scripts/jobs/validate/publish-core")
                }
                def props = readProperties file: 'jenkins.properties'
                lockerVersion = props['maven.version.number']
            }
            testJobsPaths = findFiles(glob: jobsGlob)

        } finally {
            archiveArtifacts artifacts: 'hs_err_*.log,jenkins.properties', allowEmptyArchive: true
        }
    }
}

def testStage(scriptFile) {
    def name = (scriptFile.name == "test") ? "test" : "test-${scriptFile.name}"
    // We need to wrap what we return in a Groovy closure, or else it's invoked
    // when this method is called, not when we pass it to parallel.
    // To do this, you need to wrap the code below in { }, and either return
    // that explicitly, or use { -> } syntax.
    action = { ->
        node("public") { stage(name) {
            try {
                println("Starting stage ${name} to run ${scriptFile} on ${env.NODE_NAME}@${env.EXECUTOR_NUMBER} in ${env.WORKSPACE}")
                scalaCheckout()

                if (params.skipLocker) {
                    def props = readProperties file: 'versions.properties'
                    starrVersion = props['starr.version']
                    env['scalaVersion']  = starrVersion
                } else {
                    env['scalaVersion'] = lockerVersion
                }

                ansiColor('xterm') {
                    runScript(scriptFile)
                }
            }
            finally {
                println("Ending stage ${name} to run ${scriptFile} on ${env.NODE_NAME}@${env.EXECUTOR_NUMBER} in ${env.WORKSPACE}")

                archiveArtifacts artifacts: 'hs_err_*.log,**/test-reports/**/*.xml,build/junit/TEST-*,build/osgi/TEST-*', allowEmptyArchive: true
                junit allowEmptyResults: true, testResults: '**/test-reports/**/*.xml'
            }
        }}
    }
    [name, action]
}

parallel testJobsPaths.collectEntries{testStage(it)}


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// END OF BUILD PROPER
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


def setDisplayName() {
    currentBuild.setDisplayName("[${currentBuild.number}] $repo_user/$repo_name#$_scabot_pr at ${repo_ref.take(6)}")
}

def scalaCheckout() {
    checkout scm: [$class: 'GitSCM', userRemoteConfigs: [[url: "https://github.com/${repo_user}/${repo_name}.git"]],
             branches: [[name: "${repo_ref}"]], extensions: [[$class: 'CleanCheckout']]],
             changelog: false, poll: false
}

def runScript(path) {
    sh """#!/bin/bash -ex
    if [ -f /usr/local/share/jvm/jvm-select ]; then
        . /usr/local/share/jvm/jvm-select;
        jvmSelect $jvmFlavor $jvmVersion;
    else
        echo 'WARNING: jvm-select not present. using system default Java';
    fi
    echo scalaVersion=\$scalaVersion
    echo BASH_VERSION="\$BASH_VERSION"
    . ${path}
    """
}
