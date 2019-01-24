package kr.sys4u.directorytransfer.client;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import exception.CopyException;

public class LeafFileCollector {
	private boolean initialized = false;
	private List<File> fileList;
	private Path parentPath;

	public LeafFileCollector(String rootPath) {
		this.parentPath = Paths.get(rootPath);
		this.fileList = new ArrayList<>();
	}

	public List<File> getFileList() {
		if (!initialized) {
			walk();
		}
		return fileList;
	}

	private void walk() {
		try {
			Files.walkFileTree(parentPath, new SimpleFileVisitor<Path>() {
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					if (dir.toFile().length() == 0) {
						fileList.add(dir.toFile());
					}
					return FileVisitResult.CONTINUE;
				}

				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					fileList.add(file.toFile());
					return FileVisitResult.CONTINUE;
				}

				public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
					throw exc;
				}
			});
		} catch (IOException e) {
			throw new CopyException(e);
		}
	}

}
