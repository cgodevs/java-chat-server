package chat.java.project.study.carol.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import chat.java.project.study.carol.client.Client;

public class Server {

	private int port;
	private List<PrintStream> clientsOutputList;
	private List<Socket> clients;
	
	public Server (int port) {
		this.port = port;
		this.clientsOutputList = new ArrayList<PrintStream>();
	}

    public void start() throws IOException {
        ServerSocket server = new ServerSocket(this.port);
        System.out.println("*** Port 12345 has been opened ***");

        while(true) {            
            Socket client = server.accept(); // blocking method waiting for a client connection
            System.out.println("New connection established with client at IP " 
            + client.getInetAddress().getHostAddress() + " and port " + client.getPort()); // gets the client's IP and port number
            
            PrintStream clientOutput = new PrintStream(client.getOutputStream()); // adds the customer's output to the list
            this.clientsOutputList.add(clientOutput);
            
            MessageSharingTask messageSharing =  new MessageSharingTask(client.getInputStream(), this);
            new Thread(messageSharing).start();  // starts a separate thread for treating one client's connection 
        }
    }

    public void shareMessageWithAll(String msg) { // to all clients (server not included)
        for (PrintStream console : clientsOutputList) {
        	console.println(msg);
        }
    }    
}