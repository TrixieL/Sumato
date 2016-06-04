#!/bin/sh

sudo ifconfig wlan0 192.168.42.1
sudo service hostapd restart
sudo service isc-dhcp-server restart

#sudo sixad --stop
#sudo sixad --start
#sudo /home/pi/PiBits/ServoBlaster/user/servod

sudo uv4l --auto-video_nr --driver raspicam --encoding h264 --server-option '--port=9001' --driver raspicam --hflip yes --vflip yes --width 360 --height 400 --framerate 30 --profile baseline
sleep 3
sudo uv4l --auto-video_nr --driver raspicam --encoding h264 --server-option '--port=9000' --driver raspicam --hflip yes --vflip yes --width 480 --height 270 --framerate 30 --profile baseline
sleep 3

sudo python /home/pi/Sumato/tcpserver.py
sleep 1
