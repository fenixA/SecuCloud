package model.cc;

import control.util.Pair;
import model.InformationContainer;

public class CloudConnectThreader implements Runnable {
	public enum command{
		upload,
		download,
		copy,
		listDir
	}
	
	public enum identifyer {
		InformationContainer
	}

	private InformationContainer informationContainer;
	private CloudConnectorGoogleGsutilTEMP cloudConnectorGSUTIL;
	private command cmd;
	private Pair<Integer, Object> returnValue;

	public CloudConnectThreader(command cmd,
			InformationContainer informationContainer) {
		this.cloudConnectorGSUTIL = new CloudConnectorGoogleGsutilTEMP();
		this.informationContainer = informationContainer;
		this.cmd = cmd;
	}

	public Pair<Integer, Object> getReturnValue() {
		return returnValue;
	}

	@Override
	public void run() {
		switch (cmd) {
		case download:
			break;
		case listDir:
			break;
		case upload:
			this.cloudConnectorGSUTIL.upload(informationContainer);
		case copy:
			break;
		default:
			break;
		}
	}
}
