package kr.sys4u.directorytransfer.server;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class FileGenerator {
	private List<File> directoryAndFiles;
	private Socket clientSocket;

	public FileGenerator(List<File> directoryAndFiles, Socket clientSocket) {
		this.directoryAndFiles = directoryAndFiles;
		this.clientSocket = clientSocket;
	}

	public void generate() {
		directoryAndFiles.stream().filter(file -> file.isFile()).forEach(file -> {
			String destPath = FileServerProcessor.DEST_PATH.resolve(FileServerProcessor.SRC_PATH.relativize(file.toPath())).toString();
			
			System.out.println("destPath : " + destPath);
			try (BufferedOutputStream dos = new BufferedOutputStream(new FileOutputStream(destPath));) {
				DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
				DataOutputStream socketDos = new DataOutputStream(clientSocket.getOutputStream());

				socketDos.writeUTF(file.toString());
				socketDos.flush();

				byte[] buf = new byte[8192];
				int cnt = 0;
				for (int i = cnt; i < file.length(); i += cnt) {
					cnt = dis.read(buf);
					dos.write(buf, 0, cnt);
					dos.flush();
				}
			} catch (IOException e) {

			}
		});
	}

}
