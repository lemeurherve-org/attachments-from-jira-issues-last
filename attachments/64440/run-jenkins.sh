#!/bin/bash

# Significant Declarative Pipeline Performance Issues
#
# https://issues.jenkins.io/browse/JENKINS-75711

JENKINS_WAR_VERSION=2.504.1
JENKINS_WAR=jenkins-${JENKINS_WAR_VERSION}.war
PLUGIN_MANAGER_VERSION=2.13.2
PLUGIN_MANAGER_JAR=jenkins-plugin-manager-${PLUGIN_MANAGER_VERSION}.jar

if [ ! -f ../$PLUGIN_MANAGER_JAR ]; then
  wget https://github.com/jenkinsci/plugin-installation-manager-tool/releases/download/${PLUGIN_MANAGER_VERSION}/$PLUGIN_MANAGER_JAR
  mv $PLUGIN_MANAGER_JAR ..
fi
[ ! -d plugins ] && mkdir plugins

java -jar ../$PLUGIN_MANAGER_JAR --jenkins-version $JENKINS_WAR_VERSION --latest false --plugin-download-directory plugins --plugin-file plugins.yaml

if [ ! -f ../$JENKINS_WAR ]; then
  wget https://get.jenkins.io/war-stable/${JENKINS_WAR_VERSION}/jenkins.war
  mv jenkins*.war ../$JENKINS_WAR
fi

JENKINS_HOME=. java -jar ../$JENKINS_WAR
