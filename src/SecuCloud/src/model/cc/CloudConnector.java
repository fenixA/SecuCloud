package model.cc;

import java.util.Vector;

import model.InformationContainer;

public interface CloudConnector {

	public InformationContainer upload(InformationContainer informationContainer);

	public boolean download(InformationContainer informationContainer);

	public Vector<String> listDir();
	
	public boolean remove(InformationContainer informationContainer);
}
