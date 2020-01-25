package com.ishank;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.ServerError;
import java.rmi.ServerException;
import java.rmi.server.ServerNotActiveException;
import java.util.Scanner;

public class clientHttp {
    public static void main(String[] args) throws IOException, UnknownHostException {
        try {
            //InetAddress variable to get the host name
            InetAddress host = InetAddress.getLocalHost();
            //socket object for the client side
            Socket socket = new Socket(host.getHostName(),6666);
            //input and output streams
            System.out.println("server connected\n");

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            //server sends hello message to it's clients
            System.out.println("message from server: " + dataInputStream.readUTF());

            Scanner input = new Scanner(System.in);
            String clientMessage="",serverMessage="";

            //if client message is equal to bye, terminate the connection with the server
            while (!clientMessage.equals("Bye")){
                System.out.println("request the file from the server\n");
                clientMessage = input.nextLine();
                //sending the input to the server
                dataOutputStream.writeUTF(clientMessage);
                dataOutputStream.flush();

                //reading the reply from the server and printing on the client screen
                serverMessage = dataInputStream.readUTF();
                System.out.println(serverMessage);
            }
            //exit the loop once the client types Bye
            System.out.println("connection closed");
            dataOutputStream.close();
            //close the socket
            socket.close();
        }
        catch (ServerException e){
            System.out.println("Server disconnected");
        }
        catch (FileNotFoundException f){
            System.out.println("File not found");
        }
        catch (NullPointerException g){
            System.out.println("File is empty");
        }
        catch (Exception h){
            System.out.println("Client disconnected");
        }
    }
}
