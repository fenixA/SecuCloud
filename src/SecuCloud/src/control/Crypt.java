package control;

import java.security.*;
import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public final class Crypt {
	public Crypt() {

	}

	public static File encryptFileSymAesECB(File inputFile, String key) {
		File encryptedFile = null;
		if (key.length() != 16) {
			System.out.println("Error: Invalid key length!");
			return encryptedFile;
		}
		try {
			encryptedFile = new File(inputFile.getAbsolutePath() + "_ecpt");
			FileInputStream fis;
			FileOutputStream fos;
			CipherInputStream cis;
			// Creation of Secret key

			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
			// Creation of Cipher objects
			Cipher encrypt = Cipher.getInstance("AES/ECB/PKCS5Padding",
					"SunJCE");
			encrypt.init(Cipher.ENCRYPT_MODE, secretKey);
			// Open the Plaintext file
			try {
				fis = new FileInputStream(inputFile);
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
			} catch (IOException err) {
				System.out.println("Error: Cannot open file!");
				return encryptedFile;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedFile;
	}

	public static File decryptFileSymAesECB(File inputFile, String key) {
		File decryptedFile = null;
		File tempFile = inputFile;
		if (key.length() != 16) {
			System.out.println("Error: Invalid key length!");
			return decryptedFile;
		}
		try {
			decryptedFile = new File(tempFile.getAbsolutePath().replaceAll("_ecpt", "_dcpt"));
			FileInputStream fis;
			FileOutputStream fos;
			CipherInputStream cis;
			SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
			// Creation of Cipher objects
			Cipher decrypt = Cipher.getInstance("AES/ECB/PKCS5Padding",
					"SunJCE");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedFile;
	}
}
