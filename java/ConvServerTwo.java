/******************************************************************************
 *  Author: Luyao Ma, Yu Ma
 *  CS 6421 - Multiple conversion
 *  Compilation:  javac ConvServerTwo.java
 *  Execution:    java ConvServerTwo portnumber
 *  Description: This is a convesion server that converse kilograms(kg) to puonds(lbs) or viceversa
 ******************************************************************************/

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConvServerTwo {

    public static void process (Socket clientSocket) throws IOException {
        // open up IO streams
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        /* read and print the client's request */
        // readLine() blocks until the server receives a new line from client
        String userInput;
        userInput = in.readLine();
        userInput = userInput.replaceAll("\r|\n","");
        if (userInput  == null | userInput.split(" ").length != 3) {
            System.out.println("Error reading message");
            out.close();
            in.close();
            clientSocket.close();
        }
        System.out.println("Received message: " + userInput);
        //--TODO: add your converting functions here, msg = func(userInput);
        double msg = tfunction(userInput);
        System.out.println(msg);
        if (msg == -1){
            out.println("Input format is incorrect!");
        }else{
            out.println(msg);
        }  
        // close IO streams, then socket
        out.close();
        in.close();
        clientSocket.close();
    }
    
    public static double tfunction(String input){
        String[] tokens = input.split(" ");
        double output = 0.00;
        double amount = Double.parseDouble(tokens[2]);
        System.out.println(amount);
        if (tokens[0].equals("kg") && tokens[1].equals("lbs")){
            output = amount*2.2;
            return output;
        }else if(tokens[0].equals("lbs") && tokens[1].equals("kg")){ 
            output = amount/2.2;
            return output;
        }else{
            return -1;
        }
    }
    
    public static void main(String[] args) throws Exception {

        //check if argument length is invalid
        if(args.length != 1) {
            System.err.println("Usage: java ConvServer port");
        }
        // create socket
        int port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);
        System.err.println("Started server on port " + port);

        // wait for connections, and process
        try {
            while(true) {
                // a "blocking" call which waits until a connection is requested
                Socket clientSocket = serverSocket.accept();
                System.err.println("\nAccepted connection from client");
                process(clientSocket);
            }

        }catch (IOException e) {
            System.err.println("Connection Error");
        }
        System.exit(0);
    }
}
