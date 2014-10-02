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
import model.cc.CloudConnectorGoogleGsutilTEMP;

public class ThreaderInstanceCreator implements Runnable {
	public enum command {
		encryptUploadFile, downloadDecryptFile, removeFile
	}

	private InformationContainer informationContainer;
	private command cmd;
	private CloudConnectorGoogleGsutilTEMP cloudConnectorGoogleGsutilTEMP;

	public ThreaderInstanceCreator(command cmd, InformationContainer informationContainer) {
		this.informationContainer = informationContainer;
		this.cmd = cmd;
		this.cloudConnectorGoogleGsutilTEMP = new CloudConnectorGoogleGsutilTEMP();
	}

	@Override
	public void run() {
		switch (cmd) {
		case encryptUploadFile:
			File selectedFile = new File(
					informationContainer.getLocalPLainLocation());
			try {
				CryptToolbox.threadEncryptFileAesCTR(selectedFile,
						informationContainer.getLocalEncryptedLocation(),
						informationContainer.getKey());
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| NoSuchProviderException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IOException e) {
				e.printStackTrace();
			}
			this.cloudConnectorGoogleGsutilTEMP.upload(informationContainer);
			File encryptedFile = new File(informationContainer.getLocalEncryptedLocation());
			encryptedFile.delete();
			break;
		case downloadDecryptFile:
			this.cloudConnectorGoogleGsutilTEMP.download(informationContainer);
			try {
				File f = new File(Main.getInstance().getUSER_TEMP_DIR() + "/"
						+ informationContainer.getEncryptedName() + Main.DOWNLOAD_EXTENSION);
				CryptToolbox.threadDecryptFileAesCTR(f,
						Main.getInstance().getUSER_DOWNLOAD_DIR() + "/"
								+ informationContainer.getName(),
						informationContainer.getKey());
				f.delete();
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| NoSuchProviderException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case removeFile:
			this.cloudConnectorGoogleGsutilTEMP.remove(informationContainer);
			break;
		}
	}
}
