#!/usr/bin/env groovy

/**
 * Copy VHD to destination acct & create managed image with SPN defined in
 * Jenkins Credentials store
 */

def call (params) {
  def rg              = params.rg
  def destAccount     = params.destAccount
  def storageCredId   = params.storageCredId
  def vhdBlob         = params.vhdBlob
  def vhdUri          = params.vhdUri
  def vhdCont         = params.vhdCont
  def imageName       = params.imageName

  def tag_created     = params.tag_created ?: env.BUILD_TIMESTAMP
  def tag_role        = params.tag_role ?: 'BaseImage'
  def tag_creator     = params.tag_creator ?: 'Auto'
  def tag_info        = params.tag_info ?: env.BUILD_TAG
  def sleepTime       = params.sleepTime ?: 10

  //Copy VHD to DevOps account asynchronously
  withCredentials([string(credentialsId: storageCredId, variable: 'ARM_KEY')]) {
    sh "az storage blob copy start \
    --source-uri '${vhdUri}' \
    --destination-container '${vhdCont}' \
    --destination-blob '${vhdBlob}' \
    --account-key $ARM_KEY \
    --account-name '${destAccount}'"
  
    //Blob xfers are ~10 minutes, so sleep before proceeding to reduce log verbosity 
    sleep(time: sleepTime, unit:'MINUTES')

    waitUntil {
      status = sh (returnStdout: true, 
              script: "az storage blob show \
                      --name '${vhdBlob}' \
                      -c '${vhdCont}' \
                      --account-name '${destAccount}' \
                      --account-key $ARM_KEY \
                      --query properties.copy.status \
                      --output tsv").trim()
      echo "Current status is ${status}"
      return ( status == 'success' );
    }
  }

  withCredentials([azureServicePrincipal('000f1f21-e4f4-4e24-8a1a-5376ccb80aca')]) {
    sh 'az login --service-principal -u $AZURE_CLIENT_ID -p $AZURE_CLIENT_SECRET -t $AZURE_TENANT_ID'
    sh 'az account set -s $AZURE_SUBSCRIPTION_ID'

    sh "az image create --os-type Linux --resource-group '${rg}' --name '${imageName}' \
    --source https://'${destAccount}'.blob.core.windows.net/'${vhdCont}'/'${vhdBlob}' \
    --tags Created='${tag_created}' BuildInfo='${tag_info}' Role='${tag_role}' Creator='${tag_creator}'"

    if (tag_role == 'BaseImage')
    {
      //Delete existing image first, since in-place overwriting will not work
      echo 'Deleting latest image'
      sh "az image delete --name 'base-latest' --resource-group '${rg}'"

      echo 'Creating latest image'
      sh "az image create --os-type Linux --resource-group '${rg}' --name 'base-latest' \
      --source https://'${destAccount}'.blob.core.windows.net/'${vhdCont}'/'${vhdBlob}' \
      --tags Created='${tag_created}' BuildInfo='${tag_info}' Role='${tag_role}' Creator='${tag_creator}'"
    }
  }
}