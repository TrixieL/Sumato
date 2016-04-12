import SocketServer
from ps3 import *
from gopigo import*


class SumatoTCP(SocketServer.BaseRequestHandler):
    """
    The request handler class for our server.

    It is instantiated once per connection to the server, and must
    override the handle() method to implement communication to the
    client.
    """


        
    

    def handle(self):
        # self.request is the TCP socket connected to the client
        self.data = self.request.recv(1024).strip()
        print "{} wrote:".format(self.client_address[0])
        print self.data
        # just send back the same data, but upper-cased
        self.request.sendall(self.data.upper())

        if self.data == "PAIR_PS3":
	    ps3connected = False	    
            while not ps3connected:
                try:
                    time.sleep(2)
                    ps3connected = False
		    print("ps3connected is false, and I'm in the while loop")
                    controller = ps3()
                    ps3connected = True
		    print("ps3 connected is %s" %ps3connected)
                except:
		    e=sys.exc_info()[0]
                    print("Error found hehehe %s" % e)
                    
                
            while ps3connected:
		print("ps3 connected is true")
                srl = serial.Serial('/dev/ttyACM0', 9600)
                controller.update()
                time.sleep(.01)
                if controller.up:
                    srl.write('f')
                    self.request.sendall("BUTTON_UP")
                elif controller.cross:
                    srl.write('h')
                    self.request.sendall("BUTTON_X")
                elif controller.down:
                    srl.write('b')
                    self.request.sendall("BUTTON_DOWN")
                elif controller.right:
                    srl.write('r')
                    self.request.sendall("BUTTON_RIGHT")
                elif controller.left:
                    srl.write('l')
                    self.request.sendall("BUTTON_LEFT")
                elif controller.l1:
                    self.request.sendall("BUTTON_L1")
                elif controller.l2:
                    self.request.sendall("BUTTON_L2")
                elif controller.r1:
                    self.request.sendall("BUTTON_R1")
                elif controller.r2:
                    self.request.sendall("BUTTON_R2")
                elif controller.triangle:
                    self.request.sendall("BUTTON_TRIANGLE")
                elif controller.circle:
                    self.request.sendall("BUTTON_CIRCLE")
                elif controller.square:
                    self.request.sendall("BUTTON_SQUARE")
                elif controller.start:
                    self.request.sendall("BUTTON_START")
                elif controller.select:
                    self.request.sendall("BUTTON_SELECT")


if __name__ == "__main__":
    HOST, PORT = "169.254.13.135", 9999

    # Create the server, binding to localhost on port 9999
    server = SocketServer.TCPServer((HOST, PORT), SumatoTCP)

    # Activate the server; this will keep running until you
    # interrupt the program with Ctrl-C
    server.serve_forever()
