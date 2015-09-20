/******************************************************************************
 *  Author: Luyao Ma, Yu Ma
 *  CS 6421 - Multiple Conversation
 *  Compilation:  javac ProxyServer.java
 *  Execution:    java ProxyServer. portnumber
 *  Description: This is a proxy server that conncects five convesion server to fullfill the function of transferring grams of bananas to yens.
 ******************************************************************************/

import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ProxyServer {

    public static void process (Socket clientSocket) throws IOException {
        // open up IO streams
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        /* Write a welcome message to the client */
        out.println("Welcome to the grams of banana (g) to yen (y) conversion server!");
        out.println("This set of servers will fullfill the following transitions");
        out.println("Forward conversion:");
        out.println("grams(g) => kilograms(kg) (1000:1)");
        out.println("kilograms(kg) => pounds(lbs) (1:2.2)");
        out.println("pounds(lbs) => ounces(oz) (1:16)");
        out.println("ounces(oz) of bananas => dollars($) (16: 1)");
        out.println("dollars($) => yens(y) (1:120)");
        out.println("The conversion in reverse order can also be accepted.");
        /* read and print the client's request */
        // readLine() blocks until the server receives a new line from client
        String userInput;
        userInput = in.readLine();
        //check if user input is valid
        if (userInput  == null | userInput.split(" ").length != 3) {
            System.out.println("Error reading message");
            out.close();
            in.close();
            clientSocket.close();
        }
        System.out.println("Received message: " + userInput);
        
        // spit input into an string array
        String[] tokens = userInput.split(" ");
        double output = 0.00;
        if(tokens[2].matches("[0-9]+")){
            double amount = Double.parseDouble(tokens[2]);
            //if the input format is "g y value", do the forward conversion
            if (tokens[0].equals("g") && tokens[1].equals("y")){
                output = tfunction(amount);
                String finaloutput = String.format("%.2f", output);
                out.println("The value of "+tokens[2]+" grams of banana is "+finaloutput+" yens.");
            }
            //if the format is "y g value", do the reverse conversion
            else if(tokens[0].equals("y") && tokens[1].equals("g")){ 
                output = treverfunction(amount);
                String finaloutput = String.format("%.2f", output);
                out.println(tokens[2]+" yens can buy "+ finaloutput +" grams of banana.");
            }else{
                out.println("Input format is incorrect!");
            }
        }else{
            out.println("Input format is incorrect!");
        }
        // close IO streams, then socket
        out.close();
        in.close();
        clientSocket.close();
    }
    
    public static double tfunction(double input){
        String hostname = "umkk344c861c.maluyao002.koding.io";
        int portnumOne = 5555;
        int portnumTwo = 6666;
        int portnumThree = 7777;
        int portnumFour = 8888;
        int portnumFive = 9999;
        Double resultone = 0.0;
        Double resulttwo = 0.0;
        Double resultthree = 0.0;
        Double resultfour = 0.0;
        Double resultfive = 0.0;
        //Connect the grams(g) to kilograms(kg) convesion server
        try {
            // each time conncet to a new server, establish a new socket and conncet
            Socket PtoFsocket = new Socket (hostname,portnumOne);
            BufferedReader inFromFirst = new BufferedReader(new InputStreamReader(PtoFsocket.getInputStream()));
            PrintWriter outToFirst = new PrintWriter(PtoFsocket.getOutputStream(), true);
            outToFirst.println("g kg "+ input);
            String firstString = inFromFirst.readLine();
            resultone = Double.parseDouble(firstString);
            System.out.println(input+ " grams is "+resulttwo+ " kilograms!");
            inFromFirst.close();
            outToFirst.flush();
            outToFirst.close();
            PtoFsocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //Connect the kilograms(kg) to pounds(lbs) convesion server
        try {
            Socket PtoSsocket = new Socket (hostname,portnumTwo);
            BufferedReader inFromSecond = new BufferedReader(new InputStreamReader(PtoSsocket.getInputStream()));
            PrintWriter outToSecond = new PrintWriter(PtoSsocket.getOutputStream(), true);
            outToSecond.println("kg lbs "+ resultone);
            String secondString = inFromSecond.readLine();
            resulttwo = Double.parseDouble(secondString);
            System.out.println(resultone + " kilograms is "+ resulttwo+" pounds!");
            inFromSecond.close();
            outToSecond.flush();
            outToSecond.close();
            PtoSsocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //Connect the pounds(lbs) to ounces(oz) convesion server
        try {
            Socket PtoTsocket = new Socket (hostname,portnumThree);
            BufferedReader inFromThird = new BufferedReader(new InputStreamReader(PtoTsocket.getInputStream()));
            PrintWriter outToThird = new PrintWriter(PtoTsocket.getOutputStream(), true);
            outToThird.println("lbs oz "+ resulttwo);
            String ThirdString = inFromThird.readLine();
            resultthree = Double.parseDouble(ThirdString);
            System.out.println(resulttwo+" pounds is "+ resultthree+ " ounces!");
            inFromThird.close();
            outToThird.flush();
            outToThird.close();
            PtoTsocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //Connect the ounces(oz) to dollar($) convesion server
        try {
            Socket PtoFrsocket = new Socket (hostname,portnumFour);
            BufferedReader inFromFourth = new BufferedReader(new InputStreamReader(PtoFrsocket.getInputStream()));
            PrintWriter outToFourth = new PrintWriter(PtoFrsocket.getOutputStream(), true);
            outToFourth.println("oz $ "+ resultthree);
            String FourthString = inFromFourth.readLine();
            resultfour = Double.parseDouble(FourthString);
            System.out.println(resultthree+" ounces bananas is "+ resultfour+" dollars!");
            inFromFourth.close();
            outToFourth.flush();
            outToFourth.close();
            PtoFrsocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        //Connect the dollar($) to yens(y) convesion server
        try {
            Socket PtoFivesocket = new Socket (hostname,portnumFive);
            BufferedReader inFromFifth = new BufferedReader(new InputStreamReader(PtoFivesocket.getInputStream()));
            PrintWriter outToFifth = new PrintWriter(PtoFivesocket.getOutputStream(), true);
            outToFifth.println("$ y "+ resultfour);
            String fifthString = inFromFifth.readLine();
            resultfive = Double.parseDouble(fifthString);
            System.out.println(resultfour+ " dollars is "+ resultfive+ " yens");
            inFromFifth.close();
            outToFifth.flush();
            outToFifth.close();
            PtoFivesocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return resultfive;
    }
    
    //Here is the reverse convesion function, which connect servers in a reverse order
    public static double treverfunction(double input){
        String hostname = "umkk344c861c.maluyao002.koding.io";
        int portnumOne = 5555;
        int portnumTwo = 6666;
        int portnumThree = 7777;
        int portnumFour = 8888;
        int portnumFive = 9999;
        Double resultone = 0.0;
        Double resulttwo = 0.0;
        Double resultthree = 0.0;
        Double resultfour = 0.0;
        Double resultfive = 0.0;
        String ReceivedString;
        try {
            Socket clientsocket = new Socket (hostname,portnumFive);
            //establish a client Socket
            BufferedReader in = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
            //put user input into BufferedReader
            PrintWriter out = new PrintWriter(clientsocket.getOutputStream(), true);
            //put the input from socket into a PrintWriter, message from user
            //BufferedReader is =new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
            //put the output from socket into a BufferedReader, message from server
            out.println("y $ "+ input);
            ReceivedString = in.readLine();
            resultone = Double.parseDouble(ReceivedString);
            System.out.println(input +" yens is "+resultone+ "dollars!");
            in.close();
            out.flush();
            out.close();
            clientsocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Socket clientsocket = new Socket (hostname,portnumFour);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientsocket.getOutputStream(), true);
            out.println("$ oz "+ resultone);
            ReceivedString = in.readLine();
            resulttwo = Double.parseDouble(ReceivedString);
            System.out.println(resultone+ " dollars worth "+ resulttwo+ " ounces of bananas!");
            in.close();
            out.flush();
            out.close();
            clientsocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Socket clientsocket = new Socket (hostname,portnumThree);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientsocket.getOutputStream(), true);
            out.println("oz lbs "+ resulttwo);
            ReceivedString = in.readLine();
            resultthree = Double.parseDouble(ReceivedString);
            System.out.println(resulttwo+" ounces is "+ resultthree +" pounds!");
            in.close();
            out.flush();
            out.close();
            clientsocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Socket clientsocket = new Socket (hostname,portnumTwo);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientsocket.getOutputStream(), true);
            out.println("lbs kg "+ resultthree);
            ReceivedString = in.readLine();
            resultfour = Double.parseDouble(ReceivedString);
            System.out.println(resultthree+ " puonds is "+ resultfour+" kilograms!");
            in.close();
            out.flush();
            out.close();
            clientsocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Socket clientsocket = new Socket (hostname,portnumOne);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientsocket.getOutputStream(), true);
            out.println("kg g "+ resultfour);
            ReceivedString = in.readLine();
            resultfive = Double.parseDouble(ReceivedString);
            System.out.println(resultfour+ " kilograms is "+ resultfive+ " grams!");
            in.close();
            out.flush();
            out.close();
            clientsocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return resultfive;
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
