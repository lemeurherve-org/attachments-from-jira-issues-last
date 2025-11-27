#!/bin/bash

# Bug report says that notifyCommit always prompts for token even when disabled.
# Test with:
# curl http://testing-b.markwaite.net:8080/git/notifyCommit?url=https://github.com/MarkEWaite/git-plugin.git
#
# https://issues.jenkins.io/browse/JENKINS-71876

JENKINS_WAR_VERSION=2.401.3
JENKINS_WAR=jenkins-${JENKINS_WAR_VERSION}.war
PLUGIN_MANAGER_VERSION=2.12.13
PLUGIN_MANAGER_JAR=jenkins-plugin-manager-${PLUGIN_MANAGER_VERSION}.jar

if [ ! -f ../$PLUGIN_MANAGER_JAR ]; then
  wget https://github.com/jenkinsci/plugin-installation-manager-tool/releases/download/${PLUGIN_MANAGER_VERSION}/$PLUGIN_MANAGER_JAR
  mv $PLUGIN_MANAGER_JAR ..
fi
if [ ! -d plugins ]; then
  mkdir plugins
fi
/opt/jdk-11/bin/java -jar ../$PLUGIN_MANAGER_JAR --jenkins-version $JENKINS_WAR_VERSION --latest false --plugin-download-directory plugins --plugin-file plugins.txt

if [ ! -f ../$JENKINS_WAR ]; then
  wget https://get.jenkins.io/war-stable/${JENKINS_WAR_VERSION}/jenkins.war
  mv jenkins*.war ../$JENKINS_WAR
fi

JENKINS_HOME=. java -jar -Dhudson.plugins.git.GitStatus.NOTIFY_COMMIT_ACCESS_CONTROL=disabled ../$JENKINS_WAR
