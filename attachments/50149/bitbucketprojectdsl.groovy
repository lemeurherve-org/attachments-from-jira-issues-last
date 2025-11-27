organizationFolder("${projectName}") {
    description("Includes jobs for repositories in ${projectName} project")
    displayName("${projectName}")

    organizations { // organizations
        bitbucket {
            serverUrl('<bitbucket server>')
            credentialsId('<bitbucket user>')
            repoOwner("${projectSlug}")
            if (reposToExclude?.trim()) {
                traits {
                    sourceWildcardFilter {
                        includes("*")
                        excludes("${reposToExclude}")
                    }
                }
            }
        } // bitbucket
    } // organizations

    // The rest is taken from: https://github.com/jenkinsci/configuration-as-code-plugin/blob/master/demos/jobs/bitbucket.yaml

    // "Traits" ("Behaviours" in the GUI) that are NOT "declarative-compatible"
    // For some 'traits, we need to configure this stuff by hand until JobDSL handles it
    // https://issues.jenkins.io/browse/JENKINS-45504
    configure { node ->
        def traits = node / navigators / 'com.cloudbees.jenkins.plugins.bitbucket.BitbucketSCMNavigator' / traits
        traits << 'com.cloudbees.jenkins.plugins.bitbucket.BranchDiscoveryTrait' {
            strategyId('1')
        }
        traits << 'com.cloudbees.jenkins.plugins.bitbucket.OriginPullRequestDiscoveryTrait' {
            strategyId('2')
        }
        traits << 'com.cloudbees.jenkins.plugins.bitbucket.SSHCheckoutTrait' {
            credentialsId('bitbucket-repo-key')
        }
        traits / 'jenkins.plugins.git.traits.CleanAfterCheckoutTrait' / extension {
            'class' 'hudson.plugins.git.extensions.impl.CleanAfterCheckout'
        }
        traits / 'jenkins.plugins.git.traits.CleanBeforeCheckoutTrait' / extension {
            'class' 'hudson.plugins.git.extensions.impl.CleanBeforeCheckout'
        }
        traits / 'jenkins.plugins.git.traits.IgnoreOnPushNotificationTrait'
    }

    configure { node ->
        def buildStrategies = node / 'buildStrategies' / 'jenkins.branch.buildstrategies.basic.AnyBranchBuildStrategyImpl' / 'strategies'
        buildStrategies / 'jenkins.branch.buildstrategies.basic.ChangeRequestBuildStrategyImpl' {
            'ignoreTargetOnlyChanges' true
            'ignoreUntrustedChanges' false
        }
        def namedBranchStrategies = buildStrategies / 'jenkins.branch.buildstrategies.basic.AllBranchBuildStrategyImpl' / 'strategies'
        namedBranchStrategies / 'jenkins.branch.buildstrategies.basic.SkipInitialBuildOnFirstBranchIndexing'
        def filters = namedBranchStrategies / 'jenkins.branch.buildstrategies.basic.NamedBranchBuildStrategyImpl' / 'filters'
        filters << 'jenkins.branch.buildstrategies.basic.NamedBranchBuildStrategyImpl_-ExactNameFilter' {
            'name' 'develop'
            'caseSensitive' false
        }
        filters << 'jenkins.branch.buildstrategies.basic.NamedBranchBuildStrategyImpl_-ExactNameFilter' {
            'name' 'master'
            'caseSensitive' false
        }
        filters << 'jenkins.branch.buildstrategies.basic.NamedBranchBuildStrategyImpl_-WildcardsNameFilter' {
            'includes' 'release-* rc-*'
            'excludes' ''
            'caseSensitive' false
        }
    }

    // "Project Recognizers"
    projectFactories {
        workflowMultiBranchProjectFactory {
            scriptPath 'Jenkinsfile'
        }
    }

    // "Orphaned Item Strategy"
    orphanedItemStrategy {
        defaultOrphanedItemStrategy {
            pruneDeadBranches(true)
            daysToKeepStr('1')
            numToKeepStr('5')
        }
        discardOldItems {
            daysToKeep(1)
            numToKeep(7)
        }
    }

    properties{
        authorizationMatrix {
            permissions(projectPermissions)
        }
    }

    // "Scan Organization Folder Triggers" : 1 day
    // We need to configure this stuff by hand because JobDSL only allow 'periodic(int min)' for now
    configure { node ->
        node / triggers / 'com.cloudbees.hudson.plugins.folder.computed.PeriodicFolderTrigger' {
            spec('H H * * *')
            interval(86400000) // 1 day
        }
    }
} // organizationFolder