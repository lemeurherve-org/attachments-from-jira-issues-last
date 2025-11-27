job('test.job.sets.credentials') {
    description('Notice credentials do get set here')
    label('osx')
    scm {
        hg('myhgurl') {
        	branch('mybranchname')
            clean(true)
            credentials('my-credentials-string-here')
        }
    }
}