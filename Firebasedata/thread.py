import subprocess
from time import sleep

time=0.1

subprocess.Popen(["python", 'SonarSystem.py'])
sleep(time)
subprocess.Popen(["python", 'firebaseclient.py'])

