package control.crypt;

import java.security.*;
import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public final class Crypt {
	public Crypt() {

	}

	public static File encryptFileSymAesCTR(File srcFile, String dstPath, byte[] key) {
		File encryptedFile = null;
		if (key.length != 16) {
			System.out.println("Error: Invalid key length!");
			return encryptedFile;
		}
		try {
			encryptedFile = new File(dstPath);
			FileInputStream fis;
			FileOutputStream fos;
			CipherInputStream cis;
			
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			Cipher encrypt = Cipher.getInstance("AES/CTR/PKCS5Padding",
					"SunJCE");
			encrypt.init(Cipher.ENCRYPT_MODE, secretKey);
			// Open the Plaintext file
			try {
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
			} catch (IOException err) {
				System.out.println("Error: Cannot open file!");
				return encryptedFile;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedFile;
	}

	public static File decryptFileSymAesCTR(File srcFile, String dstPath, byte[] key) {
		File decryptedFile = null;
		File tempFile = srcFile;
		if (key.length != 16) {
			System.out.println("Error: Invalid key length!");
			return decryptedFile;
		}
		try {
			decryptedFile = new File(dstPath);
			FileInputStream fis;
			FileOutputStream fos;
			CipherInputStream cis;
			SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
			// Creation of Cipher objects
			Cipher decrypt = Cipher.getInstance("AES/CTR/PKCS5Padding",
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
