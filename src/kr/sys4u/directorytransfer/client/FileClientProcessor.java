package kr.sys4u.directorytransfer.client;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class FileClientProcessor {
	private Socket socket;

	public FileClientProcessor(Socket clientSocket) {
		this.socket = clientSocket;
	}

	public void process() {
		String rootPath = "C:/Temp/Test";

		LeafFileCollector collector = new LeafFileCollector(rootPath);
		List<File> fileList = collector.getFileList();

		try (DataInputStream dis = new DataInputStream(socket.getInputStream());
				BufferedOutputStream dos = new BufferedOutputStream(socket.getOutputStream());) {
			
			new DataOutputStream(dos).writeUTF(rootPath);;
			
			FileListSender fileListSender = new FileListSender(fileList);
			fileListSender.sendFileList(new ObjectOutputStream(dos));

			int fileCount = dis.readInt();
			System.out.println("fileCount : " + fileCount);

			for (int i = 0; i < fileCount; i++) {
				String file = dis.readUTF();

				FileSender fileSender = new FileSender(file);
				fileSender.sendFile(dos);
			}

		} catch (IOException e) {
			// ignored
		}
	}
}
