package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTCP {
    public static void main(String[] args) {
        String host = "localhost";
        int puerto = 6000;

        try (
                Socket socket = new Socket(host, puerto);
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
                Scanner teclado = new Scanner(System.in)
        ) {
            System.out.println(entrada.readLine());
            System.out.println(entrada.readLine());

            while (true) {
                System.out.print("> ");
                String comando = teclado.nextLine();

                salida.println(comando);

                String respuesta;
                while ((respuesta = entrada.readLine()) != null) {
                    if (respuesta.equals("FIN")) {
                        break;
                    }
                    System.out.println(respuesta);
                }

                if (comando.equalsIgnoreCase("SALIR")) {
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Error en cliente TCP: " + e.getMessage());
        }
    }
}