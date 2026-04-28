package org.example;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTCP implements Runnable {

    private final int puerto;
    private final ExecutorService pool;

    public ServidorTCP(int puerto) {
        this.puerto = puerto;
        this.pool = Executors.newFixedThreadPool(3);
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("[TCP] Servidor escuchando en puerto " + puerto);

            while (true) {
                Socket cliente = serverSocket.accept();

                System.out.println("[TCP] Cliente conectado desde "
                        + cliente.getInetAddress().getHostAddress()
                        + ":" + cliente.getPort());

                pool.execute(new ClienteTCPHandler(cliente));
            }

        } catch (Exception e) {
            System.out.println("[TCP] Error: " + e.getMessage());
        }
    }
}