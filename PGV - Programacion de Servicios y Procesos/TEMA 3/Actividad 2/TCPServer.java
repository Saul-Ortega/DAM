package org.example;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer implements Runnable {

    private final int port;
    private final ExecutorService pool;

    public TCPServer(int port) {
        this.port = port;
        this.pool = Executors.newFixedThreadPool(3);
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("[TCP] Server listening on port " + port);

            while ( true ) {
                Socket client = serverSocket.accept();

                System.out.println("[TCP] Client connected from "
                        + client.getInetAddress().getHostAddress()
                        + ":" + client.getPort());

                pool.execute(new ClientTCPHandler(client));
            }

        } catch ( Exception e ) {
            System.out.println("[TCP] Error: " + e.getMessage());
        }
    }
}