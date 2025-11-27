listeners = com.sonyericsson.hudson.plugins.gerrit.trigger.PluginImpl.getInstance().gerritEventManager.gerritEventListeners;
keys = listeners.keySet();
found = new java.util.HashMap();
for(key in keys) {
  val = listeners.get(key);
   if(found.get(val.hashCode()) != null) {
    dupe = found.get(val.hashCode());
    println();
    println("=========FOUND DUPLICATE===========");
    println("==Dupe==");
    println("\tKey:           " + key);
    println("\tName:          "+val.job.getName());
    println("\tJob.ToString:  "+val.job.toString());
    println("\tval.ToString:  "+val.toString());
    println("\tval.HashCode:  "+val.hashCode());

    println("==Origin==");
    println("\tName:          "+dupe.job.getName());
    println("\tJob.ToString:  "+dupe.job.toString());
    println("\tval.ToString:  "+dupe.toString());
    println("\tval.HashCode:  "+dupe.hashCode());
    println();
  }
  found.put(val.hashCode(), val);
}