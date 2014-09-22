package model.cc;

import java.io.File;

import model.InformationContainer;

public interface CloudConnector {
	public enum command{
		upload,
		download,
		copy,
		ls
	}
	public InformationContainer upload(InformationContainer input);

	public String download(File inputFile);

	public void listDir(String path);
}
