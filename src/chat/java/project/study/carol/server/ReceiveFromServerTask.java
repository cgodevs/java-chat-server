package chat.java.project.study.carol.server;

import java.io.InputStream;
import java.util.Scanner;

public class ReceiveFromServerTask implements Runnable {

    private InputStream serverStream;
    public ReceiveFromServerTask(InputStream server) {
        this.serverStream = server;
    }

    // Receive messages from the Server and prints them to the screen
    public void run() {
        Scanner s = new Scanner(this.serverStream);
        while (s.hasNextLine()) {
            System.out.println(s.nextLine());
        }
    }
}