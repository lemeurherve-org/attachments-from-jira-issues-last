#!/usr/bin/env python
# -*- coding:utf-8 -*-
 
import sys
import os
from shutil import copyfile
 
import json
import urllib.request
if len(sys.argv) < 2:
    sys.exit()
 
current_path = os.path.dirname(os.path.abspath(__file__))
 
with open(current_path + '/post_commit.log','w') as f:
    f.write(str(sys.argv[1:]))
    
if len(sys.argv) < 7:
    sys.exit()
 
path = sys.argv[1]
depth = sys.argv[2]
message_file = sys.argv[3]
revision = sys.argv[4]
error = sys.argv[5]
cwd = sys.argv[6]
 
#copyfile(path, current_path + '/path.tmp')
#copyfile(message_file, current_path + '/message_file.tmp')
#copyfile(error, current_path + '/error.tmp')
 
# if error is not empty, the commit should not success
try:
    if os.stat(error).st_size > 0:
       print ("[INFO] error happened")
       sys.exit()
except OSError:
    # no such file, which should not happen
    print ("[ERROR] no error file")
    sys.exit()
    
# if files include source files or project files, trigger ci build
source_extensions = ['.h',  '.c']
 
def SourcePath(file_path):
    for extension in source_extensions:
        if file_path.endswith(extension):
            return True
 
    return False
 
source_changed = False
 
with open(path) as f:
    for line in f:
        if SourcePath(line.strip()):
            source_changed = True
            break;
 
if not source_changed:
    print ("[INFO] source not changed")
    sys.exit()
    
print ("[INFO] source changed")
jenkins_job_url = 'http://priyanga:07a66fcd8261dd011c8eeba76eeed1d9@localhost:8080/job/FORD/build?token=TOKEN_NAME&amp;cause=svn-post-commit'
req = urllib.request.Request(jenkins_job_url )
response = urllib.request.urlopen(req)
 
print ("[INFO] response = " + str(response))
