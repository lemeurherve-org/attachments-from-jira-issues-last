
properties([
  parameters([
    string(name: 'MASTER_JOB_NAME', defaultValue: 'MyBranchIdentifier', description: '', trim: false), 
    string(name: 'MASTER_JOB_BUILD_NUMBER', defaultValue: '12345', description: '', trim: false),
    string(name: 'GCS_ARTIFACTS_LOCATION', defaultValue: 'gs://my-gcs-bucket', description: '', trim: false),
    string(name: 'PULL_ARTIFACTS_FROM_CLOUD', defaultValue: 'gcp', description: '', trim: false),
    string(name: 'UNIQUE_PARAMETER', defaultValue: '', description: '', trim: false)
    ])])
    
timestamps {
  node("ubuntu16") {
    try {

      stage('Verify worker is ready') {
        sh '''
            #!/bin/bash
            set -e

            filetocheck="/home/jenkins/worker-setup-done.txt"
            echo ""
            echo "Check for presence of ${filetocheck}"
            echo ""

            until [ -f ${filetocheck} ];
            do
              sleep 1
            done

        '''
      }

      stage('Pull Source Artifact from GCS') {
        sh '''
            #!/bin/bash
            set -e
            set -x
            . /home/jenkins/.profile

            export MASTER_JOB_BUILD_NUMBER_TO_USE=${MASTER_JOB_BUILD_NUMBER}
            echo "${MASTER_JOB_BUILD_NUMBER_TO_USE}" >> master-job-build-number-to-use.txt

            gsutil -m -q cp ${GCS_ARTIFACTS_LOCATION}/${MASTER_JOB_NAME}/${MASTER_JOB_BUILD_NUMBER_TO_USE}/source-tarball.tar.gz .
            tar xzf source-tarball.tar.gz

        '''
      }
      stage('Build for Ubuntu14') {
        sh '''#!/bin/bash
              set -e
              set -x
              . /home/jenkins/.profile
              export PYTHONUNBUFFERED=1
              export MASTER_JOB_BUILD_NUMBER_TO_USE=`cat master-job-build-number-to-use.txt`

              export OBFUSCATED_BUILD="true"
              echo "Doing some work.. yeah yeah yeah"
              sleep 900
              #ci/bin/build-compile/run_container_build_ubuntu14.sh

        '''
      }
    }
    catch (e) {
      echo 'Hit an error, so failing'
      // Set this because in some cases, like when the build is aborted, the downstream result & currentResult don't reflect it
      currentBuild.result = 'FAILURE'
      throw e
    }
    finally {
      stage('Post') {

        echo "currentBuild.currentResult: ${currentBuild.currentResult}"
        cleanWs()
      }
    }
  }
}
