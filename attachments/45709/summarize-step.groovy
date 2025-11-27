            stage('Summarize') {
                // Collect Delphi compiler and FixInsight warnings
                recordIssues(
                    enabledForFailure: true,
                    failedNewHigh: 0,
                    failedNewNormal: 1,
                    failedNewLow: 2,
                    tools: [
                        groovyScript(
                            parserId: 'delphi-compiler', 
                            pattern: 'BuildTools/*.Delphi.norm.log', 
                            reportEncoding: 'UTF-8'
                        ),
                        groovyScript(
                            parserId: 'delphi-fixinsight',
                            pattern: 'BuildTools/*.FixInsight.norm.log',
                            reportEncoding: 'UTF-8'
                        )
                    ]
                )

                // Collect open tasks
                recordIssues(
                    enabledForFailure: true,
                    failedNewHigh: 0,
                    tools: [
                        taskScanner(
                            includePattern: '**/*.pas, **/*.dpr',
                            excludePattern: 'Packages/**',
                            ignoreCase: true,
                            isRegularExpression: true,
                            sourceCodeEncoding: 'UTF-8',
                            highTags: '(?i)^.*(?://|\\{|\\(\\*)\\s*(FIXME)(?:\\s|:|-)+(.*)$',
                            normalTags: '(?i)^.*(?://|\\{|\\(\\*)\\s*(TODO)(?:\\s|:|-)+(.*)$',
                            lowTags: '(?i)^.*(?://|\\{|\\(\\*)\\s*(##[A-Z]{2})(?:\\s|:|-)+(.*)$'
                        )
                    ]
                )

                // Publish test results
                step([$class: 'XUnitBuilder',
                    thresholds: [
                        [$class: 'SkippedThreshold', failureThreshold: '0'],
                        [$class: 'FailedThreshold', failureThreshold: '0']
                    ],
                    tools: [[$class: 'NUnitJunitHudsonTestType', pattern: '**/*.dunit.xml']]
                ])

                // Publish code coverage
                // ToDo: find pipeline plugin that imports Emma log files or modify
                //       DelphiCodeCoverage to create a coverage file format that can be imported
            }
