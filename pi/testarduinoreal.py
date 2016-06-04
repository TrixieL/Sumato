from PS3controller import *
import threading
import os

controller = PS3controller()

t = threading.Thread(target = controller.update)

x = threading.Thread(target = controller.updateCamera)

t.start()

x.start()

os.system("sudo python /home/pi/Sumato/motorsFix.py")



