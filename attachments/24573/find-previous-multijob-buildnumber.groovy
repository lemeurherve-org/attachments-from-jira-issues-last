import jenkins.*;
import jenkins.model.*;

def previousSiblingProjectName = "my-previous-job-name";

def upstreamCause = currentBuild.causes.first()
def upstreamProjectName = upstreamCause.upstreamProject
def upstreamNumber = upstreamCause.upstreamRun.number

def instance = Jenkins.instance;

def parentProject = instance.getItem(upstreamProjectName);
def parentBuild = parentProject.getBuildByNumber(upstreamNumber);

def assetBuild = parentBuild.builders.find { subBuild ->
  subBuild.jobName == previousSiblingProjectName
}

return [PREV_JOB_BUILD_NUMBER: assetBuild.buildNumber];
