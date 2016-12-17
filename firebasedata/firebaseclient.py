import serial
import time
import requests
import json
#sonarsystem
import tests
#Remember to change url
firebase_url = 'https://smartbin-16031.firebaseio.com/'
#firebase_url = 'https://smartbin-52bd1.firebaseio.com/'
#Connect to Serial Port for communication
ser = serial.Serial('COM1', 9600)
#Setup a loop to send Pressure values at fixed intervals
#in seconds
#set time interval in sync with sonar readings
fixed_interval = 10
#10

pressure_location = 'Blk 1 Lvl 3'
#status = {'classification':'NIL'}
#result1 = requests.put(firebase_url + '/' + pressure_location + '.json', data=json.dumps(status))

try:
    while 1:
      try:
                  
        pressure_c = ser.readline().rstrip()
        pressure_c = float(pressure_c.replace(' kg',''))

        #test_sonar = '1.5 cm'
        #sonar = float(test_sonar.replace(' cm',''))
        sonar = tests.averageHistory
        average = tests.averageDistance
        #current time and date
        time_hhmmss = time.strftime('%H:%M:%S')
        date_mmddyyyy = time.strftime('%d-%m-%Y')
        timedate = '/' + date_mmddyyyy + '-' + time_hhmmss + '.json'
        
        #current location name
        
        #print pressure_c + ',' + time_hhmmss + ',' + date_mmddyyyy + ',' + pressure_location
        
        #insert record
        data = {'pressure':pressure_c,'Sonar1':sonar[0],'Sonar2':sonar[1],'Sonar3':sonar[2],'Sonar4':sonar[3],'Sonar5':sonar[4],'Sonar6':sonar[5],'SonarAvg':round(average,2)}
        
        result = requests.put(firebase_url + '/' + 'Raw Data' + '/' + pressure_location + timedate, data=json.dumps(data))
            
        print 'Record inserted. Result Code = ' + str(result.status_code) + ',' + result.text
        time.sleep(fixed_interval)
      except IOError:
        print('Error! Something went wrong.')
      time.sleep(fixed_interval)
      
except KeyboardInterrupt:
    print 'Application Terminated'
    time_hhmmss = time.strftime('%H:%M:%S')
    date_mmddyyyy = time.strftime('%d-%m-%Y')
    date = {'lastTimeStamp':date_mmddyyyy+'-'+time_hhmmss}
    result2 = requests.patch(firebase_url + '/' + 'Raw Data' + '/' + pressure_location + '.json', data=json.dumps(date))
    result3 = requests.put(firebase_url + '/' + 'Bin Last Time Stamp' + '/' + pressure_location + '.json', data=json.dumps(date))
