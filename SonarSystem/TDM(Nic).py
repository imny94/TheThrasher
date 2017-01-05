import SonarSystem
import SonarUnit

TRIG1 = 23
ECHO1 = 24

TRIG2 = 27
ECHO2 = 22

def main():
	TDM = SonarSystem.SonarSystem()
	sonar1 = SonarUnit.SonarUnit(TRIG1,ECHO1)
	sonar2 = SonarUnit.SonarUnit(TRIG2,ECHO2)
	TDM.addSonarUnit(sonar1)
	TDM.addSonarUnit(sonar2)
	TDM.initiateTDM()

main()