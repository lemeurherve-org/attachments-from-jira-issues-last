def FromFolder = "release_unittest"
def ToFolder = "new_unittest"
def Folder = Jenkins.instance.getItem(ToFolder)
if (Folder == null)
    Folder = Jenkins.instance.createProject(com.cloudbees.hudson.plugins.folder.Folder, ToFolder)
    Folder.save()
for (item in Hudson.instance.items)
{
  jobName = item.getName()
  if (jobName == FromFolder){
    jobs = item.getAllJobs()
    for (cItem in jobs){
      orgName=cItem.getName();
      println("Job "+orgName+" Found")
      tempName=cItem.getName()+"temp"
      newItem = Jenkins.instance.getItemByFullName(tempName)
      if (newItem == null){
        newItem=jenkins.model.Jenkins.instance.copy(cItem,tempName)
        newItem.save()
        println("- Copied")
      }
      else
        println(newItem.getName() + "Already Exists")
      Items.move(newItem,(DirectlyModifiableTopLevelItemGroup)Folder)
      println("- Moved")
      orgNamePlus = (orgName=~ FromFolder).replaceAll(ToFolder)
      println("- renamed to "+ orgNamePlus)
      newItem.renameTo(orgNamePlus)
      if (newItem.getPublishersList().size() > 0){
        for (BT in newItem.getPublishersList())
        if ( BT instanceof hudson.tasks.BuildTrigger){
          newChildren = (BT.getChildProjectsValue() =~ FromFolder).replaceAll(ToFolder)
          def t = new hudson.tasks.BuildTrigger(newChildren,BT.getThreshold())
          newItem.getPublishersList().remove(BT)
          newItem.getPublishersList().add(t)
          println("- updatedChild "+newChildren)
        }
      }
    }
  }
}