node {

        stage("Create testDIR") {
            deleteDir()
            dir ('testDIR'){
                println("currdir:" + pwd())
                appendFile('testfile_drop430.txt',"this is TXT line1")
                appendFile('testfile_drop430.txt',"this is TXT line2")
                //
                appendFile('testfile_kis430.txt',"this is TXT line1")
                appendFile('testfile_kis430.txt',"this is TXT line2")
                //
                appendFile('testfile_kis421.txt',"this is TXT line1")
                appendFile('testfile_kis421.txt',"this is TXT line2")
                appendFile('testfile_kis421.txt',"this is TXT line3")
                //
                appendFile('testfile_kis430.log',"this is LOG line1")
                appendFile('testfile_kis430.log',"this is LOG line2")
                appendFile('testfile_kis430.log',"this is LOG line3")
                //
                appendFile('testfile_kis421.log',"this is LOG line1")
                appendFile('testfile_kis421.log',"this is LOG line2")
                appendFile('testfile_kis421.log',"this is LOG line3")
                appendFile('testfile_kis421.log',"this is LOG line4")
                appendFile('testfile_kis421.log',"this is LOG line5")
                //
                appendFile('testfile_kis430.mtc',"this is MTC line1")
                appendFile('testfile_kis421.mtc',"this is MTC line1")
                //
                appendFile('testfile_kis441.sol',"this is SOL line1")
                //
                bat "dir"
            }
            appendFile('testfile_kis431.mtc',"this is MTC line1")
            appendFile('testfile_kis422.mtc',"this is MTC line1")
            appendFile('testfile_kis401.mtc',"this is MTC line1")
            appendFile('testfile_kis401.sol',"this is SOL line1")

        }
       
        stage("ZIP the dir") {

            inclFilesGlob = "testD*/*.mtc , testD*/*.so , **/test*.log, **/test*.txt"
            println( " including files: ${inclFilesGlob}") 
            zip ( zipFile: 'testzip.zip', archive: false, glob: inclFilesGlob )
            bat "dir"
        }
       
        stage("Show content") {
            println "Analysis"
            bat "dir *.zip"

            println( " ZIP content plain:") 
            def nrFiles
            nrFiles = showZIP('testzip.zip', '')
            assert (nrFiles == 7) : println("[ERROR] : wrong size")  // should have 7 files in total

            nrFiles = showZIP('testzip.zip', '*.txt')
            assert (nrFiles == 0) : println("[ERROR] : wrong size")  // should be no files in root of ZIP
            
            nrFiles = showZIP('testzip.zip', '**/*.txt')
            assert (nrFiles == 3) : println("[ERROR] : wrong size")  // should have 3 TXT files in subdir
            
            nrFiles = showZIP('testzip.zip', '**/*.log')
            assert (nrFiles == 2) : println("[ERROR] : wrong size")  // should have 2 LOG files in subdir
            
            nrFiles = showZIP('testzip.zip', '**/*kis*.txt')
            assert (nrFiles == 2) : println("[ERROR] : wrong size")  // should have 2 KIS TXT files in subdir
            
            nrFiles = showZIP('testzip.zip', '**/*kis421*.txt')
            assert (nrFiles == 1) : println("[ERROR] : wrong size")  // should have 1 KIS421 TXT files in subdir

            nrFiles = showZIP('testzip.zip', 'testD*/*kis421*.txt')
            assert (nrFiles == 1) : println("[ERROR] : wrong size")  // should have 1 KIS421 TXT files in subdir

            nrFiles = showZIP('testzip.zip', '**/*.log,**/*.txt')
            assert (nrFiles == 5) : println("[ERROR] : wrong size")  // should have 5 LOG or TXT files in subdir
        }
       
}

def showZIP(String fileName, String pattern) {
    println( " ZIP content with pattern: '${pattern}'") 
    def zipContent = unzip (zipFile: fileName, glob: pattern, read: true )
    def counter = zipContent.size()
    return counter
}

def appendFile(String fileName, String line) {
    def current = ""
    if (fileExists(fileName)) {
        current = readFile fileName
    }
    writeFile file: fileName, text: current + "\n" + line
}