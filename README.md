# Sumato
GU Software Engineering and Management, Project Systems Development Group 10


How to test video stream:
- In terminal: node /Sumato/node/stream-server.js
- In another terminal: raspivid -t 0 -w 640 -h 640 -fps 30 -ISO 800 -vf -hf -o - | ffmpeg -y -i pipe:0 -f video4linux2 -f mpeg1video -b 400k http://127.0.0.1:8082
- Open app