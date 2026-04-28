package org.example;

public class SensorsMain {
    public static void main(String[] args) {
        String host = "localhost";
        int UDPPort = 5000;

        Thread sensor1 = new Thread(new UDPSensor("S1", host, UDPPort));
        Thread sensor2 = new Thread(new UDPSensor("S2", host, UDPPort));
        Thread sensor3 = new Thread(new UDPSensor("S3", host, UDPPort));
        Thread sensor4 = new Thread(new UDPSensor("S4", host, UDPPort));
        Thread sensor5 = new Thread(new UDPSensor("S5", host, UDPPort));
        Thread sensor6 = new Thread(new UDPSensor("S6", host, UDPPort));

        sensor1.start();
        sensor2.start();
        sensor3.start();
        sensor4.start();
        sensor5.start();
        sensor6.start();

        System.out.println("Sensors S1, S2, S3, S4, S5 and S6 initialized.");
    }
}