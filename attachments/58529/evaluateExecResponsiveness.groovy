def podName = null
pipeline {
    agent none
    stages {
        stage('Run Tests') {
            parallel {
                stage('start-agent') {
                    agent {
                        kubernetes {
                            cloud "local"
                            yaml '''
                apiVersion: v1
                kind: Pod
                spec:
                  containers:
                  - name: shell
                    image: jenkins/inbound-agent:4.11-1
                    command:
                    - sleep
                    args:
                    - infinity
                '''
                        }
                    }
                    steps {
                        script {
                            podName=env.NODE_NAME
                            container('shell') {
                                while(true) {
                                    sh(script: """
                                        #!/bin/bash
                                        set +x
                                        printf "[%s]\t[script] start\n" "\$(date)"
                                        sleep 5
                                        printf "[%s]\t[script] end\n" "\$(date)"
                                    """)
                                }
                            }
                        }
                    }
                }
                stage('test-websocket') {
                    agent {
                        label 'built-in'
                    }
                    steps {
                        script {
                            waitUntil {
                                podName != null
                            }
                        }
                        sh """
#!/bin/bash

set +x

while true
do
# Point to the internal API server hostname
APISERVER=\${KUBERNETES_SERVICE_HOST:-kubernetes.default.svc}

# Path to ServiceAccount token
SERVICEACCOUNT=/var/run/secrets/kubernetes.io/serviceaccount

# Read this Pod's namespace
NAMESPACE=\$(cat \${SERVICEACCOUNT}/namespace)

# Read the ServiceAccount bearer token
TOKEN=\$(cat \${SERVICEACCOUNT}/token)

# Reference the internal certificate authority (CA)
CACERT=\${SERVICEACCOUNT}/ca.crt

# Fill in the POD name
POD_NAME=${podName}
# Fill in the Container name
CONTAINER_NAME=shell

printf "[%s]\t[curl]\t" "\$(date)"
curl -L \
    --write-out "%{http_code}\t%{time_total}\t %{time_connect}\t%{time_appconnect}\t%{time_starttransfer}\n" \
    --silent \
    --output /dev/null \
    --max-time 5 \
    --cacert \${CACERT} \
    --header "Authorization: Bearer \${TOKEN}" \
    --header "X-Stream-Protocol-Version: v2.channel.k8s.io" \
    --header "X-Stream-Protocol-Version: channel.k8s.io" \
    --header "Sec-WebSocket-Key: SGVsbG8sIHdvcmxkIQ==" \
    --header "Sec-WebSocket-Version: 13" \
    --header "Host: \${KUBERNETES_SERVICE_HOST}" \
    --header "Origin: \${APISERVER}" \
    --include \
    --no-buffer \
    --header "Connection: Upgrade" \
    --header "Upgrade: websocket" \
    --http1.1 \
    "https://\${APISERVER}/api/v1/namespaces/\${NAMESPACE}/pods/\${POD_NAME}/exec?container=\${CONTAINER_NAME}&command=/bin/sh&command=-c&command=id&stdin=true&stderr=true&stdout=true&tty=true"
    sleep 5
done
"""
                    }
                }
            }
        }
    }
}