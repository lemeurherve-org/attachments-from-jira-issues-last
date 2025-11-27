podTemplate(containers: [
    containerTemplate(name: 'jnlp', image: 'jenkins/jnlp-slave:3.27-1-alpine'),
]) {
    node(POD_LABEL) {
        stage('a') {
            echo 'hello world!'
        }
    }
}

podTemplate(inheritFrom: 'default-java', containers: [
    containerTemplate(name: 'jnlp', image: 'jenkins/jnlp-slave:3.27-1-alpine'),
]) {
    node(POD_LABEL) {
        stage('b') {
            echo 'hello world!'
        }
    }
}