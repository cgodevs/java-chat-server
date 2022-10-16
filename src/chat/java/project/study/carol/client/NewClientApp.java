package chat.java.project.study.carol.client;

import java.io.IOException;

public class NewClientApp {

	public static void main(String[] args) throws IOException {
        new Client("127.0.0.1", 12345).startCommunication();
	}

}
