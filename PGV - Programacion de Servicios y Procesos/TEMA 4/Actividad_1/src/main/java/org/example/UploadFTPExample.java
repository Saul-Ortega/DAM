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
            //WE CONNECT TO THE FTP SERVER
            client.connect(server, port);

            //WE SHOW THE INITIAL RESPONSE FROM THE SERVER
            System.out.println(client.getReplyString());

            //TEST IF THE CONNECTION IS ACCEPTED
            int replyCode = client.getReplyCode();

            if ( !FTPReply.isPositiveCompletion(replyCode) ) {
                System.out.print("Server dismissed connection");
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

            //WORK WITH BINARY FILES
            client.setFileType(FTPClient.BINARY_FILE_TYPE);

            //LOCAL FILE WE WANT TO UPLOAD
            String localFile = "src/main/resources/local_file.txt";

            //NAME OF THE UPLOADED FILE IN THE FTP SERVER
            String remoteFile = "uploaded_file.txt";

            //OPEN THE LOCAL FILE
            try ( InputStream inputStream = new FileInputStream(localFile) ) {
                //UPLOAD THE FILE TO THE FTP SERVER
                boolean uploaded = client.storeFile(remoteFile, inputStream);

                if ( uploaded ) {
                    System.out.println("File uploaded successfully");
                } else {
                    System.out.println("Could not upload file");
                    System.out.println(client.getReplyString());
                }
            }

            //LOGOUT AND CLOSE THE CONNECTION
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
