package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class ClienteTCPHandler implements Runnable {

    private final Socket socket;

    public ClienteTCPHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)
        ) {
            salida.println("Conectado al servidor TCP.");
            salida.println("Comandos: LISTAR, ULTIMO S1, ULTIMO S2, SALIR");

            String comando;

            while ((comando = entrada.readLine()) != null) {
                comando = comando.trim();

                if (comando.equalsIgnoreCase("LISTAR")) {
                    procesarListar(salida);

                } else if (comando.toUpperCase().startsWith("ULTIMO")) {
                    procesarUltimo(comando, salida);

                } else if (comando.equalsIgnoreCase("SALIR")) {
                    salida.println("Conexión cerrada.");
                    salida.println("FIN");
                    break;

                } else {
                    salida.println("Comando no reconocido.");
                    salida.println("FIN");
                }
            }

        } catch (Exception e) {
            System.out.println("[TCP] Error con cliente: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                // Ignorar
            }
            System.out.println("[TCP] Cliente desconectado.");
        }
    }

    private void procesarListar(PrintWriter salida) {
        Map<String, Double> datos = ServidorUDP.obtenerTodas();

        if (datos.isEmpty()) {
            salida.println("No hay temperaturas registradas.");
            salida.println("FIN");
            return;
        }

        salida.println("Últimas temperaturas:");
        for (Map.Entry<String, Double> entry : datos.entrySet()) {
            salida.println(entry.getKey() + " -> " + entry.getValue() + " ºC");
        }
        salida.println("FIN");
    }

    private void procesarUltimo(String comando, PrintWriter salida) {
        String[] partes = comando.split("\\s+");

        if (partes.length != 2) {
            salida.println("Uso correcto: ULTIMO S1");
            salida.println("FIN");
            return;
        }

        String sensorId = partes[1];
        Double temperatura = ServidorUDP.obtenerTemperatura(sensorId);

        if (temperatura == null) {
            salida.println("No hay datos del sensor " + sensorId);
        } else {
            salida.println("Última temperatura de " + sensorId + ": " + temperatura + " ºC");
        }

        salida.println("FIN");
    }
}