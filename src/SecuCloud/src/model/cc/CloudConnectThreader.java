package model.cc;

import util.Pair;
import model.InformationContainer;
import model.cc.CloudConnector.command;

public class CloudConnectThreader implements Runnable {
	public enum identifyer {
		InformationContainer
	}

	private InformationContainer informationContainer;
	private CloudConnectorGoogleGsutilTEMP cloudConnectorGSUTIL;
	private CloudConnector.command cmd;
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
		case ls:
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
