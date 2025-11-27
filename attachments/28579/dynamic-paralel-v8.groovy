import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.*

testsToRun = [ "Backstep_3D_Fully_Symmetric_Orthoganol_Grid","SupersonicPlate", "SupersonicWedge", "SupersonicCone", "HereausTest", "180_Deg_Bend", '1', '2', '3', '4', '5', '6', '7', '8', '9', '10' ]
testsExecuted = []

masterNodePath = 'automatically set to a proper value by this script' // used by slaves to create coverage .info files for the master node to merge
// gcov cross coverage params
gcovPrefixStrip = 0  // automatically set to a proper value by this script

thingsToClean = ''
for( file in testsToRun ) {
      thingsToClean += file
      thingsToClean +=' '  
}  
   
node('master') {
   ws {
       echo "master node workspace path b4 paralel is: " + pwd()

       masterNodePath = pwd()
       echo "master node workspace path is: " + masterNodePath
       gcovPrefixStrip = calculateGcovgcovPrefixStrip( masterNodePath )
       echo "master node path depth (GCOV_PREFIX_STRIP): " + gcovPrefixStrip
     
       sh 'touch master'
       sh 'ls -l . -a'
       
       echo "master node workspace path after paralel is: " + pwd()
       sh 'ls -l . -a'       
       
   } // end ws / master
} // end node master



        parallel( slave1: {

               node('ubuntu-slave') {
                    ws {
                     executeForCoverage ( testsToRun[1] )
                    } // end ws
                }     // end node

        }, slave2: {  

               node('ubuntu-slave') {
                    ws {
                     executeForCoverage ( testsToRun[2] )
                    } // end ws
                }     // end node

        }, slave3: {

               node('ubuntu-slave') {
                    ws {
                     executeForCoverage ( testsToRun[3] )
                    } // end ws
                }     // end node

        }, slave4: {

               node('ubuntu-slave') {
                    ws {
                     executeForCoverage ( testsToRun[4] )
                    } // end ws
                }     // end node

        }, slave5: {

               node('ubuntu-slave') {
                    ws {
                     executeForCoverage ( testsToRun[5] )
                    } // end ws
                }     // end node

        }, slave6: {

               node('ubuntu-slave') {
                    ws {
                     executeForCoverage ( testsToRun[6] )
                    } // end ws
                }     // end node

        }) // end paralel        




/* */
def executeForCoverage( test ) {
   
   if( test.isEmpty() ) {
       echo "ERROR: Never execute here at " + pwd()
       return 0  // exit
   }
  
   cleanWS()

   echo "Running test ${test} in slave node: " + pwd()
     
   setCrossProfilingEnvVars( pwd(), gcovPrefixStrip )
   echo "Verbosing cross profiling b4 test ${test} in slave node: " + pwd()   
   verboseCrossProfilingEnvVars()   
   sh "touch ${test}"
   sh 'ls -l . -a'
   
   if (test == 'HereausTest' || test == '7' )
     input "paused indefinely for ${test}"
     
   echo "Verbosing cross profiling after test ${test} in slave node: " + pwd()   
   verboseCrossProfilingEnvVars()
}

/* */
def cleanWS() {
        
    echo "************* " + pwd() + " before cleaning ***********************"
    sh 'ls -l . -a'
    sh "rm -rf ${thingsToClean}"    
    echo "************* " + pwd() + " after cleaning ***********************"
    sh 'ls -l . -a'
}

/* */
def calculateGcovgcovPrefixStrip( masterNodePath ) {

   def fullPathDepthPattern = /(\/.)/
   fullPathDepthPattern instanceof Pattern

    // this does work
    List<String> matches = masterNodePath.findAll( fullPathDepthPattern )
    matches.size()  // the last evaluated value is the return value
}

/* Read: https://gcc.gnu.org/onlinedocs/gcc/Cross-profiling.html */
def setCrossProfilingEnvVars( thisNodesPath, stripValue ) {
    // Properties of this variable will be environment variables on the current node
    env.GCOV_PREFIX = thisNodesPath
    env.GCOV_PREFIX_STRIP = stripValue   // comes from master object files path depth
}

def verboseCrossProfilingEnvVars() {
   echo "verboseCrossProfilingEnvVars start"
   // echo "verboseCrossProfilingEnvVars: at slave node: " + pwd() + "env.GCOV_PREFIX: " + env.GCOV_PREFIX + " env.GCOV_PREFIX_STRIP : " + env.GCOV_PREFIX_STRIP
   sh '''pwd
   echo $USER
   echo $GCOV_PREFIX
   echo $GCOV_PREFIX_STRIP'''
   echo "verboseCrossProfilingEnvVars end"   
}