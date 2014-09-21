package model.cloudConnector;

import java.io.File;

import model.InformationContainer;

public interface CloudConnector {
	public InformationContainer upload(InformationContainer input);

	public String download(File inputFile);

	public void listDir(String path);
}
