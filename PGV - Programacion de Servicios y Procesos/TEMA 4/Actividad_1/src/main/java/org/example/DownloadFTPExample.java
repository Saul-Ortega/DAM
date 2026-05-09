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
            //WE CONNECT TO THE FTP SERVER
            client.connect(server, port);

            //SHOW THE INITIAL RESPONSE FROM THE SERVER
            System.out.println(client.getReplyString());

            //TEST IF THE CONNECTION IS ACCEPTED
            int replyCode = client.getReplyCode();

            if ( !FTPReply.isPositiveCompletion(replyCode) ) {
                System.out.println("Server dismissed connection");
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

            //WE WORK WITH BINARY FILES
            client.setFileType(FTPClient.BINARY_FILE_TYPE);

            //FILE THAT EXISTS IN THE FTP SERVER
            String remoteFile = "uploaded_file.txt";

            //NAME OF FILE WHICH WILL BE STORED IN OUR PROJECT
            String localFile = "downloaded_file.txt";

            //CREATE THE OUTPUT STREAM TO SAVE THE DOWNLOADED FILE
            try ( OutputStream outputStream = new FileOutputStream(localFile) ) {
                //WE DOWNLOAD THE FILE FROM THE FTP SERVER
                boolean downloaded = client.retrieveFile(remoteFile, outputStream);

                if ( downloaded ) {
                    System.out.println("File downloaded successfully");
                } else {
                    System.out.println("Could not download file");

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
