package model.cloudConnector;

import java.io.File;

import model.container.FileToKeyTableElement;

public interface CloudConnector {
	public FileToKeyTableElement upload(FileToKeyTableElement input);

	public String download(File inputFile);

	public void listDir(String path);
}
