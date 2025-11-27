def call(Closure body) {
    currentBuild.result = 'SUCCESS'

    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    
    catchError {
        body()
    }

    def mailingList = config.mailingList ? config.mailingList : 'builds-list@example.com'
    
    (currentBuild.result != 'ABORTED') && node('master') {
        // send e-mail notifications for failed or unstable builds
        def to = emailextrecipients([
            [$class: 'CulpritsRecipientProvider'],
            [$class: 'DevelopersRecipientProvider'],
            [$class: 'RequesterRecipientProvider'],
            [$class: 'UpstreamComitterRecipientProvider']
        ]) + ' ' + mailingList
        step([$class: 'Mailer',
            notifyEveryUnstableBuild: true,
            recipients: to
        ])
    }
}
