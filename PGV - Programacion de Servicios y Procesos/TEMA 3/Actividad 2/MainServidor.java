package org.example;

public class MainServidor {
    public static void main(String[] args) {
        Thread hiloUDP = new Thread(new ServidorUDP(5000));
        Thread hiloTCP = new Thread(new ServidorTCP(6000));

        hiloUDP.start();
        hiloTCP.start();

        System.out.println("Servidor iniciado.");
        System.out.println("UDP en puerto 5000.");
        System.out.println("TCP en puerto 6000.");
    }
}