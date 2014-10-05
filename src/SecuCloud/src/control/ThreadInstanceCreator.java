package control;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.NoSuchPaddingException;

import control.util.CryptToolbox;
import model.InformationContainer;
import model.cc.CloudConnectorGoogleGsutilTEMP;

/**
 * The Class ThreaderInstanceCreator separates costly functions to different
 * threads to avoid waiting times for the user.
 */
public class ThreadInstanceCreator implements Runnable {

	/**
	 * The Enum command contains the different commands with which a thread can
	 * be started.
	 */
	public enum command {

		/** The encrypt and upload file. */
		encryptUploadFile,
		/** The download and decrypt file. */
		downloadDecryptFile,
		/** The remove file. */
		removeFile
	}

	/** The given information container. */
	private InformationContainer informationContainer;

	/** The given command. */
	private command cmd;

	/** The cloud connector. */
	private CloudConnectorGoogleGsutilTEMP cloudConnectorGoogleGsutilTEMP;

	/**
	 * Instantiates a new threader instance.
	 * 
	 * @param cmd
	 *            the command
	 * @param informationContainer
	 *            the information container
	 */
	public ThreadInstanceCreator(command cmd,
			InformationContainer informationContainer) {
		this.informationContainer = informationContainer;
		this.cmd = cmd;
		this.cloudConnectorGoogleGsutilTEMP = new CloudConnectorGoogleGsutilTEMP();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		switch (cmd) {
		case encryptUploadFile:
			File selectedFile = new File(
					informationContainer.getLocalPlainLocation());
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
			File encryptedFile = new File(
					informationContainer.getLocalEncryptedLocation());
			encryptedFile.delete();
			break;
		case downloadDecryptFile:
			this.cloudConnectorGoogleGsutilTEMP.download(informationContainer);
			try {
				File f = new File(Main.getInstance().getUSER_TEMP_DIR() + "/"
						+ informationContainer.getEncryptedName()
						+ Main.DOWNLOAD_EXTENSION);
				CryptToolbox.threadDecryptFileAesCTR(f,
						Main.getInstance().getUSER_DOWNLOAD_DIR() + "/"
								+ informationContainer.getName(),
						informationContainer.getKey());
				f.delete();
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| NoSuchProviderException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IOException e) {
				e.printStackTrace();
			}
			break;
		case removeFile:
			this.cloudConnectorGoogleGsutilTEMP.remove(informationContainer);
			break;
		}
	}
}
