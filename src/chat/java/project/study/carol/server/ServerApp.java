package chat.java.project.study.carol.server;

import java.io.IOException;

public class ServerApp {

	public static void main(String[] args) throws IOException {
		Server server = new Server(12345);
		server.start();
//		server.quit();
	}

}
