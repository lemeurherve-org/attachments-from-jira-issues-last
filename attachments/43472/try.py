import jenkins
server = jenkins.Jenkins('http://localhost:8080', username='admin', password='admin')
server.create_job('testapi','new.xml')
#jobs = server.get_jobs()
#print (jobs)
#my_job = server.get_job_config('testapi')
#print (my_job)