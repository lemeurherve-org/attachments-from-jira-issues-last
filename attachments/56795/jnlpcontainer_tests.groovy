def testCore(def retries, String containerName){
    for(def i = 0; i <= retries; i++){
        try {
            stage("${containerName ?: 'no-container()'}-iter: ${i}"){
                sh("""
                    echo "${containerName ?: 'no-container()'} - test ${i}"
                    sleep 1
                """)
            }
        }
        catch( Exception e){
            // do nothing, try again and see what happens
        }
    }
}

def runTest(String containerName){
    return {
        def retries = 100

        if(containerName){
            container(containerName){
                testCore(retries, containerName)
            }
        }
        // testing without the closure container()
        else {
            testCore(retries, containerName)
        }

    }
}

parallelTestsMap = [
    "jnlp": runTest("jnlp"),
    "build": runTest("build"),
    "noContainer": runTest("")
]
String dockerRegistry = "<my-docker-registry>"
String workingDir = "/home/jenkins"
String userID = "1000"
String gID = "1000"
String uniqueLabel = "test_multipleContainers_in_node-${UUID.randomUUID().toString()}"
String buildDockerImage = "${dockerRegistry}/ubuntu:20.04"
String jnlpDockerImage = "${dockerRegistry}/jenkins/inbound-agent:4.11-1-alpine-jdk8"
List imagePullSecrets = ["<my-secret>"]

steps.podTemplate(
    label: uniqueLabel,
    containers: [
        steps.containerTemplate(
            args: "",
            command: "",
            image: jnlpDockerImage,
            name: "jnlp",
            ttyEnabled: false,
            workingDir: workingDir
        ),
        steps.containerTemplate(
            args: "99d",
            command: "sleep",
            image: buildDockerImage,
            name: "build",
            resourceRequestCpu: "1",
            resourceLimitCpu: "1",
            resourceRequestMemory: "1Gi",
            resourceLimitMemory: "1Gi",
            ttyEnabled: false,
            workingDir: workingDir
        ),
    ],
    runAsUser: userID,
    runAsGroup: gID,
    nodeUsageMode: "EXCLUSIVE",
    nodeSelector: "beta.kubernetes.io/os=linux",
    imagePullSecrets: imagePullSecrets,
    workspaceVolume:
            steps.emptyDirWorkspaceVolume(memory: false)
){
    node(uniqueLabel) {
        parallel(parallelTestsMap)
    }
}
