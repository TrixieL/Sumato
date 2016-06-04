#include <Smartcar.h>
#include <Wire.h>
#include <Servo.h>


Car car;

int Speed = 0; //70% of the full speed forward
int Degrees = 0; //degrees to turn left



unsigned long previousPush = 0; //time passed since the last time a command was sent

void setup() {
  Serial.begin(9600);
  car.begin(); //initialize the car using the encoders and the gyro
}

void loop() {
  handleInput();
 /*
  if ( millis() > previousPush + 100) { //millis() show the time passed from poweron till this moment.if
        car.setSpeed(0);                //it exceeds the previousPush by 100 ms it will stop the car
        car.setAngle(0);                //this enables a "whilepressed" functionality to the AndroidRC
  }
  */
}

void handleInput() { //handle serial input if there is any
  if (Serial.available()) {
    char input = Serial.read(); //read everything that has been received so far and log down the last entry
    switch (input) {

      case 'r': //turn clock-wise
        
        Degrees = ((int) Serial.parseInt() * 90) / 100;
        int leftmotor = Degrees + 100 
        car.setMotorSpeed()
        car.setAngle((int) Degrees);
        break;
        
      case 'f': //go ahead
        Speed = Serial.parseInt();
        car.setSpeed((int) Speed);
        break;

      case 't': //Just so parseInt is faster
        break;
        
      default: //if you receive something that you don't know, just stop
        car.setSpeed(0);
        car.setAngle(0);
    }
    previousPush = millis();
  }
}
