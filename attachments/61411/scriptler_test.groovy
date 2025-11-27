pipelineJob('scriptler-test') {
    displayName('Scriptler Test')
    description('Active Choice + Scriptler + Job DSL Test')

    parameters {
        activeChoiceParam('P1') {
            choiceType('SINGLE_SELECT')
            filterable()
            groovyScript {
                script("return ['A', 'B']")
                fallbackScript("return ['Error']")
            }
        }
        activeChoiceParam('P2') {
            choiceType('SINGLE_SELECT')
            filterable()
            groovyScript {
                script("return ['C', 'D']")
                fallbackScript("return ['Error']")
            }
        }
        reactiveChoice {
            name('P3')
            choiceType('PT_SINGLE_SELECT')
            referencedParameters('P1,P2')
            script {
                scriptlerScript {
                    scriptlerBuilder {
                        builderId('scriptler-test')
                        scriptId('Cascade.groovy')
                        propagateParams(true)
                        parameters {
                            parameter { name 'P1'; value '$P1' }
                            parameter { name 'P2'; value '$P2' }
                        }
                        isSandboxed(true)
                    }
                }
            }
            randomName('')
            filterable(false)
            filterLength(1)
        }
    }

    definition {
        cps {
            script('''println "P1: $P1, P2: $P2, P3: $P3"''')
            sandbox(true)
        }
    }
}
