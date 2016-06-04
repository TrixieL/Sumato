import socket
import sys

HOST, PORT = "192.168.42.1", 9999
data = " ".join(sys.argv[1:])

# Create a socket (SOCK_STREAM means a TCP socket)
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

try:
    # Connect to server and send data
    sock.connect((HOST, PORT))
    sock.sendall("PAIR_PS3")
    while True:
        # Receive data from the server and shut down
        received = sock.recv(1024)
        print "Sent:     {}".format("PAIR_PS3")
        print "Received: {}".format(received)

finally:
    sock.close()


