package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class SensorUDP implements Runnable {

    private final String sensorId;
    private final String host;
    private final int puertoServidor;
    private final Random random = new Random();

    public SensorUDP(String sensorId, String host, int puertoServidor) {
        this.sensorId = sensorId;
        this.host = host;
        this.puertoServidor = puertoServidor;
    }

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress direccionServidor = InetAddress.getByName(host);

            while (true) {
                double temperatura = generarTemperatura();
                String mensaje = sensorId + "," + temperatura;

                byte[] buffer = mensaje.getBytes();
                DatagramPacket paquete = new DatagramPacket(
                        buffer,
                        buffer.length,
                        direccionServidor,
                        puertoServidor
                );

                socket.send(paquete);

                System.out.println("[" + sensorId + "] enviada temperatura: " + temperatura + " ºC");

                Thread.sleep(2000);
            }

        } catch (Exception e) {
            System.out.println("Error en " + sensorId + ": " + e.getMessage());
        }
    }

    private double generarTemperatura() {
        double valor = 20 + random.nextDouble() * 10; // entre 20 y 30
        return Math.round(valor * 10.0) / 10.0;
    }
}