package org.example;

import org.apache.commons.net.ftp.FTPClient;

public class BasicFTPClient {
    public static void main(String[] args) {

        FTPClient client = new FTPClient();

        try {
            client.connect("ftp.rediris.es");

            System.out.println(client.getReplyString());

            boolean login = client.login("anonymus", "anonymus");

            if ( login ) {
                System.out.println("Correct login");
                System.out.println("Current directory: " + client.printWorkingDirectory());
            } else {
                System.out.println("Incorrect login");
            }

            client.logout();
            client.disconnect();

        } catch ( Exception exception ) {
            System.out.println("Error: " + exception.getMessage());
        }
    }
}
