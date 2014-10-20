package control;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.NoSuchPaddingException;

import control.util.CryptToolbox;
import control.util.SupportFunctions;
import model.InformationContainer;
import model.cc.CloudConnectorGoogleGsutil;

/**
 * The Class ThreaderInstanceCreator separates costly functions to different
 * threads to avoid waiting times for the user.
 */
public class ThreadInstanceCreator implements Runnable {

	/**
	 * The Enum command contains the different commands with which a thread can
	 * be started.
	 */
	public static enum command {

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
	private CloudConnectorGoogleGsutil cloudConnectorGoogleGsutil;

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
		this.cloudConnectorGoogleGsutil = new CloudConnectorGoogleGsutil();
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
				File tempFile = CryptToolbox.createTempHashedFile(selectedFile);
				CryptToolbox.threadEncryptFileAesCTR(tempFile,
						informationContainer.getLocalEncryptedLocation(),
						informationContainer.getKey());
				tempFile.delete();
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| NoSuchProviderException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IOException e) {
				e.printStackTrace();
			}
			this.cloudConnectorGoogleGsutil.upload(informationContainer);
			new File(informationContainer.getLocalEncryptedLocation()).delete();
			break;
		case downloadDecryptFile:
			this.cloudConnectorGoogleGsutil.download(informationContainer);
			try {
				File tempFile = new File(Main.getInstance().getUSER_TEMP_DIR()
						+ File.separator
						+ informationContainer.getEncryptedName()
						+ Main.DOWNLOAD_EXTENSION);
				File tempFile2 = CryptToolbox.threadDecryptFileAesCTR(tempFile,
						Main.getInstance().getUSER_TEMP_DIR() + File.separator
								+ informationContainer.getName(),
						informationContainer.getKey());
				tempFile.delete();
				if (CryptToolbox.verifyFileHashSHA256(tempFile2)) {
					SupportFunctions.copyFile(tempFile2, Main.getInstance()
							.getUSER_DOWNLOAD_DIR() + File.separator + tempFile2.getName(),
							StandardCopyOption.REPLACE_EXISTING);
					tempFile2.delete();
				}

			} catch (InvalidKeyException | NoSuchAlgorithmException
					| NoSuchProviderException | NoSuchPaddingException
					| InvalidAlgorithmParameterException | IOException e) {
				e.printStackTrace();
			}
			break;
		case removeFile:
			this.cloudConnectorGoogleGsutil.remove(informationContainer);
			break;
		}
	}
}
