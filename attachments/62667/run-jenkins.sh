#!/bin/bash

# GIT_URL contains user name and @ after plugin update
#
# https://issues.jenkins.io/browse/JENKINS-73250

JENKINS_WAR_VER=2.452.1
JENKINS_WAR=jenkins-${JENKINS_WAR_VER}.war
PLUGIN_MANAGER_VER=2.13.0
PLUGIN_MANAGER_JAR=jenkins-plugin-manager-${PLUGIN_MANAGER_VER}.jar

if [ ! -f ../$PLUGIN_MANAGER_JAR ]; then
  base=https://github.com/jenkinsci/plugin-installation-manager-tool/releases/download
  wget ${base}/${PLUGIN_MANAGER_VER}/$PLUGIN_MANAGER_JAR
  mv $PLUGIN_MANAGER_JAR ..
fi
if [ ! -d plugins ]; then
  mkdir plugins
fi
java -jar ../$PLUGIN_MANAGER_JAR \
     --jenkins-version $JENKINS_WAR_VER \
     --plugin-download-directory plugins \
     --plugin-file plugins.txt

if [ ! -f ../$JENKINS_WAR ]; then
  base=https://get.jenkins.io/war-stable
  wget ${base}/${JENKINS_WAR_VER}/jenkins.war
  mv jenkins.war ../$JENKINS_WAR
fi

JENKINS_HOME=. java -jar ../$JENKINS_WAR
