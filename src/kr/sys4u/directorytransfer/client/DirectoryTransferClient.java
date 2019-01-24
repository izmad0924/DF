package kr.sys4u.directorytransfer.client;

import java.io.IOException;
import java.net.Socket;

public class DirectoryTransferClient {
	private String ip;
	private int port;
	private Socket clientSocket;
	private boolean initialized = false;
	
	public DirectoryTransferClient() {
		this("127.0.0.1", 1231);
	}
	
	public DirectoryTransferClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	private void initialize() throws IOException {
		if(initialized) {
			return;
		}
		clientSocket = new Socket(ip, port);
		initialized = true;
	}
	
	private void execute() {
		new FileClientProcessor(clientSocket).process();
	}

	public static void main(String[] args) {
		DirectoryTransferClient dtc = new DirectoryTransferClient();
		try {
			dtc.initialize();
			dtc.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
