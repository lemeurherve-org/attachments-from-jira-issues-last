import jenkins.model.Jenkins

/*file1 = new File('/var/log/jenkins/org.acegisecurity-0.log')
def lines = file1.readLines()
lines.each {
  println it
}
*/
def auth = Jenkins.instance.getAuthentication()
def detail = auth.getDetails()
println auth
println "----"
println detail
