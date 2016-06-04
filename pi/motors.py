import os
import time

class motors():

    def __init__(self): 
	os.system("sudo /home/pi/PiBits/ServoBlaster/user/servod")
        self.MIN = 60
        self.MAX = 250
        self.pan = 150
        self.tilt = 150
        self.currentPan = 150
        self.currentTilt = 150
	self.move = 2
	self.moving = True

    def moveMotor(self, motor, number):
        cmd = "echo "+str(motor)+"="+str(number)+" > /dev/servoblaster"
	#print(cmd)        
	os.system(cmd)
    
    #returns two numbers from the string representing the pan/tilt position
    def parseString(self, str):
        first = str.find('(')
        last = str.find(')')
        cut=str[first+1:last]
        cut = cut.split(',')
        try:
            firstnumber = int(cut[0])
            secondnumber = int(cut[1])
        except:
            return (pant,tilt)      
        return (firstnumber, secondnumber)

    #reset the position of the camera to the middle (150, 150) position
    def resetPosition(self):
        self.moveMotor(1, 150)
        time.sleep(.5)
        self.moveMotor(2, 150)
        time.sleep(.5)
        self.currentTilt=150
        self.currentPan=150
	self.pan=150
	self.tilt=150
	time.sleep(1)

    #assigns pan and tilt position by parsing the string retrieved via TCP
    def setGyroPosition(self, str):
        self.pan, self.tilt = self.parseString(str)
	if self.pan%2==1:
		self.pan=self.pan+1
	if self.tilt%2==1:
		self.tilt=self.tilt+1
    
    def update(self):
	time.sleep(3)
        while self.moving:
	   #move motor1 one step
           if self.currentPan > self.pan:
           	if self.currentPan > self.MIN:
           		self.currentPan = self.currentPan-self.move
           elif self.currentPan < self.pan:
           	if self.currentPan < self.MAX:
           		self.currentPan = self.currentPan+self.move               
           self.moveMotor(1, self.currentPan)

           #move motor2 one step
           if self.currentTilt > self.tilt:
           	if self.currentTilt > self.MIN:
                	self.currentTilt = self.currentTilt -self.move
           elif self.currentTilt < self.tilt:
                if self.currentTilt < self.MAX:
                    	self.currentTilt = self.currentTilt + self.move
           self.moveMotor(2, self.currentTilt)

	
    def stopMoving(self):
	 self.moving = False
