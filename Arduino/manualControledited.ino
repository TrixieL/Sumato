#include <Smartcar.h>

Car car;

const int fSpeed = 70; //70% of the full speed forward
const int bSpeed = -70; //70% of the full speed backward
const int lDegrees = -75; //degrees to turn left
const int rDegrees = 75; //degrees to turn right
unsigned long previousPush = 0; //time passed since the last time a command was sent

void setup() {
  Serial3.begin(9600);
  car.begin(); //initialize the car using the encoders and the gyro
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
}

void loop() {
  handleInput();
  if ( millis() > previousPush + 100) { //millis() show the time passed from poweron till this moment.if
        car.setSpeed(0);                //it exceeds the previousPush by 100 ms it will stop the car
        car.setAngle(0);                //this enables a "whilepressed" functionality to the AndroidRC
  }
}

void handleInput() { //handle serial input if there is any
  if (Serial.available()) {
    char input = Serial.read(); //read everything that has been received so far and log down the last entry
    switch (input) {
      case 'l': //rotate counter-clockwise going forward
        car.setSpeed(fSpeed);
        car.setAngle(lDegrees);
        break;
      case 'r': //turn clock-wise
        car.setSpeed(fSpeed);
        car.setAngle(rDegrees);
        break;
      case 'f': //go ahead
        car.setSpeed(fSpeed);
        car.setAngle(0);
        break;
      case 'b': //go back
        car.setSpeed(bSpeed);
        car.setAngle(0);
        break;
      default: //if you receive something that you don't know, just stop
        car.setSpeed(0);
        car.setAngle(0);
    }
    previousPush = millis();
  }
}

