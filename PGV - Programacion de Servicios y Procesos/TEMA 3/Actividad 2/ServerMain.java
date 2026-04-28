package org.example;

public class ServerMain {
    public static void main(String[] args) {
        Thread UDPThread = new Thread(new UDPServer(5000));
        Thread TCPThread = new Thread(new TCPServer(6000));

        UDPThread.start();
        TCPThread.start();

        System.out.println("Initialized server.");
        System.out.println("UDP on port 5000.");
        System.out.println("TCP on port 6000.");
    }
}