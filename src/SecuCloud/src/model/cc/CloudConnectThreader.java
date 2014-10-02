package model.cc;

import model.InformationContainer;

public class CloudConnectThreader implements Runnable {
	public enum command{
		upload,
		download,
		copy,
		listDir,
		remove
	}
	
	public enum identifyer {
		InformationContainer
	}

	private InformationContainer informationContainer;
	private CloudConnectorGoogleGsutilTEMP cloudConnectorGSUTIL;
	private command cmd;

	public CloudConnectThreader(command cmd,
			InformationContainer informationContainer) {
		this.cloudConnectorGSUTIL = new CloudConnectorGoogleGsutilTEMP();
		this.informationContainer = informationContainer;
		this.cmd = cmd;
	}

	@Override
	public void run() {
		switch (cmd) {
		case download:
			this.cloudConnectorGSUTIL.download(informationContainer);
		case listDir:
			break;
		case upload:
			this.cloudConnectorGSUTIL.upload(informationContainer);
		case copy:
			break;
		case remove:
			this.cloudConnectorGSUTIL.remove(informationContainer);
		}
	}
}
