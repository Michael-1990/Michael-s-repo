#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <wiringPi.h>
#include <softPwm.h>

int initSensor1(int pinNumber);
int initSensor2(int pinNumber);
int initSensor3(int pinNumber);
int startSensors();
int readSensor1();
int readSensor2();
int readSensor3();
