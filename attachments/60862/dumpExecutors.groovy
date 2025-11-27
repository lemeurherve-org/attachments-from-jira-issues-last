import hudson.model.OneOffExecutor
import jenkins.model.Jenkins
import org.jenkinsci.plugins.workflow.job.WorkflowRun
import org.jenkinsci.plugins.workflow.support.steps.ExecutorStepExecution

def impactedNodeName = "<nodeName>"

def dumpExecutorInfo(executor) {
    println " ${executor.displayName}(${executor.number})"
    println "  OneOff? " + (executor instanceof OneOffExecutor)
    println "  Active? " + executor.isActive()
    println "  Likely Stuck? " + executor.isLikelyStuck()
    println "  Progress: " + executor.getProgress()
    println "  Interrupted? " + executor.interrupted
    println "  Busy? " + executor.busy
    println "  Elasped Time? " + executor.elapsedTime
    println "  Current Work Unit? " + executor.currentWorkUnit
    println "  Causes Of Interruption? " + executor.causesOfInterruption
    println "  Idle Start Milliseconds? " + executor.idleStartMilliseconds
    println "  Asynchronous Execution: " + executor.getAsynchronousExecution()
    println "  Executable: " + executor.getCurrentExecutable()
    if (executor.getCurrentExecutable() != null) {
        def executorExecutable = executor.getCurrentExecutable()
        println "   Executable: " + executorExecutable.toString()
        println "   Executable (class): " + executorExecutable.class
        println "   Executable (url): " + executorExecutable.url
        if (executorExecutable.parent != null) {
            println "   Executable (parent): " + executorExecutable.parent
            println "   Executable (parent class): " + executorExecutable.parent.class
            println "   Executable (parent ownerTask): " + executorExecutable.parent.ownerTask
            if(executorExecutable.parent instanceof ExecutorStepExecution.PlaceholderTask) {
                def executorPlaceholderTask = ((ExecutorStepExecution.PlaceholderTask) executorExecutable.parent)
                println "   Executable (parent url): " + (executorPlaceholderTask).url
                println "   Executable (parent runId): " + (executorPlaceholderTask).runId
                println "   Run Id: " + (executorPlaceholderTask).runForDisplay()
                println "   Run URL: " + (executorPlaceholderTask).runForDisplay().url
                println "   Run Result: " + (executorPlaceholderTask).runForDisplay().result
                println "   Flow Node Id: " + (executorPlaceholderTask).node?.id
                println "   Job fullName: " + ((WorkflowRun) (executorPlaceholderTask).runForDisplay()).parent.fullName
                println "   Job url: " + ((WorkflowRun) (executorPlaceholderTask).runForDisplay()).parent.url
            }
        }
    }
}

println "master"
Jenkins.instanceOrNull.toComputer()?.allExecutors.each { executor ->
    dumpExecutorInfo(executor)
}

Jenkins.instanceOrNull.nodes
  .find {node -> node.nodeName == impactedNodeName}
  .each {
    println "${it.nodeName}"
    it.toComputer()?.allExecutors.each { executor ->
        dumpExecutorInfo(executor)
    }
}
return