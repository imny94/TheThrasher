import RPi.GPIO as GPIO
import time

class SonarUnit:
    def __init__(self, TRIG, ECHO):
        GPIO.setmode(GPIO.BCM)
        self.TRIG = TRIG
        self.ECHO = ECHO
        GPIO.setup(self.TRIG, GPIO.OUT)
        GPIO.setup(self.ECHO, GPIO.IN)

    def trigger(self):
        time.sleep(2)
        GPIO.output(self.TRIG, True)
        time.sleep(0.00001)
        GPIO.output(self.TRIG, False)
        counter = 0
        while GPIO.input(self.ECHO) == 0:
            pulse_start = time.time()
        
        timeout = time.time() + 6
        while GPIO.input(self.ECHO) == 1:
            if time.time() < timeout:
                pulse_end = time.time()
            else:
                print "timeout"
                pulse_end = time.time()
        pulse_duration = pulse_end - pulse_start
        distance = pulse_duration * 17150
        distance = round(distance, 2)
        return distance

    def log(self):
        print "error is getting distance in TRIG=", self.TRIG," and ECHO", self.ECHO
