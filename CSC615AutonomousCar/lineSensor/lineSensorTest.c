#include <stdio.h>
#include <stdlib.h>
#include <signal.h>
#include <wiringPi.h>
#include <softPwm.h>
#include "lineSensor.h"

#define PIN1 7
#define PIN2 1
#define PIN3 18

void handle_sigint(int sig) {
    printf("\nCaught signal %d\n", sig);
    exit(0);
}

int main() {
    // Signal for ctrl + C interrupt
    signal(SIGINT, handle_sigint);
    // Set to always flush stdout buffer
    setvbuf(stdout, NULL, _IONBF, 0);

    printf("Starting line sensor test program\n");
    // Attempt to initialize wiring pi library
    if (wiringPiSetup() == -1) {
        printf("Error setting up wiring pi library\n");
        return 1;
    }
    printf("Wiring pi setup complete\n");

    // Initialize sensors
    if (initSensor1(PIN1) != 0) {
        printf("Init sensor failed\n");
        return 1;
    }
    if (initSensor2(PIN2) != 0) {
        printf("Init sensor failed\n");
        return 1;
    }
    if (initSensor3(PIN3) != 0) {
        printf("Init sensor failed\n");
        return 1;
    }

    // Start up sensors
    if (startSensors() != 0) {
        printf("Sensors failed to start\n");
        return 1;
    }

    // Continuously read data from all 3 sensors
    while (1) {
        printf("Sensor pin %d: %d\n", PIN1, readSensor1());
        printf("Sensor pin %d: %d\n", PIN2, readSensor2());
        printf("Sensor pin %d: %d\n", PIN3, readSensor3());
        delay(100);
        printf("\n");
    }
}
