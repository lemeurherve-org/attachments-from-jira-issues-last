node('unix') {
    def groovyHome
    stage('Get tools') { // for display purposes
        echo 'Obtaining Groovy'
        groovyHome = tool 'groovy-2.3.9'
    }
    final String otherBuildSnapshotName
    final String otherBuildBuildNumber
    try {
        final String groovyScriptName = 'tmp.groovy'
        stage('LatestSnapshot: Make Script') {
            final String groovyScriptContents = '''/*redected for clarity - creates file "snapshot.properties"*/'''
            writeFile([ file: groovyScriptName, text: groovyScriptContents ])
        }
        stage('LatestSnapshot: Run Script') {
            withCredentials([usernamePassword(credentialsId: '01234567-89ab-cdef-0123-456789abcdef', usernameVariable: 'USER', passwordVariable: 'PWD')]) {
                sh "'${groovyHome}/bin/groovy' '${groovyScriptName}'"
            }
        }
        stage('LatestSnapshot: Read Results') {
            final String snapshotPropertiesAsString = readFile('snapshot.properties')
            final snapshotPropertiesReader = new java.io.StringReader(snapshotPropertiesAsString)
            final snapshotProperties = new Properties()
            snapshotProperties.load(snapshotPropertiesReader)
            otherBuildSnapshotName = snapshotProperties.getProperty('productUnderTest.SNAPSHOT')
            otherBuildBuildNumber = snapshotProperties.getProperty('productUnderTest.SNAPSHOT_JOB_BUILD_NUMBER')
            echo "snapshot name = ${otherBuildSnapshotName}"
            echo "build number  = ${otherBuildBuildNumber}"
        }
    } finally {
        stage('Archive Artifacts') {
            archiveArtifacts '*'
        }
    }
}