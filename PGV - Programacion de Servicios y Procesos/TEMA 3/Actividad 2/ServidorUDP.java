package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;

public class ServidorUDP implements Runnable {

    private final int puerto;

    // Estado compartido
    private static final Map<String, Double> temperaturas = new HashMap<>();

    public ServidorUDP(int puerto) {
        this.puerto = puerto;
    }

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket(puerto)) {
            System.out.println("[UDP] Servidor escuchando en puerto " + puerto);

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);

                socket.receive(paquete);

                String mensaje = new String(paquete.getData(), 0, paquete.getLength()).trim();
                System.out.println("[UDP] Mensaje recibido: " + mensaje);

                procesarMensaje(mensaje);
            }

        } catch (Exception e) {
            System.out.println("[UDP] Error: " + e.getMessage());
        }
    }

    private void procesarMensaje(String mensaje) {
        try {
            String[] partes = mensaje.split(",");

            if (partes.length != 2) {
                System.out.println("[UDP] Mensaje malformado.");
                return;
            }

            String sensorId = partes[0].trim();
            double temperatura = Double.parseDouble(partes[1].trim());

            actualizarTemperatura(sensorId, temperatura);

            System.out.println("[UDP] Guardado -> " + sensorId + ": " + temperatura + " ºC");

        } catch (NumberFormatException e) {
            System.out.println("[UDP] Temperatura no válida.");
        }
    }

    public static synchronized void actualizarTemperatura(String sensorId, double temperatura) {
        temperaturas.put(sensorId, temperatura);
    }

    public static synchronized Double obtenerTemperatura(String sensorId) {
        return temperaturas.get(sensorId);
    }

    public static synchronized Map<String, Double> obtenerTodas() {
        return new HashMap<>(temperaturas);
    }
}