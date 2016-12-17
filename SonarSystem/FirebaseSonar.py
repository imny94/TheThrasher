import time
import requests
from firebase import firebase

firebase_url = 'https://smartbin-16031.firebaseio.com/'
#Setup a loop to send Pressure readings at fixed intervals in seconds
fixed_interval = 10
#Connect to Serial port for communication

class FirebaseSonar:
    def __init__(self):
        firebase = firebase.FirebaseApplication('https://smartbin-16031.firebaseio.com/', None)
        print "firebaseSonar class initialized"

    def upload(self, data, bin_location):
        try:
            result = requests.put(firebase_url + '/' + bin_location + '/sonar' + '.json', data=json.dumps(data))

            print 'Record inserted. Result code = ' + str(result.status_code) + ',' + result.text
            time.sleep(fixed_interval)

        except IOError:
            print '===FirebaseSonar=== uploading failed'
            time.sleep(fixed_interval)
        
    def checkFull(self, bin_location):
        try:
            isFull = firebase.get(bin_location + '/sonar/Full' , None)
            if (isFull):
                print "bin is full"
                return True
            else:
                print "bin is not full"
                return False
        except IOError:
            print '===FirebaseSonar=== download failed'
            time.sleep(fixed_interval)
        
