package model.cc;

import model.InformationContainer;

public interface CloudConnector {

	public InformationContainer upload(InformationContainer informationContainer);

	public boolean download(InformationContainer informationContainer);

	public void listDir(String path);
	
	public boolean remove(InformationContainer informationContainer);
}
