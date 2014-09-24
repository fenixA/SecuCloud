package model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import util.CryptToolbox;
import control.FileListHandler;
import control.Main;

public class InformationContainerStorer {
	private static final int ATTRIBUTE_LEN = 256;
	private static final int ATTRIBUTES = 8;
	private static final int ARRAY_LEN = ATTRIBUTE_LEN * ATTRIBUTES;
	private static final String STATE_FILE = "state.cfg";
	private byte[] expandedPassword;

	public InformationContainerStorer(String userPassword) {
		this.expandedPassword = CryptToolbox.expandUserPassword(userPassword,
				Main.AES_KEY_LEN);
	}

	public boolean storeFileList() throws IOException {
		Vector<InformationContainer> fileList = FileListHandler.getInstance()
				.getFileList();
		for (InformationContainer informationContainer : fileList) {
			if (!storeInformationContainer(informationContainer)) {
				return false;
			}
		}
		return true;
	}

	private boolean storeInformationContainer(
			InformationContainer informationContainer) throws IOException {
		byte[] output;
		if (informationContainer == null) {
			return false;
		} else {
			output = encryptByteArray(createByteArrayFromInformationContainer(informationContainer));
			FileOutputStream stream = new FileOutputStream(Main.getInstance().getUSER_DATA_DIR() + "/" + STATE_FILE, true);
			stream.write(output);
			stream.close();
			return true;
		}
	}

	private byte[] encryptByteArray(byte[] input) {
		byte[] output;
		//TODO
		return new byte[2];
	}

	private byte[] createByteArrayFromInformationContainer(
			InformationContainer informationContainer) {
		int ctr = 0;
		byte[] plain = new byte[ARRAY_LEN];
		Arrays.fill(plain, (byte) 0);

		String tempString = informationContainer.getLocalPLainLocation();
		System.arraycopy(tempString.getBytes(), 0, plain, ctr * ATTRIBUTE_LEN,
				tempString.length());
		ctr++;

		tempString = informationContainer.getName();
		System.arraycopy(tempString.getBytes(), 0, plain, ctr * ATTRIBUTE_LEN,
				tempString.length());
		ctr++;

		tempString = informationContainer.getEncryptedName();
		System.arraycopy(tempString.getBytes(), 0, plain, ctr * ATTRIBUTE_LEN,
				tempString.length());
		ctr++;

		tempString = informationContainer.getLocalEncryptedLocation();
		System.arraycopy(tempString.getBytes(), 0, plain, ctr * ATTRIBUTE_LEN,
				tempString.length());
		ctr++;

		tempString = informationContainer.getCloudLocation();
		System.arraycopy(tempString.getBytes(), 0, plain, ctr * ATTRIBUTE_LEN,
				tempString.length());
		ctr++;

		tempString = informationContainer.getTimestamp();
		System.arraycopy(tempString.getBytes(), 0, plain, ctr * ATTRIBUTE_LEN,
				tempString.length());
		ctr++;
		System.arraycopy(informationContainer.getKey(), 0, plain, ctr
				* ATTRIBUTE_LEN, informationContainer.getKey().length);
		ctr++;

		byte[] tempByte = informationContainer.getEncryption().getByteArray();
		System.arraycopy(tempByte, 0, plain, ctr * ATTRIBUTE_LEN,
				tempByte.length);
		ctr++;

		return plain;
	}
}
