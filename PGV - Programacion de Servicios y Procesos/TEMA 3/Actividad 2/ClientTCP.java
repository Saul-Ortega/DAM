package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 6000;

        try (
                Socket socket = new Socket(host, port);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner keyboard = new Scanner(System.in)
        ) {
            System.out.println(in.readLine());
            System.out.println(in.readLine());

            while ( true ) {
                System.out.print("> ");
                String command = keyboard.nextLine();

                out.println(command);

                String response;
                while ( (response = in.readLine()) != null ) {
                    if (response.equals("END")) {
                        break;
                    }
                    System.out.println(response);
                }

                if ( command.equalsIgnoreCase("EXIT") ) {
                    break;
                }
            }

        } catch ( Exception e ) {
            System.out.println("Error in TCP Client: " + e.getMessage());
        }
    }
}