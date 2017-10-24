
#This is where the code to speak to the sensors will be written

import RPi.GPIO as GPIO
import serial

arduinoData = serial.Serial('/dev/ttyACM1',9600)

while (1==1):
    if(arduinoData.inWaiting() > 0):
        myData = arduinoData.readline()
        print (myData)


"""class sensors(object):
	
	def __inti__(self):
		GPIO.setwarnings(False)

"""