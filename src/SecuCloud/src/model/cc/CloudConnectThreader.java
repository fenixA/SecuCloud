package model.cc;

import model.InformationContainer;
import model.cc.CloudConnector.command;

public class CloudConnectThreader implements Runnable {
	public class ReturnValue<X, Y> {
		public final X x;
		public final Y y;

		public ReturnValue(X x, Y y) {
			this.x = x;
			this.y = y;
		}
	}

	public enum identifyer {
		InformationContainer
	}

	private InformationContainer informationContainer;
	private CloudConnectorGoogleGsutilTEMP cloudConnectorGSUTIL;
	private CloudConnector.command cmd;
	private ReturnValue<Integer, Object> returnValue;

	public CloudConnectThreader(command cmd,
			InformationContainer informationContainer) {
		this.cloudConnectorGSUTIL = new CloudConnectorGoogleGsutilTEMP();
		this.informationContainer = informationContainer;
		this.cmd = cmd;
	}

	public ReturnValue<Integer, Object> getReturnValue() {
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
