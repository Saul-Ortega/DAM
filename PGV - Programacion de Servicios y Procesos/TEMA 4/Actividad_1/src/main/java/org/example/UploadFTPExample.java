package org.example;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.FileInputStream;
import java.io.InputStream;

public class UploadFTPExample {
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
                System.out.print("Server dismissed connection");
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

            String localFile = "local_file.txt";

            String remoteFile = "uploaded_file.txt";

            try ( InputStream inputStream = new FileInputStream(localFile) ) {
                boolean uploaded = client.storeFile(remoteFile, inputStream);

                if ( uploaded ) {
                    System.out.println("File uploaded successfully");
                } else {
                    System.out.println("Could not upload file");
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
