/**************************************************************
* Class: CSC-615-01 Spring 2020
* Name: Brian Lai
* Student ID: 916818167
* Project: Final Project - Autonomous Car
* Team: Liger
*       - Ahmad Moussalli
*       - Michael Feyk
*       - Brandan Hui
*
* File: lineSensor.c
*
* Description: Uses a line sensor connected to the IR pin on
*              the Raspberry Pi to detect dark or light surface.
*
**************************************************************/

#include "lineSensor.h"

#define RATE 0.1

int started = 0;
int buffer1 = -1;
int buffer2 = -1;
int buffer3 = -1;

int sensorPin1 = -1;
int sensorPin2 = -1;
int sensorPin3 = -1;

PI_THREAD(sensor1) {
    while (started) {
        // Read data and store in buffer
        int temp = digitalRead(sensorPin1);
        if (temp != buffer1){
            printf("sensor 1: old value: %d, new value: %d \n",buffer1, temp);
        }
        buffer1 = digitalRead(sensorPin1);

    }

    return 0;
}

PI_THREAD(sensor2) {
    while (started) {
        // Read data and store in buffer
        buffer2 = digitalRead(sensorPin2);
    }

    return 0;
}

PI_THREAD(sensor3) {
    while (started) {
        // Read data and store in buffer
        buffer3 = digitalRead(sensorPin3);
    }

    return 0;
}

int initSensor1(int pinNumber) {
    if (pinNumber < 0) {
        printf("Invalid pin number, pin for sensor 1 not set.\n");
        return 1;
    }

    sensorPin1 = pinNumber;
    pinMode(sensorPin1, OUTPUT);
    return 0;
}

int initSensor2(int pinNumber) {
    if (pinNumber < 0) {
        printf("Invalid pin number, pin for sensor 2 not set.\n");
        return 1;
    }

    sensorPin2 = pinNumber;
    pinMode(sensorPin2, OUTPUT);
    return 0;
}

int initSensor3(int pinNumber) {
    if (pinNumber < 0) {
        printf("Invalid pin number, pin for sensor 3 not set.\n");
        return 1;
    }

    sensorPin3 = pinNumber;
    pinMode(sensorPin3, OUTPUT);
    return 0;
}

int startSensors() {
    if (started) {
        printf("Sensors already started.\n");
        return 1;
    }

    started = 1;

    // Create threads for line sensors
    if (piThreadCreate(sensor1) != 0) {
        printf("Thread did not start!\n");
        return 1;
    }

    if (piThreadCreate(sensor2) != 0) {
        printf("Thread did not start!\n");
        return 1;
    }

    if (piThreadCreate(sensor3) != 0) {
        printf("Thread did not start!\n");
        return 1;
    }
    printf("Starting sensors\n");
    return 0;
}

int readSensor1() {
    return buffer1;
}

int readSensor2() {
    return buffer2;
}

int readSensor3() {
    return buffer3;
}
