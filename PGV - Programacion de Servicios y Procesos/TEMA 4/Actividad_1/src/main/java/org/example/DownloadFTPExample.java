package org.example;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class DownloadFTPExample {
    public static void main(String[] args) {

        FTPClient client = new FTPClient();

        String server = "127.0.0.1";
        int port = 21;
        String user = "USER";
        String password = "PASSWORD";

        try {
            client.connect(server, port);

            System.out.println(client.getReplyString());

            int replyCode = client.getReplyCode();

            if ( !FTPReply.isPositiveCompletion(replyCode) ) {
                System.out.println("Server dismissed connection");
                client.disconnect();
                return;
            }

            boolean correctLogin = client.login(user, password);

            if ( !correctLogin ) {
                System.out.println("Incorrect login");
                System.out.println(client.getReplyString());
                client.disconnect();
                return;
            }

            System.out.println("Correct login");

            client.enterLocalPassiveMode();

            client.setFileType(FTPClient.BINARY_FILE_TYPE);

            String remoteFile = "uploaded_file.txt";

            String localFile = "downloaded_file.txt";

            try ( OutputStream outputStream = new FileOutputStream(localFile) ) {
                boolean downloaded = client.retrieveFile(remoteFile, outputStream);

                if ( downloaded ) {
                    System.out.println("File downloaded successfully");
                } else {
                    System.out.println("Could not download file");

                    System.out.println(client.getReplyString());
                }
            }

            client.logout();
            client.disconnect();

            System.out.println("Connection closed successfully");

        } catch ( Exception exception ) {
            System.out.println("Error: " + exception.getMessage());

            try {
                if ( client.isConnected() ) {
                    client.disconnect();
                }
            } catch ( Exception ex ) {
                System.out.println("Error connection failed");
            }
        }
    }
}
