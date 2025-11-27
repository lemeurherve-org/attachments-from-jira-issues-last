#!/bin/bash

set -xe
export CWD=$(pwd)
echo "CWD=${CWD}"

#
# define directories
#
export REPOS_DIR=${CWD}/repos
export WORKING_DIR=${CWD}/working
export REPOS_WORKING_DIR=${WORKING_DIR}/repos
export PROJECT_WORKING_DIR=${WORKING_DIR}/project
export REPOS_URL=svn://localhost/

export USERPASS="--username=myself --password=itsme"



#
# fill with some code
#
echo "function add3(a,b,c) return a+b end" >> ${PROJECT_WORKING_DIR}/subproject1/lib1/add.lua
echo "function sub3(a,b,c) return a-b end" >> ${PROJECT_WORKING_DIR}/subproject1/lib1/sub.lua

echo "function mul3(a,b,c) return a*b end" >> ${PROJECT_WORKING_DIR}/subproject1/lib2/mul.lua
echo "function div3(a,b,c) return a/b end" >> ${PROJECT_WORKING_DIR}/subproject1/lib2/div.lua

svn ci ${USERPASS} -m'updated modules' ${PROJECT_WORKING_DIR}/subproject1/lib*

#
# deploy to other dirs
#
svn up ${USERPASS} ${PROJECT_WORKING_DIR}

echo "ALL DONE"

