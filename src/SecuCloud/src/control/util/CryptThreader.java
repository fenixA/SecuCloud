package control.util;


import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.NoSuchPaddingException;

import control.Main;
import model.InformationContainer;
import model.cc.CloudConnectThreader;

public class CryptThreader implements Runnable {
	public enum command {
		encryptFile, decryptFile
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
			File selectedFile = new File(informationContainer.getLocalPLainLocation());
			try {
				CryptToolbox.threadEncryptFileAesCTR(selectedFile,
						informationContainer.getLocalEncryptedLocation(), informationContainer.getKey());
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| NoSuchProviderException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Thread t = new Thread(new CloudConnectThreader(CloudConnectThreader.command.upload, informationContainer));
			t.start();
			Main.getInstance().threadVector.add(t);
		case decryptFile:
			break;
		}
	}
}
