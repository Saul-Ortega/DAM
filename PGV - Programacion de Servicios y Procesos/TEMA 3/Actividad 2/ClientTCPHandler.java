package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClientTCPHandler implements Runnable {

    private final Socket socket;

    public ClientTCPHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println("Connected to TCP Server.");

            SensorId[] sensorIds = SensorId.values();
            List<String> lastCommandMessage = new ArrayList<>();

            for ( SensorId sensorId : sensorIds ) {
                lastCommandMessage.add("LAST " + sensorId.name());
            }

            out.println("Commands: LIST, " + String.join(", ", lastCommandMessage) + ", ALERTS, AVERAGE, EXIT");

            String command;

            while ( (command = in.readLine()) != null ) {
                command = command.trim();

                if ( command.equalsIgnoreCase("LIST") ) {
                    processList(out);

                } else if ( command.toUpperCase().startsWith("LAST") ) {
                    processLast(command, out);

                } else if ( command.equalsIgnoreCase("ALERTS") ) {
                    processAlerts(out);

                } else if ( command.equalsIgnoreCase("AVERAGE") ) {
                    processAverage(out);

                } else if ( command.equalsIgnoreCase("EXIT") ) {
                    out.println("Connection closed.");
                    out.println("END");
                    break;

                } else {
                    out.println("Command not recognized.");
                    out.println("END");
                }
            }

        } catch ( Exception e ) {
            System.out.println("[TCP] Error with client: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch ( Exception e ) {
                // Ignore
            }
            System.out.println("[TCP] Client disconnected.");
        }
    }

    private void processList(PrintWriter out) {
        Map<String, Double> data = UDPServer.getTemperatures();

        if ( data.isEmpty() ) {
            out.println("There are not any registered temperatures.");
            out.println("END");
            return;
        }

        out.println("Last temperatures:");
        for ( Map.Entry<String, Double> entry : data.entrySet() ) {
            out.println(entry.getKey() + " -> " + entry.getValue() + " ºC");
        }
        out.println("END");
    }

    private void processLast(String command, PrintWriter out) {
        String[] parts = command.split("\\s+");

        if ( parts.length != 2 ) {
            out.println("Correct use: LAST S1");
            out.println("END");
            return;
        }

        String sensorId = parts[1];
        Double temperature = UDPServer.getTemperature(sensorId);

        if (temperature == null) {
            out.println("There are not any sensor data " + sensorId);
        } else {
            out.println("Last temperature of " + sensorId + ": " + temperature + " ºC");
        }

        out.println("END");
    }

    private void processAlerts(PrintWriter out) {
        Map<String, Double> data = UDPServer.getTemperatures();

        if ( data.isEmpty() ) {
            out.println("There are not any registered temperatures.");
            out.println("END");
            return;
        }

        out.println("Alerts in sensors:");

        int numberOfAlerts = 0;

        for ( Map.Entry<String, Double> entry : data.entrySet() ) {
            if ( entry.getValue() >= 30.0 ) {
                out.println("[ALERT] " + entry.getKey() + " -> " + entry.getValue() + " ºC");
                numberOfAlerts++;
            }
        }

        if ( numberOfAlerts == 0 ) {
            out.println("There are no alerts.");
        }

        out.println("END");
    }

    private void processAverage(PrintWriter out) {
        Map<String, Double> data = UDPServer.getTemperatures();

        if ( data.isEmpty() ) {
            out.println("There are not any registered temperatures.");
            out.println("END");
            return;
        }

        out.println("Average temperatures:");

        double temperaturesSum = 0;

        for ( Map.Entry<String, Double> entry : data.entrySet() ) {
            temperaturesSum += entry.getValue();
        }

        double averageTemperature = temperaturesSum / data.size();

        out.println("Average -> " + Math.round(averageTemperature * 10.0) / 10.0 + " ºC");
        out.println("END");
    }
}