def getWorkspace() {
    return "${pwd()}/../Executor${env.EXECUTOR_NUMBER}"
}

def isPullRequest() {
    return env.BRANCH_NAME.startsWith("PR-")
}

def getPullRequestID() {
    return env.BRANCH_NAME.substring(3)
}

def prepareCommonLibraries(String commonLibrariesHash) {
    def workspace = "${env.CHECKOUT_DIR}/Executor${env.EXECUTOR_NUMBER}/CommonLibraries"
    
    echo "Checking out Common libraries hash ${commonLibrariesHash} to folder ${workspace}"
    ws("${workspace}") {
        checkout changelog: false, poll: false, scm: [$class: 'GitSCM', branches: [[name: commonLibrariesHash]], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'CloneOption', depth: 0, noTags: false, reference: '', shallow: false, timeout: 60], [$class: 'PruneStaleBranch'], [$class: 'SubmoduleOption', disableSubmodules: false, recursiveSubmodules: true, reference: '', timeout: 60, trackingSubmodules: false], [$class: 'CheckoutOption', timeout: 60]], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'aa4f6219-df5e-4cb0-b5b2-0dfe53217183', url: 'jenkins@rambo:/opt/CommonLibraries/.git']]]
    }

    return workspace
}

def prepareTestImages(String testImagesHash) {
    def workspace = "${env.CHECKOUT_DIR}/Executor${env.EXECUTOR_NUMBER}/TestImages"

    echo "Checking out Test images hash ${testImagesHash} to folder ${workspace}"

    ws("${workspace}") {
        checkout changelog: false, poll: false, scm: [$class: 'GitSCM', branches: [[name: testImagesHash]], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'CloneOption', depth: 0, noTags: false, reference: '', shallow: false, timeout: 60], [$class: 'PruneStaleBranch'], [$class: 'SubmoduleOption', disableSubmodules: false, recursiveSubmodules: true, reference: '', timeout: 60, trackingSubmodules: false], [$class: 'CheckoutOption', timeout: 60]], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'aa4f6219-df5e-4cb0-b5b2-0dfe53217183', url: 'jenkins@rambo:/opt/TestImages/.git']]]
    }

    return workspace
}