package kr.sys4u.directorytransfer.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileServerProcessor {
	protected static Path SRC_PATH;
	protected static Path DEST_PATH = Paths.get("C:/temp/testcopy");
	private Socket socket;

	public FileServerProcessor(Socket socket) {
		this.socket = socket;
	}

	public void process() {
		try (DataInputStream dis = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());) {

			SRC_PATH = Paths.get(dis.readUTF());
			
			@SuppressWarnings("unchecked")
			List<File> directoryAndFiles = (List<File>) new ObjectInputStream(dis).readObject();

			DirectoryGenerator dg = new DirectoryGenerator(directoryAndFiles);
			dg.generate();
			dos.writeInt(dg.getOnlyFileCnt());

			new FileGenerator(directoryAndFiles, socket).generate();

		} catch (Exception e) {
			// ignored
		}

	}
}
