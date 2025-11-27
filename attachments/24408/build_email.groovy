<%
def projectname = build.project.name
projectname = projectname.replaceAll('_Send_Email', '')
String fileContents = new File("R:\\logs\\" + projectname + "\\build_email\\build_email.txt").text
println fileContents %>
