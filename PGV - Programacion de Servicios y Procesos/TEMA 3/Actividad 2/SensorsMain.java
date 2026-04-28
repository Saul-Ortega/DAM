package org.example;

public class SensorsMain {
    public static void main(String[] args) {
        String host = "localhost";
        int UDPPort = 5000;

        Thread sensor1 = new Thread(new UDPSensor("S1", host, UDPPort));
        Thread sensor2 = new Thread(new UDPSensor("S2", host, UDPPort));

        sensor1.start();
        sensor2.start();

        System.out.println("Sensors S1 y S2 initialized.");
    }
}