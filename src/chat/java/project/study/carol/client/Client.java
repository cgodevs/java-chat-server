package chat.java.project.study.carol.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import chat.java.project.study.carol.server.ReceiveFromServerTask;

public class Client {
	private Socket clientSocket;
    protected String clientName;
    protected Scanner keyboard = new Scanner(System.in);
    private OutputStream fromClient;
    private InputStream toClient;
    protected PrintStream clientPrinting;

    public PrintStream getClientPrinting() {
		return clientPrinting;
	}

	public Client (String host, int port) throws UnknownHostException, IOException {
        this.clientSocket = new Socket(host, port);  
        System.out.println("The client was successfully connected to the server!");
        
        this.fromClient = clientSocket.getOutputStream();
        this.toClient = clientSocket.getInputStream();
        clientPrinting = new PrintStream(fromClient);
    }

    public void startCommunication() throws IOException, InterruptedException {
    	this.clientName = getName(keyboard); 
    	
    	// THREAD 0: Gets messages from other Clients through the server
        ReceiveFromServerTask getServerMessages = new ReceiveFromServerTask(toClient);
        Thread fromServer = new Thread(getServerMessages);
        fromServer.start();
        
        // THREAD 1: Sends this client's messages to other clients
        PrintClientMessagesTask printClientMessages = new PrintClientMessagesTask(this);
        Thread toClients = new Thread(printClientMessages);
        toClients.start();        
        toClients.join();
        
        clientPrinting.close();
        keyboard.close();
        clientSocket.close();        
    }

    public static String getName(Scanner sc) {
    	System.out.println("What's your name?");
    	if (sc.hasNextLine())
    		return sc.nextLine();    		
    	else 
    		return getName(sc);
    }
    
}

