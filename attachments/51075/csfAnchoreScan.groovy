//###########################################################################//
//                                                                           //
//                         (c) Copyright 2020                                //
//                                                                           //
//###########################################################################//
def call(Map params) {
  script {
    println 'csf-jenkinslib/csfAnchoreScan( ' + params + ' )'
    if ( currentBuild.result == 'FAILURE' ) {
      csfColor color: 'RED', msg: 'Warning: csfAnchoreScan entry has unexpected job result == FAILURE'
    }
    def Boolean errorFound = false
    def defaultParams = [ imagesList: [], bailOnFail: false, bailOnPluginFail: false, archiveAnchoreReport: false, engineRetries: '500' ]
    def usage = 'USAGE: csfAnchoreScan imagesList:["<image1:tag1>" [, "<image2:tag2>", ...], bailOnFail: true, bailOnPluginFail: true, archiveAnchoreReport: false, engineRetries: \'400\''
    params.each {
      if (!(defaultParams.containsKey(it.key))) {
        errorFound = true; csfColor color: 'RED', msg: 'Error: ' + it.key + ' is an unknown parameter which is invalid'
      }
    }
    if (!params.imagesList) {
      errorFound = true; csfColor color: 'RED', msg: 'Error: image mandatory parameter is null/empty which is invalid'
    }
    if (params.imagesList.getClass() != ArrayList) {
      errorFound = true; csfColor color: 'RED', msg: 'Error: imagesList mandatory parameter unexpected type is: ' + params.imagesList.getClass() + ' but expecting ArrayList'
    }
    if ( params.containsKey('bailOnFail') && params.bailOnFail.getClass() != Boolean ) {
      errorFound = true; csfColor color: 'RED', msg: 'Error: bailOnFail parameter unexpected type ' + params.bailOnFail.getClass() + ' but expecting Boolean'
    }
    if ( params.containsKey('bailOnPluginFail') && params.bailOnPluginFail.getClass() != Boolean ) {
      errorFound = true; csfColor color: 'RED', msg: 'Error: bailOnPluginFail parameter unexpected type ' + params.bailOnPluginFail.getClass() + ' but expecting Boolean'
    }
    if ( params.containsKey('archiveAnchoreReport') && params.archiveAnchoreReport.getClass() != Boolean ) {
      errorFound = true; csfColor color: 'RED', msg: 'Error: archiveAnchoreReport parameter unexpected type ' + params.archiveAnchoreReport.getClass() + ' but expecting Boolean'
    }
    if ( params.containsKey('engineRetries') && params.engineRetries.getClass() != String ) {
      errorFound = true; csfColor color: 'RED', msg: 'Error: engineRetries parameter unexpected type ' + params.engineRetries.getClass() + ' but expecting String'
    }
    defaultParams << params
    if (errorFound) {
      currentBuild.result = 'FAILURE'; error('csfAnchoreScan parameter checking failure listed above!\n' + usage)
    }
    writeFile file: 'anchore_images', text: defaultParams.imagesList.join("\n")
    try{
      anchore name: 'anchore_images', engineurl: 'https://anchore.int.net.myCom.com/v1', engineCredentialsId: 'csf_anchore_jenkins_lib_id',
              forceAnalyze: true, autoSubscribeTagUpdates: false, bailOnFail: defaultParams.bailOnFail, bailOnPluginFail: defaultParams.bailOnPluginFail, engineRetries: defaultParams.engineRetries
    } catch(e) {
      echo "Anchore scan failed: " + e.toString()
    }
    if(defaultParams.archiveAnchoreReport){
      archiveArtifacts artifacts: 'anchore*.json', allowEmptyArchive: true
    }
  }
}
