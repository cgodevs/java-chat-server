package chat.java.project.study.carol.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import chat.java.project.study.carol.client.Client;

public class Server {

	private int port;
	private List<PrintStream> clientsOutputList;
	private AtomicBoolean isOn;
	ServerSocket server;
	
	public Server (int port) throws IOException {
		this.port = port;
		this.clientsOutputList = new ArrayList<PrintStream>();
		this.isOn = new AtomicBoolean(true);
		this.server = new ServerSocket(this.port);
		System.out.println("*** Port 12345 is ready to be connected ***");
	}

    public void start() throws IOException {

        while(isOn.get()) {            
            try {
				Socket client = server.accept(); // blocking method waiting for a client connection
				System.out.println("New connection established with client at IP " 
				+ client.getInetAddress().getHostAddress() + " and port " + client.getPort()); // gets the client's IP and port number
				
				PrintStream clientOutput = new PrintStream(client.getOutputStream()); // adds the customer's output to the list
				this.clientsOutputList.add(clientOutput);
				
				ClientSharingTask messageSharing =  new ClientSharingTask(client.getInputStream(), this);
				new Thread(messageSharing).start();  // starts a separate thread for treating one client's connection
				
				ServerSharingTask serverMessaging = new ServerSharingTask(client.getInputStream(), this);
				new Thread(serverMessaging).start();
			} catch (SocketException e) {
				System.out.println("Is the socket running? " + this.isOn.get());
			}
        }
    }

    public void quit() throws IOException {
    	this.isOn.set(false);
    	this.server.close();
    }    
    
    public void shareMessageWithAll(String msg) { // to all clients (server not included)
        for (PrintStream console : clientsOutputList) {
        	console.println(msg);
        }
    }

}