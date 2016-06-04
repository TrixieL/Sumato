import pygame
from math import hypot
from pygame.locals import *
from ps3 import *
from gopigo import *

class PS3controller():

    def __init__(self):
        try:
		self.moving = True
                self.controller = ps3()
		print("initialized ps3 controller")
            
        except:
                e=sys.exc_info()[0]

    def update(self):
	srl = serial.Serial('/dev/ttyACM0', 9600)            
	time.sleep(1)
			
        while True:                    

                self.controller.update()
                time.sleep(.1)

		r = int((self.controller.a_joystick_left_x)*100)

		f = int((self.controller.a_joystick_left_y)*-100)
		
		d = int(int(hypot(r,f)) * 0.5)

		if f < -10:
			d = -d

#		print('r' + str(r) + 'f' + str(d) + 't')
                srl.write('r' + str(r) + 'f' + str(d) + 't' )
                #print('r'+str(int((self.controller.a_joystick_left_x)*100)) + 'f' + str(int((self.controller.a_joystick_left_y)*-100)) + 't' )
    		#print('r'+str(r) + 'f' + str(d) + 't' )

    def updateCamera(self):
	self.moving = True
     	motorpan = 150
     	motortilt = 150
      	self.moveMotor(2,motorpan)
      	self.moveMotor(1,motortilt)
	time.sleep(1)
		
	while self.moving:
	      	time.sleep(0.01)
		self.controller.update()
#		print('Moving Cameras, motorpan is ' + str(motorpan) + ' motortilt is ' + str(motortilt) )
                if self.controller.a_joystick_right_x > 0 and motorpan >= 52:
 	               motorpan = motorpan - 2
                       self.moveMotor(2,motorpan)
	        elif self.controller.a_joystick_right_x < 0 and motorpan <= 248:
                       motorpan = motorpan + 2
                       self.moveMotor(2,motorpan)
                if self.controller.a_joystick_right_y > 0 and motortilt <= 248:
                       motortilt = motortilt + 2
                       self.moveMotor(1,motortilt)
                elif self.controller.a_joystick_right_y < 0 and motortilt >= 52:
                        motortilt = motortilt - 2
                        self.moveMotor(1,motortilt)

    def moveMotor(self, motor, number):
        cmd = "echo "+str(motor)+"="+str(number)+" > /dev/servoblaster"
        os.system(cmd)


    def stopMoving(self):
	self.moving = False

