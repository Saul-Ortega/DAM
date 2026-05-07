package org.example;

import org.apache.commons.net.ftp.FTPClient;

public class BasicFTPClient {
    public static void main(String[] args) {

        FTPClient client = new FTPClient();

        try {
//            client.connect("ftp.rediris.es");
            client.connect("127.0.0.1", 21);

            System.out.println(client.getReplyString());

//            boolean login = client.login("anonymus", "anonymus");
            boolean login = client.login("USER", "PASSWORD");

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
