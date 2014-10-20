package control.util;

import java.security.*;
import java.util.Arrays;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.StandardCopyOption;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import control.Main;
import control.ThreadInstanceCreator;
import control.ThreadInstanceCreator.command;
import model.InformationContainer;
import model.InformationContainer.Encryption;

// TODO: Auto-generated Javadoc
/**
 * The Class CryptToolbox is a collection cryptographic functions or functions
 * needed to start a successful encryption.
 */
public final class CryptToolbox {
	/**
	 * A threadable function which encrypts the given file AES in CTR mode and
	 * saves the cipher in given destination.
	 * 
	 * @param srcFile
	 *            the file to encrypt
	 * @param dstPath
	 *            the destination of cipher
	 * @param key
	 *            the encryption key
	 * @return the encrypted file
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws NoSuchProviderException
	 *             the no such provider exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InvalidAlgorithmParameterException
	 *             the invalid algorithm parameter exception
	 */
	public static File threadEncryptFileAesCTR(File srcFile, String dstPath,
			byte[] key) throws NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException,
			InvalidKeyException, IOException,
			InvalidAlgorithmParameterException {
		File encryptedFile = null;
		encryptedFile = new File(dstPath);
		FileInputStream fis;
		FileOutputStream fos;
		CipherInputStream cis;

		SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
		Cipher encrypt = Cipher.getInstance("AES/CTR/PKCS5Padding", "SunJCE");
		byte[] iv = generateRandomBytes(encrypt.getBlockSize());
		IvParameterSpec ps = new IvParameterSpec(iv);
		encrypt.init(Cipher.ENCRYPT_MODE, secretKey, ps);
		// Open the Plaintext file
		fis = new FileInputStream(srcFile);
		cis = new CipherInputStream(fis, encrypt);
		// Write to the Encrypted file
		fos = new FileOutputStream(encryptedFile);
		byte[] b = new byte[8];
		int i = cis.read(b);
		fos.write(iv);
		while (i != -1) {
			fos.write(b, 0, i);
			i = cis.read(b);
		}
		fos.flush();
		fos.close();
		cis.close();
		fis.close();
		return encryptedFile;
	}

	/**
	 * A threadable function which decrypts the given file AES in CTR mode and
	 * saves the plain in given destination.
	 * 
	 * @param srcFile
	 *            the file to decrypt
	 * @param dstPath
	 *            the destination of plain file
	 * @param key
	 *            the decryption key
	 * @return the decrypted file
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws NoSuchProviderException
	 *             the no such provider exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InvalidAlgorithmParameterException
	 *             the invalid algorithm parameter exception
	 */
	public static File threadDecryptFileAesCTR(File srcFile, String dstPath,
			byte[] key) throws NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException,
			InvalidKeyException, IOException,
			InvalidAlgorithmParameterException {
		File decryptedFile = null;
		File tempFile = srcFile;
		decryptedFile = new File(dstPath);
		FileInputStream fis;
		FileOutputStream fos;
		CipherInputStream cis;
		SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
		// Creation of Cipher objects
		Cipher decrypt = Cipher.getInstance("AES/CTR/PKCS5Padding", "SunJCE");
		fis = new FileInputStream(tempFile);
		byte[] iv = new byte[decrypt.getBlockSize()];
		for (int i = 0; i < decrypt.getBlockSize(); i++) {
			iv[i] = (byte) fis.read();
		}
		IvParameterSpec ps = new IvParameterSpec(iv);
		decrypt.init(Cipher.DECRYPT_MODE, secretKey, ps);
		// Open the Encrypted file
		cis = new CipherInputStream(fis, decrypt);
		// Write to the Decrypted file
		fos = new FileOutputStream(decryptedFile);
		byte[] b = new byte[8];
		int i = cis.read(b);
		while (i != -1) {
			fos.write(b, 0, i);
			i = cis.read(b);
		}
		fos.flush();
		fos.close();
		cis.close();
		fis.close();

		return decryptedFile;
	}

	/**
	 * Encrypts a byte array with AES in CTR mode and returns the encrypted
	 * array with the initialization vector added to the end.
	 * 
	 * @param input
	 *            the byte array to encrypt
	 * @param key
	 *            the encryption key
	 * @return the encrypted cipher and the initialization vector.
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws NoSuchProviderException
	 *             the no such provider exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws IllegalBlockSizeException
	 *             the illegal block size exception
	 * @throws ShortBufferException
	 *             the short buffer exception
	 * @throws BadPaddingException
	 *             the bad padding exception
	 * @throws InvalidAlgorithmParameterException
	 *             the invalid algorithm parameter exception
	 */
	public static byte[] encryptByteArrayAesCTR(byte[] input, byte[] key)
			throws NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, ShortBufferException,
			BadPaddingException, InvalidAlgorithmParameterException {
		SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/CTR/PKCS5Padding", "SunJCE");
		byte[] iv = generateRandomBytes(cipher.getBlockSize());
		IvParameterSpec ps = new IvParameterSpec(iv);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ps);
		byte[] temp = new byte[cipher.getOutputSize(input.length)];
		int ctLength = cipher.update(input, 0, input.length, temp, 0);
		ctLength += cipher.doFinal(temp, ctLength);
		byte output[] = new byte[temp.length + cipher.getBlockSize()];
		System.arraycopy(temp, 0, output, 0, temp.length);
		System.arraycopy(iv, 0, output, temp.length, iv.length);
		return output;
	}

	/**
	 * Decrypts a byte array with AES in CTR mode on which the initialization
	 * vector is added to the end.
	 * 
	 * @param input
	 *            the byte array with the cipher and the initialization vector.
	 * @param key
	 *            the decryption key
	 * @return the decrypted data
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws NoSuchProviderException
	 *             the no such provider exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws ShortBufferException
	 *             the short buffer exception
	 * @throws IllegalBlockSizeException
	 *             the illegal block size exception
	 * @throws BadPaddingException
	 *             the bad padding exception
	 * @throws InvalidAlgorithmParameterException
	 *             the invalid algorithm parameter exception
	 */
	public static byte[] decryptByteArrayAesCTR(byte[] input, byte[] key)
			throws NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, InvalidKeyException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException,
			InvalidAlgorithmParameterException {
		SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/CTR/PKCS5Padding", "SunJCE");
		byte[] iv = new byte[cipher.getBlockSize()];
		System.arraycopy(input, input.length - cipher.getBlockSize(), iv, 0,
				iv.length);
		IvParameterSpec ps = new IvParameterSpec(iv);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ps);
		byte[] output = new byte[cipher.getOutputSize(input.length
				- cipher.getBlockSize())];
		int ctLength = cipher.update(input, 0,
				input.length - cipher.getBlockSize(), output, 0);
		ctLength += cipher.doFinal(output, ctLength);
		return output;
	}

	/**
	 * Encrypts file AES in CTR mode and uploads it by creating a thread for
	 * this costly tasks.
	 * 
	 * @param selectedFile
	 *            the selected file
	 * @return a information container of the given and now encrypted file
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws NoSuchProviderException
	 *             the no such provider exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InvalidAlgorithmParameterException
	 *             the invalid algorithm parameter exception
	 */
	public static InformationContainer encryptAndUploadFileCTR(File selectedFile)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException, IOException,
			InvalidAlgorithmParameterException {
		byte[] tempKey = generateRandomBytes(Main.AES_KEY_LEN);
		String encryptedName = generateLocationString();
		InformationContainer informationContainer = new InformationContainer(
				selectedFile.getAbsolutePath(), Main.getInstance()
						.getUSER_TEMP_DIR() + File.separator + encryptedName,
				encryptedName, selectedFile.getName(), tempKey,
				Encryption.AES_CTR);
		Thread t = new Thread(new ThreadInstanceCreator(
				command.encryptUploadFile, informationContainer));
		t.start();
		Main.getInstance().threadVector.add(t);
		return informationContainer;
	}

	/**
	 * Downloads a file and decrypts it AES in CTR mode by creating a thread for
	 * this costly tasks. The decrypted plain file is saved in the users
	 * download directory.
	 * 
	 * @param informationContainer
	 *            the information container of selected file
	 */
	public static void downloadAndDecryptFileCTR(
			InformationContainer informationContainer) {
		Thread t = new Thread(new ThreadInstanceCreator(
				command.downloadDecryptFile, informationContainer));
		t.start();
		Main.getInstance().threadVector.add(t);
	}

	/**
	 * Generate random bytes. Generated a byte array of securely generated
	 * random bits in the given length.
	 * 
	 * @param length
	 *            the length of returned byte array
	 * @return the random bytes
	 */
	public static byte[] generateRandomBytes(int length) {
		SecureRandom random = new SecureRandom();
		byte key[] = new byte[length];
		random.nextBytes(key);
		return key;
	}

	/**
	 * Generate location string. Generates a string for the encrypted name of a
	 * uploaded file by a securely random function. It has the length of the
	 * Main.FILE_IDENT_LEN constant.
	 * 
	 * @return the random generated string
	 */
	public static String generateLocationString() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(Main.FILE_IDENT_LEN)
				.replace("[", "").replace("@", "");
	}

	/**
	 * Hash byte array by SHA256.
	 * 
	 * @param input
	 *            the byte array to be hashed
	 * @return the calculated hash
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 */
	public static byte[] hashByteArraySHA256(byte[] input)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(input);
		return md.digest();
	}

	/**
	 * Hash file sh a256.
	 *
	 * @param input file to hash
	 * @param len part size to be hashed
	 * @return the calculated hash
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] hashFileSHA256(File input, int len)
			throws NoSuchAlgorithmException, IOException {
		if (len == 0) {
			len = (int) input.length();
		}
		FileInputStream stream = new FileInputStream(input);
		byte[] temp = new byte[len];
		for (int i = 0; i < len; i++) {
			temp[i] = (byte) stream.read();
		}
		stream.close();
		return hashByteArraySHA256(temp);
	}

	/**
	 * Extract hash from the end of a file.
	 *
	 * @param input the file to extract from
	 * @return the extracted hash
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] extractHashSHA256FromFile(File input)
			throws IOException {
		FileInputStream iStream = new FileInputStream(input);
		byte[] temp = new byte[(int) input.length()];
		for (int i = 0; i < input.length(); i++) {
			temp[i] = (byte) iStream.read();
		}
		iStream.close();
		byte[] hash = new byte[Main.HASH_LEN];
		System.arraycopy(temp, temp.length - Main.HASH_LEN, hash, 0,
				Main.HASH_LEN);

		FileOutputStream oStream = new FileOutputStream(input);
		oStream.write(temp, 0, temp.length - Main.HASH_LEN);
		oStream.flush();
		oStream.close();
		return hash;
	}

	/**
	 * Verify the hash at the end of a file by the files data
	 *
	 * @param input the File
	 * @return true, if successful
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static boolean verifyFileHashSHA256(File input)
			throws NoSuchAlgorithmException, IOException {
		byte[] firstHash = hashFileSHA256(input, (int) input.length()
				- Main.HASH_LEN);
		byte[] secondHash = extractHashSHA256FromFile(input);
		if (Arrays.equals(firstHash, secondHash)) {
			return true;
		}
		return true;
	}

	/**
	 * Creates a temporary file with a sha256 attached to the end
	 *
	 * @param input the file to hash
	 * @return the temporary file
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static File createTempHashedFile(File input)
			throws NoSuchAlgorithmException, IOException {
		File returnFile = SupportFunctions.copyFile(input, Main.getInstance()
				.getUSER_TEMP_DIR() + File.separator + input.getName(),
				StandardCopyOption.REPLACE_EXISTING);

		FileOutputStream stream = new FileOutputStream(returnFile, true);
		stream.write(hashFileSHA256(input, 0));
		stream.flush();
		stream.close();
		return returnFile;
	}

	/**
	 * Expands the users password to given length to make it usable as an
	 * encryption key.
	 * 
	 * @param password
	 *            the users password
	 * @param len
	 *            the desired length of the expanded password
	 * @return the expanded password
	 */
	public static byte[] expandUserPassword(String password, int len) {
		System.out.println("CryptoToolbox.expandUserPassword()");
		byte[] expanding = password.getBytes();
		byte[] result = new byte[len];
		while (expanding.length < len) {
			byte[] temp = new byte[expanding.length * 2];
			System.arraycopy(expanding, 0, temp, 0, expanding.length);
			System.arraycopy(expanding, 0, temp, expanding.length,
					expanding.length);
			expanding = temp;
		}
		System.arraycopy(expanding, 0, result, 0, len);
		return result;
	}
}
