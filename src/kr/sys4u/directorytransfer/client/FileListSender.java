package kr.sys4u.directorytransfer.client;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class FileListSender {
	List<File> fileList;

	public FileListSender(List<File> fileList) {
		this.fileList = fileList;
	}

	public void sendFileList(ObjectOutputStream oos) {
		try {
			oos.writeObject(fileList); // List전송에 오류가 생길경우 ArrayList로 캐스팅
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
