properties([
    parameters([
        string(name: "STRING_PARAM", defaultValue: "default", description: "A string parameter"),
        [$class: 'ChoiceParameter',
           choiceType: 'PT_CHECKBOX',
           description: "PARAM_1",
           name: "PARAM_1",
           script: [
               $class: 'GroovyScript',
               script: [
                   classpath: [],
                   sandbox: true,
                   script: """
                   Thread.sleep(2_000)
                   return ['1']
                   """
               ]
           ]
        ],
        [$class: 'ChoiceParameter',
           choiceType: 'PT_CHECKBOX',
           description: "PARAM_2",
           name: "PARAM_2",
           script: [
               $class: 'GroovyScript',
               script: [
                   classpath: [],
                   sandbox: true,
                   script: """
                   Thread.sleep(2_000)
                   return ['2']
                   """
               ]
           ]
        ],
        [$class: 'ChoiceParameter',
           choiceType: 'PT_CHECKBOX',
           description: "PARAM_3",
           name: "PARAM_3",
           script: [
               $class: 'GroovyScript',
               script: [
                   classpath: [],
                   sandbox: true,
                   script: """
                   Thread.sleep(2_000)
                   return ['3']
                   """
               ]
           ]
        ]
    ])
])

stage('Test') {
    echo "- STRING PARAM -"
    echo params.STRING_PARAM
    echo params.STRING_PARAM
    echo params.STRING_PARAM
    echo "- 1 -"
    echo params.PARAM_1
    echo params.PARAM_1
    echo params.PARAM_1
    echo "- 2 -"
    echo params.PARAM_2
    echo params.PARAM_2
    echo params.PARAM_2
    echo "- 3 -"
    echo params.PARAM_3
    echo params.PARAM_3
    echo params.PARAM_3
}

properties([
    buildDiscarder(logRotator(numToKeepStr: '5', daysToKeepStr: '5')),
    disableConcurrentBuilds(),
    parameters()
])
