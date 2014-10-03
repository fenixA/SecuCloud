package control.util;

import java.security.*;
import java.io.*;
import java.math.BigInteger;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import control.Main;
import control.util.ThreaderInstanceCreator.command;
import model.InformationContainer;
import model.InformationContainer.Encryption;

public final class CryptToolbox {
	public CryptToolbox() {

	}

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

	public static InformationContainer encryptFileCTR(File selectedFile)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException, IOException,
			InvalidAlgorithmParameterException {
		byte[] tempKey = generateRandomBytes(Main.AES_KEY_LEN);
		String encryptedName = generateLocationString();
		InformationContainer informationContainer = new InformationContainer(
				selectedFile.getAbsolutePath(), Main.getInstance()
						.getUSER_TEMP_DIR() + "/" + encryptedName,
				encryptedName, selectedFile.getName(), tempKey,
				Encryption.AES_CTR);
		Thread t = new Thread(new ThreaderInstanceCreator(command.encryptUploadFile,
				informationContainer));
		t.start();
		Main.getInstance().threadVector.add(t);
		return informationContainer;
	}

	public static void decryptFileCTR(InformationContainer informationContainer) {
		Thread t = new Thread(new ThreaderInstanceCreator(command.downloadDecryptFile,
				informationContainer));
		t.start();
		Main.getInstance().threadVector.add(t);
	}

	public static byte[] generateRandomBytes(int length) {
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

	public static byte[] hashByteArraySHA256(byte[] input)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(input);
		return md.digest();
	}

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
