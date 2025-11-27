def fileContents = new groovy.json.JsonSlurper().parseText(new File("$file").text)
def matches = fileContents.findAll{it."$lookupKey".equals("$lookupValue".trim())}
matches.collect{it."$key"}.each{println(it)}