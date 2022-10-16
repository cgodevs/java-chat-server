package chat.java.project.study.carol.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	private int port;
	private List<PrintStream> clientsOutputsList;
	
	public Server (int port) {
		this.port = port;
		this.clientsOutputsList = new ArrayList<PrintStream>();
	}

    public void start() throws IOException {
        ServerSocket server = new ServerSocket(this.port);
        System.out.println("*** Port 12345 has been opened ***");

        while(true) {            
            Socket client = server.accept(); // blocking method waiting for a client connection
            System.out.println("New connection established with cliente " + client.getInetAddress().getHostAddress()); // gets a client's IP
            
            PrintStream clientOutput = new PrintStream(client.getOutputStream()); // adds the customer's output to the list
            this.clientsOutputsList.add(clientOutput);

        }
    }

    public void shareMessageToAll(String msg) { // to all clients, including the server        
        for (PrintStream console : this.clientsOutputsList) {
        	console.println(msg);
        }
    }    
}