import SocketServer, pygame, threading, os
from pygame.locals import *
from ps3 import *
from gopigo import*
from PS3controller import *
from motors import *

class SumatoTCP(SocketServer.BaseRequestHandler):

    """
    The request handler class for our server.

    It is instantiated once per connection to the server, and must
    override the handle() method to implement communication to the
    client.
    """
 
    os.system("sudo /home/pi/PiBits/ServoBlaster/user/servod")
    
    def handle(self):
        self.controller = None
        self.data = None

        self.motor = motors()
	self.motorThread = None
	self.cameraThread = None
	time.sleep(1)

        while True:        
        # self.request is the TCP socket connected to the client
        # just send back the same data, but upper-cased
            self.data = self.request.recv(1024).strip()
            print "{} wrote:".format(self.client_address[0])
            print self.data
            self.request.sendall(self.data.upper()+"\n")
            
            if self.data == "PAIR_PS3":
                try:
		    if self.controller is None:
                    	self.controller = PS3controller()
                    	ps3Thread = threading.Thread(target = self.controller.update)
                    	ps3Thread.start()
		except:
                    self.request.sendall("PS3_FAIL\n")
            elif self.data == "VR_MODE":
		self.motor.resetPosition()
		#stop the controller from moving the camera
		if self.cameraThread is not None:
			self.controller.stopMoving()
			self.cameraThread = None
		if self.motorThread is not None:
			self.controller.stopMoving()
			self.motorThread = None
		#start the thread for moving the camera through the phone
		self.motorThread = threading.Thread(target=self.motor.update)
		self.motorThread.start()
		os.system("sudo python /home/pi/Sumato/motorsFix.py")
            
	    elif self.data == "NORMAL_MODE":
		#stop the phone from moving the camera
		if self.motorThread is not None:
			self.motor.stopMoving()
			self.motorThread = None
		if self.cameraThread is not None:
			self.controller.stopMoving()
			self.cameraThread = None
		#start the thread for moving the camera through the controller
                self.cameraThread = threading.Thread(target = self.controller.updateCamera)
		self.cameraThread.start()
		os.system("sudo python /home/pi/Sumato/motorsFix.py")
            elif self.data[:3] == "pos":
		if self.motor is not None:
                	self.motor.setGyroPosition(self.data)     
                

if __name__ == "__main__":
    HOST, PORT = "192.168.42.1", 9999

    # Create the server, binding to localhost on port 9999
    server = SocketServer.TCPServer((HOST, PORT), SumatoTCP)

    # Activate the server; this will keep running until you
    # interrupt the program with Ctrl-C

    server.serve_forever()



