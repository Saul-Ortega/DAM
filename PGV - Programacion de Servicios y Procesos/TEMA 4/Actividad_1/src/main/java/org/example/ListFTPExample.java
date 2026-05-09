package org.example;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class ListFTPExample {
    public static void main(String[] args) {

        FTPClient client = new FTPClient();

        String server = "127.0.0.1";
        int port = 21;
        String user = "USER";
        String password = "PASSWORD";

        try {
            //WE CONNECT TO THE FTP SERVER
            client.connect(server, port);

            //SHOW THE INITIAL RESPONSE FROM THE SERVER
            System.out.println(client.getReplyString());

            //TEST IF THE CONNECTION IS ACCEPTED
            int replyCode = client.getReplyCode();

            if ( !FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Server dismissed the connection");
                client.disconnect();
                return;
            }

            //LOGIN WITH USER AND PASSWORD
            boolean correctLogin = client.login(user, password);

            if ( !correctLogin ) {
                System.out.println("Incorrect login");
                System.out.println(client.getReplyString());
                client.disconnect();
                return;
            }

            System.out.println("Correct login");

            //ACTIVATE PASSIVE MODE
            client.enterLocalPassiveMode();

            System.out.println("Current directory: " + client.printWorkingDirectory());

            //GET THE FILES AND FOLDERS LIST
            FTPFile[] files = client.listFiles();

            System.out.println("Directory content:");

            for ( FTPFile file : files ) {
                if ( file.isDirectory() ) {
                    System.out.println("[FOLDER] " + file.getName());
                } else if ( file.isFile() ) {
                    System.out.println("[FILE] " + file.getName() + " - " + file.getSize() + " bytes");
                }
            }

            //LOGOUT AND CLOSE THE CONNECTION
            client.logout();
            client.disconnect();

            System.out.println("Connection closed successfully");

        } catch ( Exception exception ) {
            System.out.println("Error: " + exception.getMessage());

            try {
                if  ( client.isConnected() ) {
                    client.disconnect();
                }
            } catch ( Exception ex ) {
                System.out.println("Error closing connection");
            }
        }

    }
}
