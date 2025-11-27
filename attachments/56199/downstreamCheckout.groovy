
// hudson.util.DescribableList => scm.extensions

// Get the commit from the parent, and remove the BuildChooserSetting
// which is overwriting the ability to specify a commit
// Newer versions of Git plugin (>4.7.1) might fix this.
// CANNOT ADD TO EXTENSIONS
def call(Map options = [:]) {
    gitCommit = scm.branches

    // If an upstreamBuild was a cause, we have access to its variables
    // which MUST be set on the environment before being called.
    if (!currentBuild.upstreamBuilds.isEmpty()) {
        upstreamBuildObj =  currentBuild.upstreamBuilds.first()
        gitCommit = [[name: upstreamBuildObj.getBuildVariables().get('GIT_COMMIT')]]
    }

    // Would love to split this up, but any attempt at locally editing the extensions
    // or saving to a local variable causes Groovy to fail with NotSerializableException
    if( options.extensions ) {
        checkout([
            $class: 'GitSCM',
            branches: gitCommit,
            extensions: removeBuildChooserExtension(options.extensions),
            userRemoteConfigs: scm.userRemoteConfigs
        ]).each { k,v -> env.setProperty(k, v) }

    } else {
        checkout([
            $class: 'GitSCM',
            branches: gitCommit,
            extensions: removeBuildChooserExtension(scm.extensions),
            userRemoteConfigs: scm.userRemoteConfigs
        ]).each { k,v -> env.setProperty(k, v) }
    }
}

@NonCPS
def removeBuildChooserExtension(exts) {
    // Remove the BuildChooserSetting extension
    // This seems to overwrite the commit sent in the branches key, and might just be the entire
    // reason that downstream jobs don't work like they should
    // This was causing the latest SHA to be built, regardless of what the upstream build was doing
    // Stolen from
    // https://issues.jenkins.io/browse/JENKINS-64000?focusedCommentId=409105&page=com.atlassian.jira.plugin.system.issuetabpanels%3Acomment-tabpanel#comment-409105
    return exts.findAll{ !(it instanceof hudson.plugins.git.extensions.impl.BuildChooserSetting) }
}
