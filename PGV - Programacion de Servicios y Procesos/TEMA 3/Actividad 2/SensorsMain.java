package org.example;

public class SensorsMain {
    public static void main(String[] args) {
        String host = "localhost";
        int UDPPort = 5000;

        SensorId[] sensorIds = SensorId.values();

        for ( SensorId sensorId : sensorIds ) {
            Thread sensor = new Thread(new UDPSensor(sensorId.name(), host, UDPPort));
            sensor.start();
        }

        System.out.println("Sensors S1, S2, S3, S4, S5 and S6 initialized.");
    }
}