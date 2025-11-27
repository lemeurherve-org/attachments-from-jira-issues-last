#!groovy

import com.cloudbees.groovy.cps.NonCPS

/**
 * Return TRUE if the current node is running Cygwin
 */
def isCygwin() {
    return env.OSTYPE == 'cygwin'
}

/**
 * Return TRUE if the current node is a Unix-like node, ie. a true Unix or Cygwin
 */
def isUnixLike() {
    return isUnix() || isCygwin()
}

/**
 * Return TRUE if the 'scm' variable refers to a Git configuration
 */
def isGit() {
    return (scm instanceof hudson.plugins.git.GitSCM)
}

/**
 * Return TRUE if the 'scm' variable refers to a SVN configuration
 */
def isSvn() {
    return (scm instanceof hudson.scm.SubversionSCM)
}

/**
 *
 */
def appendArgs(current, newArgs) {
    if (!newArgs) {
        return current
    }

    def result
    if (current) {
        result = current + ' '
    }
    else {
        result = ''
    }
    return result + newArgs
}

def failBuild(String message) {
    log.error("Error: ${message}")
    error(message)
}

def verify(boolean condition, String message) {
    if (!condition) {
        failBuild(message)
    }
}

def verify(Closure funct, String message) {
    if (funct() != true) {
        failBuild(message)
    }
}


/**
 * Check whether a Jenkins plugin is installed and active.
 */
def isPluginActive(String pluginId) {
    return Jenkins.instance.pluginManager.plugins.find { p -> p.isActive() && p.getShortName() == pluginId }
}

/**
 * Fail the build if the required Jenkins plugin is not active
 */
@NonCPS
def requirePlugins(String... pluginIds) {
    def missing = []
    pluginIds.each { p -> if(!isPluginActive(p)) missing.add(p) }
    if (!missing.empty) {
        failBuild("The following required Jenkins plugins are missing or not active: ${missing}")
    }
}