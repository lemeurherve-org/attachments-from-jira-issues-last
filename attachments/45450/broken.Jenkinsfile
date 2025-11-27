pipeline {
    agent {
        node {
            label 'OSX'
            customWorkspace 'coach-ios'
        }
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }
    stages {
        stage('Build') {
            steps {
                xcodeBuild appURL: '',
                    assetPackManifestURL: '',
                    buildDir: '',
                    bundleID: 'com.company.careercoach',
                    bundleIDInfoPlistPath: 'Career Coach/Info.plist',
                    cfBundleShortVersionStringValue: '',
                    cfBundleVersionValue: '',
                    changeBundleID: true,
                    configuration: 'Release',
                    developmentTeamID: '',
                    developmentTeamName: 'Company Holding Ltd.',
                    displayImageURL: '',
                    fullSizeImageURL: '',
                    ipaExportMethod: 'development',
                    ipaName: 'career-coach',
                    ipaOutputDirectory: '',
                    keychainName: '',
                    keychainPath: '',
                    keychainPwd: '',
                    logfileOutputDirectory: '',
                    provisioningProfiles: [[provisioningProfileAppId: '', provisioningProfileUUID: '']],
                    sdk: '',
                    symRoot: '',
                    target: 'Career Coach',
                    thinning: '',
                    xcodeProjectFile: '',
                    xcodeProjectPath: 'Career Coach',
                    xcodeSchema: 'Career Coach',
                    xcodeWorkspaceFile: 'Career Coach',
                    xcodebuildArguments: ''
            }
        }
        stage('Archive') {
            steps {
                archiveArtifacts 'Career Coach/build/Release-iphoneos/career-coach.ipa'
            }
        }
        stage('Deploy') {
            steps {
                sh '/usr/local/bin/cfgutil --foreach remove-app com.company.careercoach && /usr/local/bin/cfgutil --foreach install-app "Career Coach/build/Release-iphoneos/career-coach.ipa"'
            }
        }
    }
    post {
        failure {
            slackSend(color: 'danger',channel: "#careercoachapp",message: "${env.JOB_NAME} - <${env.BUILD_URL}|Build Failed>")
        }
        success {
            slackSend(color: 'good',channel: "#careercoachapp",message: "${env.JOB_NAME} - <${env.BUILD_URL}|Built Test App>")
        }
    }
}
