package chat.java.project.study.carol.server;

import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class MessageSharingTask implements Runnable{

	private InputStream client;
    private Server servidor;

    public MessageSharingTask(InputStream client, Server servidor) {
        this.client = client;
        this.servidor = servidor;
    }

    public void run() {        
        Scanner s = new Scanner(this.client);
        while (s.hasNextLine()) {
            servidor.shareMessageToAll(s.nextLine());
        }
        s.close();
    }
}
