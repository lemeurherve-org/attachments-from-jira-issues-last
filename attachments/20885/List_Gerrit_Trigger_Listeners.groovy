listeners = com.sonyericsson.hudson.plugins.gerrit.trigger.PluginImpl.getInstance().gerritEventManager.gerritEventListeners;
for (listener in listeners) {
	for (project in listener.getGerritProjects()) {
		println("Project: " + project.getPattern())
        for (branch in project.getBranches()) {
            println(" -> Branch: " + branch.getPattern());
        }
	}
}