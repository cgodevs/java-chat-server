package chat.java.project.study.carol.client;

import java.io.PrintStream;
import java.util.Scanner;

public class PrintClientMessagesTask implements Runnable {

	private Client client;
	
	public PrintClientMessagesTask(Client client) {
		this.client = client;
	}

	@Override
	public void run() {
		while (client.keyboard.hasNextLine()) {
			client.clientPrinting.println(String.format("%s says: %s", client.clientName, client.keyboard.nextLine()));
    	}
    }
}


