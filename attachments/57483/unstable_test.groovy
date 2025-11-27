pipeline {
    agent any
    stages {
        stage('download') {
            steps {
                // rtDownload of unstable_test.xml from Artifactory
            }
        }
        stage("parallel") {
            parallel {
                stage('no1') {
                    steps {
                        sleep 2
                        xunit thresholds: [failed(unstableThreshold: '0')],
                            tools: [GoogleTest(
                                deleteOutputFiles: true,
                                failIfNotNew: false,
                                pattern: "*.xml",
                                skipNoTestFiles: false,
                                stopProcessingIfError: true)]
                    }
                }
                stage('no2') {
                    steps {
                        sleep 4
                        xunit thresholds: [failed(unstableThreshold: '0')],
                            tools: [GoogleTest(
                                deleteOutputFiles: true,
                                failIfNotNew: false,
                                pattern: "*.xml",
                                skipNoTestFiles: false,
                                stopProcessingIfError: true)]
                    }
                }
                stage('no3') {
                    steps {
                        sleep 6
                        xunit thresholds: [failed(unstableThreshold: '0')],
                            tools: [GoogleTest(
                                deleteOutputFiles: true,
                                failIfNotNew: false,
                                pattern: "*.xml",
                                skipNoTestFiles: false,
                                stopProcessingIfError: true)]
                    }
                }
            }
        }
    }
}
