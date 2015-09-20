// CS 6421 - Simple Message Board Client in Java
// Luyao Ma
// Version 1.4 Data 09/07/2015
// Compile with: javac MsgClient
// Run with: java MsgClient 52.20.224.192 MyName MyMessage (or java MsgClient "52.20.224.192" "My Name" "My Message")

import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;



public class MsgClient {
    public static void main(String[] args) {
        int length = args.length;
        if (length !=3) {
            System.out.println("Input arguments are not correct.");
        }
        String host = "umkk344c861c.maluyao002.koding.io";
        int portnum = 12345;
        String[] commandline = new String[3];
        for(int i=0;i<args.length;i++){
            commandline[i] = args[i];
        }
        String hostname = commandline[0];
        String name = commandline[1];
        String message = commandline[2];
        try {
            Socket clientsocket = new Socket (hostname,portnum);
            //establish a client Socket
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            //put user input into BufferedReader
            PrintWriter out = new PrintWriter(clientsocket.getOutputStream(), true);
            //put the input from socket into a PrintWriter, message from user
            //BufferedReader is =new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
            //put the output from socket into a BufferedReader, message from server
            out.println(name);
            out.println(message);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
