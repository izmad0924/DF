package kr.sys4u.directorytransfer.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;

public class DirectoryTransferServer implements Closeable {

	private int port;
	private ServerSocket serverSocket;
	private boolean initialized = false;

	public DirectoryTransferServer() {
		this(1231);
	}

	public DirectoryTransferServer(int port) {
		this.port = port;
	}

	private void initialize() throws IOException {
		if (initialized) {
			return;
		}
		serverSocket = new ServerSocket(port);

		initialized = true;
	}

	private void execute() throws IOException {
		if (!initialized) {
			initialize();
		}

		while (true) {
			new FileServerProcessor(serverSocket.accept()).process();
		}
	}

	@Override
	public void close() {
		if (!initialized) {
			return;
		}

		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		try (DirectoryTransferServer server = new DirectoryTransferServer();) {
			server.initialize();
			server.execute();
		} catch (Exception e) {
			//
		}
	}
}