#!/bin/bash

# You enter the parameter, but Jenkins is unaware of it v249.v75d865a_a_d530
#
# https://issues.jenkins.io/browse/JENKINS-74871

JENKINS_WAR_VERSION=2.485
JENKINS_WAR=jenkins-${JENKINS_WAR_VERSION}.war
PLUGIN_MANAGER_VERSION=2.13.2
PLUGIN_MANAGER_JAR=jenkins-plugin-manager-${PLUGIN_MANAGER_VERSION}.jar

if [ ! -f ../$PLUGIN_MANAGER_JAR ]; then
  wget https://github.com/jenkinsci/plugin-installation-manager-tool/releases/download/${PLUGIN_MANAGER_VERSION}/$PLUGIN_MANAGER_JAR
  mv $PLUGIN_MANAGER_JAR ..
fi
if [ ! -d plugins ]; then
  mkdir plugins
fi
java -jar ../$PLUGIN_MANAGER_JAR --jenkins-version $JENKINS_WAR_VERSION --plugin-download-directory plugins --plugin-file plugins.txt

if [ ! -f ../$JENKINS_WAR ]; then
  wget https://get.jenkins.io/war/${JENKINS_WAR_VERSION}/jenkins.war
  mv jenkins*.war ../$JENKINS_WAR
fi

# export VAR1=jenkins-command-line-value-of-VAR1
JENKINS_HOME=. java -jar ../$JENKINS_WAR
