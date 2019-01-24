package kr.sys4u.directorytransfer.server;

import java.io.File;
import java.util.List;

public class DirectoryGenerator {
	private List<File> directoryAndFiles;
	private int onlyFileCnt = 0;


	public int getOnlyFileCnt() {
		return onlyFileCnt;
	}

	public DirectoryGenerator(List<File> directoryAndFiles) {
		this.directoryAndFiles = directoryAndFiles;
	}

	public void generate() {
		directoryAndFiles.stream().forEach(file -> {
			if (file.isFile()) {
				onlyFileCnt++;
				return;
			}
			String destDir = FileServerProcessor.DEST_PATH.resolve(FileServerProcessor.SRC_PATH.relativize(file.toPath())).toString();
			new File(destDir).mkdirs();
		});
	}

}
