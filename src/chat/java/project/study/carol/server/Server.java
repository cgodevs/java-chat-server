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
	private List<PrintStream> allClientsPrintStreams;
	private AtomicBoolean isOn;
	ServerSocket server;
	
	public Server (int port) throws IOException {
		this.port = port;
		this.allClientsPrintStreams = new ArrayList<PrintStream>();
		this.isOn = new AtomicBoolean(true);
		this.server = new ServerSocket(this.port);
		System.out.println("*** Port 12345 is ready to be connected ***");
	}

    public void start() throws IOException {

        while(isOn.get()) {            
            try {  // for each client's connection
				Socket clientConnection = server.accept(); // blocking method waiting for a client connection

				PrintStream clientPrintStream = new PrintStream(clientConnection.getOutputStream()); // gets hold of printing to the client's console 
				this.allClientsPrintStreams.add(clientPrintStream);
				
				ClientSharingTask shareWithOthers =  new ClientSharingTask(clientConnection.getInputStream(), this);
				new Thread(shareWithOthers).start();  

				ServerSharingTask getMessagesFromServer = new ServerSharingTask(clientConnection.getInputStream(), this);
				new Thread(getMessagesFromServer).start();

				System.out.printf("New connection established with client at port %d\n", clientConnection.getPort()); // client's acceptance port number
				
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
        for (PrintStream console : allClientsPrintStreams) {
        	console.println(msg);
        }
    }

}