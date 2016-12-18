import serial
import time
import requests
import json
firebase_url = 'https://smartbin-16031.firebaseio.com/'

location = {0:'Blk 1 Lvl 3',1:'Blk 1 Lvl 2'}
lastTime = {'Blk 1 Lvl 3':'dd-mm-yyyy-hh:mm:ss','Blk 1 Lvl 2':'16-12-2016-15:49:23'}
classify = {'Classification':'N.A.','Time':'dd-mm-yyyy-hh:mm:ss'}
staff = {'Blk 1 Lvl 3':'N.A','Blk 1 Lvl 2':'N.A'}

result1 = requests.put(firebase_url + '/' + 'Bin Locations' + '.json', data=json.dumps(location))
result2 = requests.put(firebase_url + '/' + 'Bin Last Time Stamp' + '.json', data=json.dumps(lastTime))
result3 = requests.put(firebase_url + '/' + 'Bin Classification' + '/' + 'Blk 1 Lvl 3' + '.json', data=json.dumps(classify))
result4 = requests.put(firebase_url + '/' + 'Bin Classification' + '/' + 'Blk 1 Lvl 2' + '.json', data=json.dumps(classify))
result5 = requests.put(firebase_url + '/' + 'Staff Classification' + '.json', data=json.dumps(staff))

