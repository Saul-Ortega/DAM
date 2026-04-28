package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class UDPSensor implements Runnable {

    private final String sensorId;
    private final String host;
    private final int serverPort;
    private final Random random = new Random();

    public UDPSensor(String sensorId, String host, int serverPort) {
        this.sensorId = sensorId;
        this.host = host;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        try ( DatagramSocket socket = new DatagramSocket() ) {
            InetAddress serverAddress = InetAddress.getByName(host);

            while ( true ) {
                double temperature = generateTemperature();
                String message = sensorId + "," + temperature;

                byte[] buffer = message.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(
                        buffer,
                        buffer.length,
                        serverAddress,
                        serverPort
                );

                socket.send(datagramPacket);

                System.out.println("[" + sensorId + "] sent temperature: " + temperature + " ºC");

                Thread.sleep(2000);
            }

        } catch ( Exception e ) {
            System.out.println("Error en " + sensorId + ": " + e.getMessage());
        }
    }

    private double generateTemperature() {
        double value = 20 + random.nextDouble() * 10; // Between 20 and 30
        return Math.round(value * 10.0) / 10.0;
    }
}