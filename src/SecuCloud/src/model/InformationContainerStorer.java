package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.Vector;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

import util.CryptToolbox;
import control.FileListHandler;
import control.Main;

public class InformationContainerStorer {
	private static final int ATTRIBUTE_LEN = 256;
	private static final int ATTRIBUTES = 8;
	private static final int ARRAY_LEN = ATTRIBUTE_LEN * ATTRIBUTES;
 	private static final String STATE_FILE = "state.cfg";
	private byte[] expandedPassword;
	
	public static final int ENC_ARRAY_LEN = ARRAY_LEN + Main.AES_BLOCK_SIZE; //plus IV

	public InformationContainerStorer(String userPassword) {
		System.out.println("InformationContainerStorer.InformationContainerStorer()");
		this.expandedPassword = CryptToolbox.expandUserPassword(userPassword,
				Main.AES_KEY_LEN);
	}

	public boolean storeFileList() throws IOException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		Vector<InformationContainer> fileList = FileListHandler.getInstance()
				.getFileList();
		System.out.println("InformationContainerStorer.storeFileList");
		File outFile = new File(Main.getInstance()
				.getUSER_DATA_DIR() + "/" + STATE_FILE);
		if (outFile.exists()){
			outFile.delete();
		}
		outFile.createNewFile();
		for (InformationContainer informationContainer : fileList) {
			if (!storeInformationContainer(informationContainer, outFile)) {
				return false;
			}
		}
		return true;
	}

	private boolean storeInformationContainer(
			InformationContainer informationContainer, File outFile) throws IOException,
			InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException,
			ShortBufferException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException {
		System.out.println("InformationContainerStorer.storeInformationContainer");
		
		byte[] output;
		if (informationContainer == null) {
			return false;
		} else {
			output = CryptToolbox
					.encryptByteArrayAesCTR(
							createByteArrayFromInformationContainer(informationContainer),
							expandedPassword);
			FileOutputStream stream = new FileOutputStream(outFile, true);
			stream.write(output);
			stream.close();
			return true;
		}
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
