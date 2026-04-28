package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;

public class UDPServer implements Runnable {

    private final int port;

    // Shared state
    private static final Map<String, Double> temperatures = new HashMap<>();

    public UDPServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("[UDP] Server listening on port " + port);

            while ( true ) {
                byte[] buffer = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);

                socket.receive(datagramPacket);

                String message = new String(datagramPacket.getData(), 0, datagramPacket.getLength()).trim();
                System.out.println("[UDP] Received message: " + message);

                processMessage(message);
            }

        } catch ( Exception e ) {
            System.out.println("[UDP] Error: " + e.getMessage());
        }
    }

    private void processMessage(String message) {
        try {
            String[] parts = message.split(",");

            if ( parts.length != 2 ) {
                System.out.println("[UDP] Bad message.");
                return;
            }

            String sensorId = parts[0].trim();
            double temperature = Double.parseDouble(parts[1].trim());

            updateTemperature(sensorId, temperature);

            System.out.println("[UDP] Saved -> " + sensorId + ": " + temperature + " ºC");

            if ( temperature >= 30.00 ) {
                System.out.println("[UDP] ALERT! Critical temperature reached in " + sensorId + ": " + temperature + " ºC");
            }

        } catch ( NumberFormatException e ) {
            System.out.println("[UDP] Invalid temperature.");
        }
    }

    public static synchronized void updateTemperature(String sensorId, double temperature) {
        temperatures.put(sensorId, temperature);
    }

    public static synchronized Double getTemperature(String sensorId) {
        return temperatures.get(sensorId);
    }

    public static synchronized Map<String, Double> getTemperatures() {
        return new HashMap<>(temperatures);
    }
}