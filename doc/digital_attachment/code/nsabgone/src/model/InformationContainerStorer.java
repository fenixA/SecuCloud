package model;

import java.io.File;
import java.io.FileInputStream;
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

import model.InformationContainer.Encryption;
import control.FileListHandler;
import control.Main;
import control.util.CryptToolbox;
import control.util.SupportFunctions;

/**
 * The Class InformationContainerStorer is able to convert
 * {@link InformationContainer} to byte arrays, save them encrypted in a file or
 * restore information from a file and create {@link InformationContainer} of
 * them.
 */
public class InformationContainerStorer {

	/**
	 * The Constant ARRAY_LEN defines the size of one
	 * {@link InformationContainer} needs in a byte array.
	 */
	private static final int ARRAY_LEN = InformationContainer.ATTRIBUTE_LEN
			* InformationContainer.ATTRIBUTES;

	/** The Constant STATE_FILE. */
	private static final String STATE_FILE = "state.cfg";

	/** The expanded password. */
	private byte[] expandedPassword;

	/** The Constant ENC_ARRAY_LEN. */
	public static final int ENC_ARRAY_LEN = ARRAY_LEN + Main.AES_BLOCK_SIZE; // plus
																				// IV

	/**
	 * Instantiates a new information container storer. Expands the password to
	 * make it usable for encryptin and saves it.
	 * 
	 * @param userPassword
	 *            the users password
	 */
	public InformationContainerStorer(String userPassword) {
		System.out
				.println("InformationContainerStorer.InformationContainerStorer()");
		this.expandedPassword = CryptToolbox.expandUserPassword(userPassword,
				Main.AES_KEY_LEN);
	}

	/**
	 * Loads a saved file list from the STATE_FILE by creating
	 * {@link InformationContainer} from it an add them to the file list of
	 * {@link FileListHandler}.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws NoSuchProviderException
	 *             the no such provider exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws ShortBufferException
	 *             the short buffer exception
	 * @throws IllegalBlockSizeException
	 *             the illegal block size exception
	 * @throws BadPaddingException
	 *             the bad padding exception
	 * @throws InvalidAlgorithmParameterException
	 *             the invalid algorithm parameter exception
	 */
	public void loadFileList() throws IOException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException,
			InvalidAlgorithmParameterException {
		System.out.println("InformationContainerStorer.loadFileList()");
		File inFile = new File(Main.getInstance().getUSER_DATA_DIR() + "/"
				+ STATE_FILE);
		if (!inFile.exists()) {
			return;
		}
		FileInputStream stream = new FileInputStream(inFile);
		byte[] inFileByte = new byte[(int) inFile.length()];
		int read = 0;
		int ctr = 0;
		while ((read = stream.read()) != -1) {
			inFileByte[ctr] = (byte) read;
			ctr++;
		}
		stream.close();
		byte[] temp = new byte[ENC_ARRAY_LEN];
		for (int i = 0; i < inFile.length() / ENC_ARRAY_LEN; i++) {
			System.arraycopy(inFileByte, i * ENC_ARRAY_LEN, temp, 0,
					ENC_ARRAY_LEN);
			FileListHandler.getInstance().addFile(
					loadInformationContainer(temp));
		}
	}

	/**
	 * Stores a file list of the {@link FileListHandler} encrypted by the users
	 * password to the STATE_FILE.
	 * 
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws NoSuchProviderException
	 *             the no such provider exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws ShortBufferException
	 *             the short buffer exception
	 * @throws IllegalBlockSizeException
	 *             the illegal block size exception
	 * @throws BadPaddingException
	 *             the bad padding exception
	 * @throws InvalidAlgorithmParameterException
	 *             the invalid algorithm parameter exception
	 */
	public boolean storeFileList() throws IOException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException,
			InvalidAlgorithmParameterException {
		System.out.println("InformationContainerStorer.storeFileList()");
		Vector<InformationContainer> fileList = FileListHandler.getInstance()
				.getFileList();
		File outFile = new File(Main.getInstance().getUSER_DATA_DIR() + "/"
				+ STATE_FILE);
		if (outFile.exists()) {
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

	/**
	 * Store given {@link InformationContainer} to given file by creating a byte
	 * array from it and encrypting it.
	 * 
	 * @param informationContainer
	 *            the {@link InformationContainer}
	 * @param outFile
	 *            the destination of encrypted information
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws NoSuchProviderException
	 *             the no such provider exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws ShortBufferException
	 *             the short buffer exception
	 * @throws IllegalBlockSizeException
	 *             the illegal block size exception
	 * @throws BadPaddingException
	 *             the bad padding exception
	 * @throws InvalidAlgorithmParameterException
	 *             the invalid algorithm parameter exception
	 */
	private boolean storeInformationContainer(
			InformationContainer informationContainer, File outFile)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException,
			ShortBufferException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException {
		System.out
				.println("InformationContainerStorer.storeInformationContainer()");
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

	/**
	 * Load information container from given byte array by decrypting and
	 * converting it to an {@link InformationContainer}.
	 * 
	 * @param input
	 *            the given byte array
	 * @return the created {@link InformationContainer}
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws NoSuchProviderException
	 *             the no such provider exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws ShortBufferException
	 *             the short buffer exception
	 * @throws IllegalBlockSizeException
	 *             the illegal block size exception
	 * @throws BadPaddingException
	 *             the bad padding exception
	 * @throws InvalidAlgorithmParameterException
	 *             the invalid algorithm parameter exception
	 */
	private InformationContainer loadInformationContainer(byte[] input)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException,
			ShortBufferException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException {
		System.out
				.println("InformationContainerStorer.loadInformationContainer()");
		return createInformationContainerFromByteArray(CryptToolbox
				.decryptByteArrayAesCTR(input, expandedPassword));
	}

	/**
	 * Creates the information container from byte array.
	 * 
	 * @param input
	 *            the input
	 * @return the created {@link InformationContainer}
	 */
	private InformationContainer createInformationContainerFromByteArray(
			byte[] input) {
		System.out
				.println("InformationContainerStorer.createInformationContainerFromByteArray()");
		String localPlainLocation = new String();
		String name = new String();
		String encryptedName = new String();
		String localEncryptedLocation = new String();
		String cloudLocation = new String();
		String time = new String();
		byte[] key = new byte[Main.AES_KEY_LEN];
		int encryption;

		int ctr = 0;
		byte[] temp = new byte[InformationContainer.ATTRIBUTE_LEN];
		System.arraycopy(input, InformationContainer.ATTRIBUTE_LEN * ctr, temp,
				0, InformationContainer.ATTRIBUTE_LEN);
		for (byte b : temp) {
			if (b != 0) {
				localPlainLocation += (char) b;
			}
		}
		ctr++;

		System.arraycopy(input, InformationContainer.ATTRIBUTE_LEN * ctr, temp,
				0, InformationContainer.ATTRIBUTE_LEN);
		for (byte b : temp) {
			if (b != 0) {
				name += (char) b;
			}
		}
		ctr++;

		System.arraycopy(input, InformationContainer.ATTRIBUTE_LEN * ctr, temp,
				0, InformationContainer.ATTRIBUTE_LEN);
		for (byte b : temp) {
			if (b != 0) {
				encryptedName += (char) b;
			}
		}
		ctr++;

		System.arraycopy(input, InformationContainer.ATTRIBUTE_LEN * ctr, temp,
				0, InformationContainer.ATTRIBUTE_LEN);
		for (byte b : temp) {
			if (b != 0) {
				localEncryptedLocation += (char) b;
			}
		}
		ctr++;

		System.arraycopy(input, InformationContainer.ATTRIBUTE_LEN * ctr, temp,
				0, InformationContainer.ATTRIBUTE_LEN);
		for (byte b : temp) {
			if (b != 0) {
				cloudLocation += (char) b;
			}
		}
		ctr++;

		System.arraycopy(input, InformationContainer.ATTRIBUTE_LEN * ctr, temp,
				0, InformationContainer.ATTRIBUTE_LEN);
		for (byte b : temp) {
			if (b != 0) {
				time += (char) b;
			}
		}
		ctr++;

		System.arraycopy(input, InformationContainer.ATTRIBUTE_LEN * ctr, key,
				0, Main.AES_KEY_LEN);
		ctr++;

		System.arraycopy(input, InformationContainer.ATTRIBUTE_LEN * ctr, temp,
				0, SupportFunctions.INT_BYTE_SIZE);
		encryption = SupportFunctions.byteArrayToInt(temp);
		ctr++;

		InformationContainer ic = new InformationContainer(localPlainLocation,
				localEncryptedLocation, encryptedName, name, key,
				Encryption.values()[encryption]);
		ic.setTimestamp(time);
		ic.setCloudLocation(cloudLocation);
		return ic;
	}

	/**
	 * Creates the byte array from information container.
	 * 
	 * @param informationContainer
	 *            the given {@link InformationContainer}
	 * @return the created byte[]
	 */
	private byte[] createByteArrayFromInformationContainer(
			InformationContainer informationContainer) {
		int ctr = 0;
		byte[] plain = new byte[ARRAY_LEN];
		Arrays.fill(plain, (byte) 0);

		String tempString = informationContainer.getLocalPlainLocation();
		System.arraycopy(tempString.getBytes(), 0, plain, ctr
				* InformationContainer.ATTRIBUTE_LEN, tempString.length());
		ctr++;

		tempString = informationContainer.getName();
		System.arraycopy(tempString.getBytes(), 0, plain, ctr
				* InformationContainer.ATTRIBUTE_LEN, tempString.length());
		ctr++;

		tempString = informationContainer.getEncryptedName();
		System.arraycopy(tempString.getBytes(), 0, plain, ctr
				* InformationContainer.ATTRIBUTE_LEN, tempString.length());
		ctr++;

		tempString = informationContainer.getLocalEncryptedLocation();
		System.arraycopy(tempString.getBytes(), 0, plain, ctr
				* InformationContainer.ATTRIBUTE_LEN, tempString.length());
		ctr++;

		tempString = informationContainer.getCloudLocation();
		System.arraycopy(tempString.getBytes(), 0, plain, ctr
				* InformationContainer.ATTRIBUTE_LEN, tempString.length());
		ctr++;

		tempString = informationContainer.getTimestamp();
		System.arraycopy(tempString.getBytes(), 0, plain, ctr
				* InformationContainer.ATTRIBUTE_LEN, tempString.length());
		ctr++;
		System.arraycopy(informationContainer.getKey(), 0, plain, ctr
				* InformationContainer.ATTRIBUTE_LEN,
				informationContainer.getKey().length);
		ctr++;

		byte[] tempByte = informationContainer.getEncryption().getByteArray();
		System.arraycopy(tempByte, 0, plain, ctr
				* InformationContainer.ATTRIBUTE_LEN, tempByte.length);
		ctr++;

		return plain;
	}
}
