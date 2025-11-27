properties([
        parameters([
                choice(choices: ['dev', 'test'], description: '', name: 'STAGE'),
				string(name: 'SNAPSHOT_NAME', defaultValue: 'snapshot', description: '')
        ])
])

node {

    // set jenkins job description
    setDescription()

    try {

        // Block build if certain jobs are running.
        blockOn('.*deploy.*') {
            // Possible values are 'GLOBAL' and 'NODE' (default).
            blockLevel('GLOBAL')
            // Possible values are 'ALL', 'BUILDABLE' and 'DISABLED' (default).
            scanQueueFor('DISABLED')
        }

        // replace blanks with underscores
        env.SNAPSHOT_NAME = env.SNAPSHOT_NAME.replace(" ", "_")

        shutdownPods()
        dbAction()
        repoAction()
        startPods()

        currentBuild.result = 'SUCCESS'

    } catch (Exception err) {
        println err
        currentBuild.result = 'FAILURE'
    }

    println "RESULT: $currentBuild.result"

    stage('Post build actions') {
        step([$class: 'hudson.plugins.chucknorris.CordellWalkerRecorder'])
        // Mailer notification
        step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: 'noreply@mail.com', sendToIndividuals: false])
    }

}