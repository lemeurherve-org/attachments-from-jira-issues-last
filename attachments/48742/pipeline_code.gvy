Nodes_to_check= ['master','domvsaf1','SRV1625u','SRV4708','domvsafb1','domvsafb2']

Number_of_Cores=    [:]
Number_of_Cores['domvsafb1']=  6   // windows output has a maximum of 100%
Number_of_Cores['domvsafb2']=  4
freeMem=    [:]
load=       [:]
alarm=      false

fileHeader=""
data_disk=""
data_load=""

def CheckNode(nodename, free_directory, load_cmd, load_regex, load_regexGroup)
{
    try
    {
    def loadStr=   "0"

    stdout= sh returnStdout: true, script: "df "+free_directory
    freeMem[nodename]="invalid"
    RegExMatcher= stdout =~ "\\S+\\s+\\d+\\s+\\d+\\s+(\\d+)\\s+\\d+%\\s+\\S+"
    if (RegExMatcher)
        freeMem[nodename]=RegExMatcher.group(1)
    RegExMatcher=   null
    
    String stdout= sh returnStdout: true, script: load_cmd
    if (!isUnix())                                      // TODO: RegEx not yet working!
    {   temptxt= stdout[2..stdout.length()-1].trim()     // Windows output is quite malformed...
        stdout= ""
        temptxt.each
        {   if (it > 0)     // dirty hack to convert Unicode
                stdout+=it.toString()
        }
        //println stdout
        stdout= stdout.replaceAll("\\r\\n|\\r|\\n", " ");
    }
    RegExMatcher= stdout =~ load_regex
    if (RegExMatcher)
    {   //println stdout
        loadStr= RegExMatcher.group(load_regexGroup)
    }
    else
    {   println "warning: couldn't get load data from "+nodename
        println "RegEx:"+load_regex
        println "stdout:"+stdout
    }
    RegExMatcher=null   // matcher must be invalidated before the next step

    if (isUnix())
    {   load[nodename]=    loadStr.replace(",",".").toFloat()*100
    }
    else
    {   load[nodename]=    loadStr.toFloat()*Number_of_Cores[nodename]
    }

    println nodename+": "+load[nodename].toString()
    
    println "we have "+freeMem[nodename]+" bytes left on "+nodename

    }
    catch(Exception ex)
    {   println "caught exception "+ex
        error "--- THIS SHOULD NOT HAPPEN ---"
    }

    try
    {   freeMemInt= freeMem[nodename].toInteger()
        if (freeMemInt<5000000)
            alarm=  true
        else if (freeMemInt>50000000)   // limit for better plot scaling
            freeMemInt= 50000000
        data_disk+=   freeMemInt.toString()+","
        data_load+=   load[nodename].round().toString()+","
    }
    catch(Exception ex)
    {   println "caught exception "+ex
        alarm=  true
    }

    try
    {   fileOperations([fileDeleteOperation(excludes: '', includes: 'build_*.txt')])    // remove old test files
        sleep(1)
        archivedFile=   "build_"+BUILD_NUMBER+"_"+nodename+".txt"
        writeFile file: archivedFile, text: 'build#'+BUILD_NUMBER
        def exwsAll = exwsAllocate diskPoolId: 'ArchivedBuilds', path: "unmerged/"+JOB_NAME
        exws  (exwsAll)
        {	exwsPath=   pwd()
            fileOperations([fileDeleteOperation(excludes: '', includes: 'build_*'+nodename+'.txt')])    // remove old files
            sleep(1)
        }
    	fileOperations([fileCopyOperation(excludes: '', flattenFiles: false, includes: archivedFile, targetLocation: exwsPath)])
    
        sleep(1)
        exws  (exwsAll)
        {   if ( fileExists(archivedFile) != true)
            {   println "   error: missing archived file "+pwd()+"/"+archivedFile
                alarm=      true
            }
        }
    }
    catch(Exception ex)
    {   println "caught exception "+ex
        alarm=  true
    }

} // CheckNode()

try
{
Nodes_to_check.each
{
    fileHeader+=it+","
    stage(it)
    {
        sleep(1)
        node(it)
        {   sleep(1)
            if (isUnix())                                                  // groups 1/2/3 = 1/5/15 minutes average
            {   CheckNode(it, "/rsync", "uptime", ".*load average:\\s*(\\d*[,.]\\d*),\\s*(\\d*[,.]\\d*),\\s*(\\d*[,.]\\d*)", 2)
            }
            else
            {   CheckNode(it, "/cygdrive/e/jenkins/workspace/", "wmic cpu get loadpercentage", "\\s*LoadPercentage\\s*(\\d*)\\s*(\\d*)", 1)
            }
            sleep(1)
        } // node
    } // stage
} // Nodes_to_check.each

node('linux')
{
	fileContent=    fileHeader+"\n"+data_load 
    fileOperations([fileCreateOperation(fileContent: fileContent, fileName: 'data_load.csv')])
    plot(   csvFileName: 'work_ServerCheck__plot_load.csv',
            csvSeries: [[displayTableFlag: false, exclusionValues: '', file: 'data_load.csv', inclusionFlag: 'OFF', url: '']],
            exclZero: false, group: 'Server Health', keepRecords: false, logarithmic: false, numBuilds: '', style: 'line',
            title: "load", useDescr: false, yaxis: '', yaxisMaximum: '', yaxisMinimum: '')

    fileContent=    fileHeader+"\n"+data_disk 
    fileOperations([fileCreateOperation(fileContent: fileContent, fileName: 'data_disk.csv')])
    plot(   csvFileName: 'work_ServerCheck__plot_disk.csv',
            csvSeries: [[displayTableFlag: false, exclusionValues: '', file: 'data_disk.csv', inclusionFlag: 'OFF', url: '']],
            exclZero: false, group: 'Server Health', keepRecords: false, logarithmic: false, numBuilds: '', style: 'line',
            title: "disc space", useDescr: false, yaxis: '', yaxisMaximum: '', yaxisMinimum: '')

	archiveArtifacts('*.csv')

    if ( (alarm==true) ) // (true==false) &&  ... disabled during vacation
    {   mailText=   'warning: check servers!\n\n'
        freeMem.each
        {   mailText+=  it.key+": "+it.value+" Bytes\n"
        }
        println "sending EMail with following text: \n"+mailText
        sleep(2)
        try
        {    mail bcc: '', body: mailText, cc: '', from: 'Jenkins_domvsaf2@we.com', replyTo: '', subject: 'warning from '+env.JOB_NAME, to: 'me@we.com'
        }
        catch (err)
        {   println "error: sending mail failed with "+err
        }
    }

	step([$class: 'LogParserPublisher', failBuildOnError: true, parsingRulesPath: '/opt/ToolsBuildpy/JenkinsTools/output_parser_rule_std.txt',
										showGraphs: true, unstableOnWarning: true, useProjectRule: false])
} // node
} // try
catch(Exception ex)
{   println "caught exception "+ex
    error "--- PIPELINE TERMINATED - this should not happen! ---"
}
