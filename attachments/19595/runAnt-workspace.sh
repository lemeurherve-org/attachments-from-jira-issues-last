#!/bin/sh
workspace="$1"
baseDir=`dirname "$0"`
pwd=`pwd`
if [ $baseDir = "." ]
then
  baseDir=$pwd
fi

# The JRE java.exe to be used by Ant.  Note: for WTP 2.0 the JDK needs to be 1.5 or higher.
JAVAEXE=/opt/IBM/SDP/jdk/jre/bin/java
       
       
# The Eclipse install directory.  Some Eclipse based products may refer to this directory
# as the non shared directory.
INSTALL_DIRECTORY=/opt/IBM/SDP

# The Eclipse Equinox Launcher jar.  Usually this plugin jar file is located in the
# shared plugin directory(ie. plugins/org.eclipse.equinox.launcher*.jar )
LAUNCHER_JAR=/opt/IBM/SDPShared/plugins/org.eclipse.equinox.launcher_1.0.101.R34x_v20081125.jar


if [ -d "$workspace" ]; then
  echo using workspace $workspace
else
  echo ERROR incorrect workspace $workspace ...
  echo ERROR ... edit this runAnt.sh and correct the workspace var
  echo ERROR ... or, create this workspace directory.
  exit 1
fi

PLATFORM=`/bin/uname`
case $PLATFORM in
  AIX)
    OSOPTIONS="-os aix -ws gtk"
    export OSOPTIONS;;
  Linux)
    OSOPTIONS="-os linux -ws gtk"
    export OSOPTIONS;;
  SunOS)
    OSOPTIONS="-os sparc -ws gtk"
    export OSOPTIONS;;
  HP-UX)
    OSOPTIONS="-ws gtk"
    export OSOPTIONS;;
esac


echo "invoking java launch of Eclipse ..."
$JAVAEXE -Xms256m -Xmx512m -Xj9 -Dwtp.autotest.noninteractive=true -cp $LAUNCHER_JAR org.eclipse.equinox.launcher.Main -install $INSTALL_DIRECTORY -application com.ibm.etools.j2ee.ant.RunAnt -data "$workspace" $OSOPTIONS $2 $3 $4
errorcode=$?
echo "java returned ErrorCode $errorcode"


if [ $errorcode -eq 13 ]; then
  echo ERROR runAnt BUILD FAILED.
  exit 1
elif [ $errorcode -eq 15 ]; then
  echo ERROR WORKSPACE is already BEING USED.
  exit 1
elif [ $errorcode -eq 23 ]; then
  echo WARNING echo totally clean UNINITIALIZED workspace, it is now setup.  will rerun...
  $JAVAEXE -Xms256m -Xmx512m -Xj9 -Dwtp.autotest.noninteractive=true -cp $LAUNCHER_JAR org.eclipse.equinox.launcher.Main -install $INSTALL_DIRECTORY -application com.ibm.etools.j2ee.ant.RunAnt -data "$workspace" $OSOPTIONS $*
elif [ $errorcode -ne 0 ]; then
  echo ERROR runAnt FAILED.  ErrorCode=$errorcode
  exit 1
fi

echo runAnt.sh DONE.
exit 0

