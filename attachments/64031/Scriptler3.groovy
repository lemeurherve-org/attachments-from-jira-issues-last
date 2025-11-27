def list3 = evaluate(new File("/var/jenkins_home/scriptler/scripts/Scriptler6.groovy"))
def list4 = evaluate(new File("/var/jenkins_home/scriptler/scripts/Scriptler7.groovy"))

def answerList = []
switch(param1) {
	case 'item1':
		answerList = list3
		break
	case 'item2':
		answerList = list4
}
return answerList