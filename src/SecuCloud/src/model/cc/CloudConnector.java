package model.cc;

import model.InformationContainer;

public interface CloudConnector {

	public InformationContainer upload(InformationContainer input);

	public boolean download(InformationContainer informationContainer);

	public void listDir(String path);
	
	public void remove(String path);
}
