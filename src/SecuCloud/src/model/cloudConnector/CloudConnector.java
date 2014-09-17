package model.cloudConnector;

import java.io.File;

import model.container.SecuCloudContainer;

public interface CloudConnector {
	public SecuCloudContainer upload(SecuCloudContainer input);

	public String download(File inputFile);

	public void listDir(String path);
}
