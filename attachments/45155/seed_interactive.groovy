def tests = [[scenarioName: 'S 4.4 N NAS Interactive Approveall', description:'4.2.N Interactive test', testId:188, testInstanceId:70, hours:1, minutes:20, groovyFile:'test\\data_cleanup\\approval_rollback.groovy', groovyParams:'cdlpftgf7-scan.es.ad.adp.com:1521 cdlpftgf7-scan.es.ad.adp.com:1521 ece12y ece12y SR'],
			[scenarioName: 'S.4.4M MAS interactive Approve All', description:'4.2.N Interactive test', testId:193, testInstanceId:72, hours:4, minutes:0, groovyFile:'test\\data_cleanup\\approval_rollback.groovy', groovyParams:'cdlpftgf7-scan.es.ad.adp.com:1521 cdlpftgf7-scan.es.ad.adp.com:1521 ece12y ece13y WF'],
			[scenarioName: 'S.4.5N NAS Combined', description:'4.2.N Interactive test', testId:192, testInstanceId:71, hours:3, minutes:0, groovyFile:'', groovyParams:''],
			[scenarioName: 'S4.5M MAS Combined', description: '4.2.M Interactive test', testId:183, testInstanceId:69, hours:3, minutes:0, groovyFile:'test\\data_cleanup\\mas_manual_edit_rollback.groovy', groovyParams:'test\\s.4.1_13.M\\masUsers_all.csv'],
			[scenarioName: 'beaconhill_prodsupport', description:'Beacon Hill production support Interactive test', testId:208, testInstanceId:91, hours:1, minutes:0, groovyFile:'', groovyParams:''],
			[scenarioName: 'ASN_prodsupport', description:'ASN production support Interactive test', testId:210, testInstanceId:90, hours:1, minutes:0, groovyFile:'', groovyParams:''],
			[scenarioName: 'grifols_prodsupport', description:'Grifols production support Interactive test', testId:212, testInstanceId:92, hours:1, minutes:0, groovyFile:'', groovyParams:'']]


tests.each { test ->

	def jobId = "${test.scenarioName}"
	job(jobId) { 
		description("This job executes test ${test.scenarioName}.")
		
		//Add a mercurial scm source
		scm {
			//hg('http://scm-oba.workscape.net/aca/performance')
			git("https://bitbucket.es.ad.adp.com/scm/aca/aca-perf.git")
		}
		steps {
			// Add the environment variable CLASSPATH to include jars and other classes in the build
			environmentVariables{
				env('CLASSPATH', 'test\\data_cleanup\\;test\\data_cleanup\\*')
			}
			
			if(test.groovyFile != ''){
				//Run the groovy script file with parameters
				groovyScriptFile(test.groovyFile) {
					groovyInstallation('Groovy 2.4.6')
					scriptParam(test.groovyParams)
				}
			}
		}
		configure { project ->
	        project / builders << {'com.hp.application.automation.tools.run.PcBuilder'(plugin: 'hp-application-automation-tools-plugin@5.5') {
	        //{'com.microfocus.application.automation.tools.settings.OctaneServerSettingsBuilder_-OctaneDescriptorImpl'(plugin: 'hp-application-automation-tools-plugin@5.5') {
	        
					'pcModel' {
						'pcServerName' 'cdlpfpcsvr04.es.ad.adp.com'
						'almUserName' 'jenkinsACA'
						//'almPassword' 'adpaca'
						//'almPassword'(class:"com.hp.application.automation.tools.model.SecretContainerImpl"){
						//	'__secret' 'SVgLEvbkajdZLrVegImgVQ=='
						//}
						'almPassword'(class:"com.hp.application.automation.tools.model.SecretContainerImpl"){
							'__secret' 'Dsr90dh/+d6p3F8Mi5p4897Scnz/rUOsGF8TEsxzRxI='
						}
						'almDomain' 'NAS'
						'almProject' 'ACA'
						'testId' test.testId
						'testInstanceId' test.testInstanceId
						'timeslotDuration'{
						  'Hours' test.hours
						  'Minutes' test.minutes
						}
						'postRunAction' 'COLLATE_AND_ANALYZE'
						'vudsMode' 'false'
						delegate.description test.description
					}
					'statusBySLA' 'false'
					'runId' 0																								
				}
			}
		}
		configure { project ->	
			project / publishers << {'com.hp.application.automation.tools.results.RunResultRecorder'(plugin:"hp-application-automation-tools-plugin@4.0.1"){
						'__resultsPublisherModel'{
							'archiveTestResultsMode' 'PUBLISH_HTML_REPORT'
						}
					}
				}
		}
		publishers{
			downstreamParameterized {
	            trigger("Report for ${jobId}") {
	                condition('SUCCESS')
	            }
        	}
		}

	}

	job("Report for ${jobId}") { 
		description("This job generated custom report for the test ${test.scenarioName}.")
		//Add a mercurial scm source
		scm {
			//hg('http://scm-oba.workscape.net/aca/performance-reporting')
			git("https://bitbucket.es.ad.adp.com/scm/aca/aca-perf-reporting.git")
		}
		steps {
			copyArtifacts(jobId) {
	            includePatterns('performanceTestsReports\\*\\Report\\*.xls')
	            targetDirectory('performanceReports')
	            flatten()
	            fingerprintArtifacts()
	            buildSelector {
	                upstreamBuild(false)
	            }
	        }

	        gradle {
				gradleName("gradle 2.12")
				useWrapper(false)
				description("Executes the groovy script to convert xls files to csv formatted files")
	            tasks('convertToCSV')
	        }
	        batchFile("if exist performanceReports\\Average_transaction_response_time.csv copy performanceReports\\Average_transaction_response_time.csv AverageResponseTimes.csv")
		}

		publishers {
				// archive the log files so that they are attached to the workspace
				publishHtml{
					report('.') {
						reportName('HTML Report')
						reportFiles('PerformanceReports.html')
						keepAll()
						alwaysLinkToLastBuild()
					}
				}
			}

	}
}