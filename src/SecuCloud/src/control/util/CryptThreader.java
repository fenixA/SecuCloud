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
	private Thread t;

	public CryptThreader(command cmd, InformationContainer informationContainer) {
		this.informationContainer = informationContainer;
		this.cmd = cmd;
	}

	@Override
	public void run() {
		switch (cmd) {
		case encryptFile:
			File selectedFile = new File(
					informationContainer.getLocalPLainLocation());
			try {
				CryptToolbox.threadEncryptFileAesCTR(selectedFile,
						informationContainer.getLocalEncryptedLocation(),
						informationContainer.getKey());
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| NoSuchProviderException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.t = new Thread(new CloudConnectThreader(
					CloudConnectThreader.command.upload, informationContainer));
			this.t.start();
			Main.getInstance().threadVector.add(this.t);
		case decryptFile:
			this.t = new Thread(
					new CloudConnectThreader(
							CloudConnectThreader.command.download,
							informationContainer));
			this.t.start();
			try {
				this.t.join();
				File f = new File(Main.getInstance().getUSER_TEMP_DIR() + "/"
						+ informationContainer.getName());
				CryptToolbox.threadDecryptFileAesCTR(f,
						Main.getInstance().getUSER_DOWNLOAD_DIR() + "/"
								+ informationContainer.getName(),
						informationContainer.getKey());
				f.delete();
			} catch (InterruptedException | InvalidKeyException
					| NoSuchAlgorithmException | NoSuchProviderException
					| NoSuchPaddingException
					| InvalidAlgorithmParameterException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
