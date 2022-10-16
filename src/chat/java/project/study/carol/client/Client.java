package chat.java.project.study.carol.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import chat.java.project.study.carol.server.GetFromServerTask;

public class Client {
	private Socket client;
    private String clientName;
    private Scanner keyboard = new Scanner(System.in);
    private PrintStream output;

    public Client (String host, int port) throws UnknownHostException, IOException {
        this.client = new Socket(host, port);  
        System.out.println("The client was successfully connected to the server!");
        output = new PrintStream(client.getOutputStream());
    }

    public void startCommunication() throws IOException {
    	this.clientName = getName(keyboard); 
        
        keepPrintingFromClient(keyboard, output, clientName);       

        output.close();
        keyboard.close();
        client.close();        
    }

    public static String getName(Scanner sc) {
    	System.out.println("What's your name?");
    	if (sc.hasNextLine())
    		return sc.nextLine();    		
    	else 
    		return getName(sc);
    }
    
    public static void keepPrintingFromClient(Scanner keyboard, PrintStream clientOutput, String clientName) {
    	while (keyboard.hasNextLine()) {
    		clientOutput.println(String.format("%s says: %s", clientName, keyboard.nextLine()));
    	}
    }
}

