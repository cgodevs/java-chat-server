package chat.java.project.study.carol.server;

import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientSharingTask implements Runnable{

	private InputStream client;
    private Server server;

    public ClientSharingTask(InputStream client, Server server) {
        this.client = client;
        this.server = server;
    }

    public void run() {        
        Scanner s = new Scanner(this.client);
        while (s.hasNextLine()) {
            server.shareMessageWithAll(s.nextLine());
        }
        s.close();
    }
}
