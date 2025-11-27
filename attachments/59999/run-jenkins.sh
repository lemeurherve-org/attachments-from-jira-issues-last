#!/bin/bash

# Not a git directory reported in some cases where ownership of job
# directories is not consistent with the user performing the fetch or
# checkout, even though the current user has permission in the directory.
#
# https://issues.jenkins.io/browse/JENKINS-70540

JENKINS_WAR_VERSION=2.375.3
JENKINS_WAR=jenkins-${JENKINS_WAR_VERSION}.war
PLUGIN_MANAGER_VERSION=2.12.11
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
  mv jenkins.war ../$JENKINS_WAR
fi

(cd jobs/freestyle && git clone https://github.com/jenkinsci/elastic-axis-plugin.git workspace && cd workspace && git checkout HEAD^)
chmod 770 jobs/freestyle/workspace
sudo chown jagent jobs/freestyle/workspace

ls -altrd jobs/freestyle/workspace

JENKINS_HOME=. java -jar ../$JENKINS_WAR
