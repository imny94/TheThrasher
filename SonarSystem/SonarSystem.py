import time
import FirebaseSonar
import schedule
import math
import datetime
'''
-- SonarSystem Class --
This class is the highest level class of the system
It is a state machine with the following states
    - Off-peak checks every 30 minutes, these are around 8-11AM and 1-5PM, 8-10PM
    - Peak checks every 15 minutes, these are between 11AM-1PM, 5-7PM
    - Verify does a one more thorough check on the bin
The sonarSystem holds a set of SonarUnit classes and triggers their scan
functions and get their data
'''

# CONFIG CONSTANTS
NORMAL_DELAY = 1800
PEAK_DELAY = 900
SONAR_TRIES = 5
SONAR_DELAY = 2
BIN_LOCATION = "Blk 1 Lvl 3"


class SonarSystem:
    def __init__(self):
        self.sonarList = []         # List of sonar units
        self.distanceHistory = []   # history of detected distance
        self.binState = 0           # 0 = empty, 1 = full
        self.state = 0              # States = {OffPeak, Peak, Verify, Full, FAIL }

    def addSonarUnit(self, sonarUnit):
        self.sonarList.append(sonarUnit)
        # print "sonarList is now", self.sonarList

    def stateManager(self):
        if self.state == 0:
            schedule.clear()
            print "STATE = Off-peak, refreshing every 30 minutes"
            schedule.every(30).minutes.do(self.initiateTDM)
        elif self.state == 1:
            schedule.clear()
            print "STATE = Peak, refreshing every 15 minutes"
            schedule.every(15).minutes.do(self.initiateTDM)
        elif self.state == 2:
            print "STATE = Verifying, Retrying TDM in 5 seconds"
            time.sleep(5)
            initiateTDM()
        elif self.state == 3:
            print "STATE = Full, periodically checking firebase for bin updates"
            schedule.clear()
            schedule.every(1).minutes.do(self.checkFirebase)
            
    def initiateTDM(self):
        averageDistance = self.getSensorData()
        valid = self.validateData(averageDistance)
        if (not valid):
            self.state = 2
            self.stateManager()
        elif (averageDistance < 20):
            self.state = 3
            self.stateManager()
        else:
            hour = datetime.datetime.now().hour
            if (hour >= 11 and hour < 13 or hour >= 17 and hour < 19):
                self.state = 1
                self.stateManager()
            else:
                self.state = 0
                self.stateManager()
            

    def getSensorData(self):
        for sonarunit in self.sonarList:
            self.distanceHistory.append(0)
        
        cumulativeAvg = 0
            
        for i in range(0,SONAR_TRIES):              # loop a certain amount of tries
            sonarUnitIndex = 0;
            
            for sonarunit in self.sonarList:            # cycle for every sonar around the bin
                cumulativeDistance = 0
                success = 0
                try:                    
                    distance = sonarunit.trigger()      # Trigger the sonar and get data
                    cumulativeDistance += distance      # add the cumulative sensor distance
                    self.distanceHistory[sonarUnitIndex] += distance # increase that sonarUnit's cumulative distance
                    success += 1
                    print "sonar", sonarUnitIndex, "distance = ", distance ,"cm"
                    time.sleep(SONAR_DELAY)
                except Exception as e:
                    sonarunit.log()
                    print "error = ",e

                if (success > 0):
                    cumulativeAvg += (cumulativeDistance / success)
                else:
                    sonarunit.log()
                sonarUnitIndex += 1
            
        averageDistance = cumulativeAvg / SONAR_TRIES
        print "TDM complete with average sensor distance = ", averageDistance, "cm"
        return averageDistance
            
        
    def validateData(self, averageDistance):
        print "validating self with distance history"
        averageHistory = []
        time_hhmmss = time.strftime('%H:%M:%S')
        date_mmddyyyy = time.strftime('%d/%m/%Y')

        for distance in self.distanceHistory:
            average = round(distance / SONAR_TRIES,2)
            if (average < 2 or average > 100):      # if value is an outlier, ignore
                pass
            else:
                averageHistory.append(average)

        # if most values are not outliers, data is valid.
        if (len(averageHistory) >= len(self.distanceHistory) - 1):
            # check if bin is full
            if (averageDistance < 20):
                data = {'date':date_mmddyyyy,'time':time_hhmmss,'sonarValues': averageHistory, 'sonarAvg': round(averageDistance,2), 'Full': True }
            else:
                data = {'date':date_mmddyyyy,'time':time_hhmmss,'sonarValues': averageHistory, 'sonarAvg': round(averageDistance,2), 'Full': False }
            print "Data successfully validated, uploading"
            firebaseSonar = FirebaseSonar.FirebaseSonar()
            firebaseSonar.upload(data, BIN_LOCATION)
            return True
        else:
            print "validation failed, retrying"
            return False

    def checkFirebase(self):
        print "Checking firebase for bin updates"
        firebaseSonar = FirebaseSonar.FirebaseSonar()
        full = firebaseSonar.checkFull(BIN_LOCATION)
        if (not full):
            schedule.clear()
            self.initiateTDM()
            
            
