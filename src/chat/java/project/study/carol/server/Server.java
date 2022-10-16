package chat.java.project.study.carol.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

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
            
            MessageSharingTask messageSharing =  new MessageSharingTask(client.getInputStream(), this);
            new Thread(messageSharing).start();  // starts a separate thread for treating one client's connection 
        }
    }

    public void shareMessageToAll(String msg) { // to all clients (server not included)
        for (PrintStream console : this.clientsOutputsList) {
        	console.println(msg);
        }
    }    
}