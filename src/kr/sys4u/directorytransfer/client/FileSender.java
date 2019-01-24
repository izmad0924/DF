package kr.sys4u.directorytransfer.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import exception.FileTransferException;

public class FileSender {
	Path requested;

	public FileSender(String requested) {
		this.requested = Paths.get(requested);
	}

	public void sendFile(BufferedOutputStream dos) {
		try (BufferedInputStream dis = new BufferedInputStream(new FileInputStream(requested.toFile()))) {
			System.out.println(requested + "를 요청받음");

			byte[] buf = new byte[8192];
			int cnt = 0;
			for (int i = cnt; i < requested.toFile().length(); i += cnt) {
				cnt = dis.read(buf);
				dos.write(buf, 0, cnt);
				dos.flush();
			}
		} catch (IOException e) {
			throw new FileTransferException(e);
		}
	}

}
