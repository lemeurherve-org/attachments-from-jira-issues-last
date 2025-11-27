import jenkins.model.Jenkins
import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.plugin.JenkinsJobManagement

String organizationTemplateFile = "/var/jenkins_home/init.groovy.d/organization_templates/organizationJobTemplate.groovy"

try {
    List<String> list = getOrganizationList()

    for (String organization : list) {
        String orgName = organization.trim()
        def jobDslScript = new File(organizationTemplateFile)
        def workspace = new File('.')
        def jobText = jobDslScript.text.replaceAll('###REPOOWNER###', orgName)

        def jobManagement = new JenkinsJobManagement(System.out, [:], workspace)
        new DslScriptLoader(jobManagement).runScript(jobText)
    }
} catch (any) {
    if (System.getenv("JENKINS_TYPE") == "TEAM") {
        println('===> DEBUG <===')
        println('Exception caught!')
        print(any)
        println('Restarting')
        println('===> DEBUG <===')
        sleep(5000)
        Jenkins.instance.restart()
    }
}

public List<String> getOrganizationList() {
    String gitSeedURL = System.getenv("GIT_SEED")
    if (gitSeedURL == null || gitSeedURL.length() == 0) {
        gitSeedURL = "sample-applications"
    }
    String[] organizations = gitSeedURL.split(',')
    return Arrays.asList(organizations)
}
