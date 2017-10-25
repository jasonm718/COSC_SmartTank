
#This is where the code to speak to the sensors will be written

import RPi.GPIO as GPIO
import serial

arduinoData = serial.Serial('/dev/ttyACM0',9600)


class sensors(object):
	
	def __inti__(self):
		GPIO.setwarnings(False)
		
	def counter(self):	
            while (1==1):
                if(arduinoData.inWaiting() > 0):
                    myData = int(arduinoData.readline().strip())
                    return (myData)

