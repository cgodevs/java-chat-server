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
    protected PrintStream clientPrinting;

    public PrintStream getClientPrinting() {
		return clientPrinting;
	}

	public Client (String host, int port) throws UnknownHostException, IOException {
        this.clientSocket = new Socket(host, port);  
        clientPrinting = new PrintStream(clientSocket.getOutputStream());
        System.out.println("The client was successfully connected to the server!");
    }

    public void startCommunication() throws IOException, InterruptedException {
    	this.clientName = getName(keyboard); 
    	
    	// THREAD 0: Gets messages from other Clients through the server
        ReceiveFromServerTask getServerMessages = new ReceiveFromServerTask(clientSocket.getInputStream());
        Thread serverFeed = new Thread(getServerMessages);
        serverFeed.start();
        
        // THREAD 1: Sends this client's messages to other clients
        Thread shareWithOthers = new Thread(new PrintClientMessagesTask(this));
        shareWithOthers.start();        
        shareWithOthers.join();
        
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

