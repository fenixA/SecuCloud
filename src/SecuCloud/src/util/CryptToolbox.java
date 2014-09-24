package util;

import java.security.*;
import java.io.*;
import java.math.BigInteger;

import javax.crypto.*;
import javax.crypto.spec.*;

import control.Main;
import model.InformationContainer;
import model.InformationContainer.Encryption;

public final class CryptToolbox {
	public CryptToolbox() {

	}

	public static File encryptFileAesCTR(File srcFile, String dstPath,
			byte[] key) throws NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException,
			InvalidKeyException, IOException {
		File encryptedFile = null;
		if (key.length != Main.AES_KEY_LEN) {
			System.out.println("Error: Invalid key length!");
			return encryptedFile;
		}
		encryptedFile = new File(dstPath);
		FileInputStream fis;
		FileOutputStream fos;
		CipherInputStream cis;

		SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
		Cipher encrypt = Cipher.getInstance("AES/CTR/PKCS5Padding", "SunJCE");
		encrypt.init(Cipher.ENCRYPT_MODE, secretKey);
		// Open the Plaintext file
		fis = new FileInputStream(srcFile);
		cis = new CipherInputStream(fis, encrypt);
		// Write to the Encrypted file
		fos = new FileOutputStream(encryptedFile);
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
		return encryptedFile;
	}

	public static File decryptFileAesCTR(File srcFile, String dstPath,
			byte[] key) throws NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException,
			InvalidKeyException, IOException {
		File decryptedFile = null;
		File tempFile = srcFile;
		if (key.length != Main.AES_KEY_LEN) {
			System.out.println("Error: Invalid key length!");
			return decryptedFile;
		}
		decryptedFile = new File(dstPath);
		FileInputStream fis;
		FileOutputStream fos;
		CipherInputStream cis;
		SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
		// Creation of Cipher objects
		Cipher decrypt = Cipher.getInstance("AES/CTR/PKCS5Padding", "SunJCE");
		decrypt.init(Cipher.DECRYPT_MODE, secretKey);
		// Open the Encrypted file
		fis = new FileInputStream(tempFile);
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
	
	public static byte[] encryptByteArrayAesCTR(byte[] input, byte[] key){
		//TODO
		return new byte[0];
	}
	public static byte[] decryptByteArrayAesCTR(byte[] input){
		//TODO
		return new byte[0];
	}

	public static InformationContainer encryptFile(File selectedFile)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException, IOException {
		byte[] tempKey = generateRandomKey(Main.AES_KEY_LEN);
		String encryptedName = generateLocationString();
		InformationContainer temp = new InformationContainer(
				selectedFile.getAbsolutePath(), Main.getInstance()
						.getUSER_ENCRYPTED_DATA_DIR()
						+ "/"
						+ encryptedName
						+ ".enc", encryptedName, selectedFile.getName(), null,
				tempKey, Encryption.AES_CTR);

		CryptToolbox.encryptFileAesCTR(selectedFile,
				temp.getLocalEncryptedLocation(), tempKey);
		return temp;
	}

	public static byte[] generateRandomKey(int length) {
		SecureRandom random = new SecureRandom();
		byte key[] = new byte[length];
		random.nextBytes(key);
		return key;
	}

	public static String generateLocationString() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(Main.FILE_IDENT_LEN)
				.replace("[", "").replace("@", "");
	}

	public static byte[] hashStringSHA256(String input)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(input.getBytes("UTF-8"));
		return md.digest();
	}

	public static byte[] expandUserPassword(String password, int len) {
		byte[] expanding = password.getBytes();
		byte[] result = new byte[len];
		while (expanding.length < len) {
			byte[] temp = new byte[expanding.length * 2];
			System.arraycopy(expanding, 0, temp, 0, expanding.length);
			System.arraycopy(expanding, 0, temp, expanding.length,
					expanding.length);
		}
		System.arraycopy(expanding, 0, result, 0, len);
		return result;
	}
}
