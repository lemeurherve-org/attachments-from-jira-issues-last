package com.worldremit.jenkins.utils

// Class to run containerised automated tests

class TestRunner {

    // Runs automated tests in a container. Requires that the container contains the file
    // `entrypoint.sh` to start tests. The service hostname is injected into the container
    // at runtime to provide for dynamic targeting of http based tests.
    //
    // args:
    // parent - pipeline/script object (usualy 'this' from parent)
    // env - Environment under test (dev/tst/ppd/prd)
    // jobBaseName - name of the current service (job)
    // serviceHost - hostname of the service under test
    // testWorkingDir - Working dir for tests
    // entrypointScript - path to the `entrypoint.sh` script
    // image - the image to use for testing
    static def runTestingImage(parent, env, jobBaseName, serviceHost, testWorkingDir, entrypointScript, image) {
        def nodeID = "jknode-${jobBaseName}-test-${parent.env.BUILD_NUMBER}"

        def serviceName = jobBaseName

        // Set a couple of variables which will be exposed inside of the testing container
        def envVars = [parent.envVar(key: 'SERVICE_NAME', value: "${serviceName}")]
        if (serviceHost) {
            envVars.push(parent.envVar(key: 'SERVICE_HOST', value: "${serviceHost}"))
        }

        parent.echo("Running automated tests on ${jobBaseName} (${serviceHost}) using container: ${image}")

        // Set up the test container/pod
        parent.podTemplate(
                cloud: "primary-aks-eurw-${env}",
                label: nodeID,
                namespace: jobBaseName,
                nodeUsageMode: 'EXCLUSIVE',
                slaveConnectTimeout: '1800', // 30 minutes connection timeout, in case cluster autoscaling needs to kick in
                activeDeadlineSeconds: '1800', // Kill the pod after 30 mins even if it has not completed
                podRetention: parent.never(), // Always delete the pod after build completes
                idleMinutes: '0', // Do not keep the pod active for reuse
                //imagePullSecrets: [parent.env.K8S_DOCKER_CONFIG_SECRET_NAME],
                workspaceVolume: parent.emptyDirWorkspaceVolume(true),
                //serviceAccount: 'jenkins',
                containers: [
                        parent.containerTemplate(
                                name: 'tests',
                                image: image,
                                resourceRequestCpu: '0',
                                resourceRequestMemory: '2Gi',
                                ttyEnabled: true,
                                command: 'cat',
                                envVars: envVars
                        )
                ]
        )
                {
                    parent.node(nodeID) {
                        parent.container('tests') {...}
                    }
                }
    }
}