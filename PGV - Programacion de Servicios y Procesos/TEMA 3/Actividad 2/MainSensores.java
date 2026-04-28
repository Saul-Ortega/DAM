package org.example;

public class MainSensores {
    public static void main(String[] args) {
        String host = "localhost";
        int puertoUDP = 5000;

        Thread sensor1 = new Thread(new SensorUDP("S1", host, puertoUDP));
        Thread sensor2 = new Thread(new SensorUDP("S2", host, puertoUDP));

        sensor1.start();
        sensor2.start();

        System.out.println("Sensores S1 y S2 iniciados.");
    }
}