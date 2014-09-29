package control.util;

import model.InformationContainer;

public class CryptThreader implements Runnable {
	public enum command {
		encryptFile, decryptFile
	}

	public enum identifyer {
		InformationContainer
	}

	private InformationContainer informationContainer;
	private command cmd;

	public CryptThreader(command cmd, InformationContainer informationContainer) {
		this.informationContainer = informationContainer;
		this.cmd = cmd;
	}

	@Override
	public void run() {
		switch (cmd) {
		case encryptFile:
			break;
		case decryptFile:
			break;
		}
	}
}
