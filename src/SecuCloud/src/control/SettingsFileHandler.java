package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import control.util.CryptToolbox;

public class SettingsFileHandler {
	private static final int PASSWORD_INFO_LEN = Main.HASH_LEN + Main.SALT_LEN;
	private static final int MAX_NAME_LEN = 256;
	private static final char SEPETATOR = ':';
	private String settingsFileName;
	public int users = 0;
	private Map<byte[], byte[]> userData = new HashMap<byte[], byte[]>();

	public SettingsFileHandler(String settingsFileName) throws IOException {
		this.settingsFileName = settingsFileName;
		readSettingsFile();
	}

	public void buildNewSettingsFile() throws IOException {
		File settings = new File(this.settingsFileName);
		settings.createNewFile();
	}

	public void addUser(String userName, String userPassword)
			throws IOException, NoSuchAlgorithmException {
		System.out.println("SettingsFileHandler.addUser()");
		FileOutputStream stream = new FileOutputStream(this.settingsFileName,
				true);
		byte[] salt = CryptToolbox.generateRandomBytes(Main.SALT_LEN);
		byte[] hashBase = new byte[Main.SALT_LEN
				+ userPassword.getBytes().length];
		System.arraycopy(userPassword.getBytes(), 0, hashBase, 0,
				userPassword.getBytes().length);
		System.arraycopy(salt, 0, hashBase, userPassword.length(),
				Main.SALT_LEN);
		byte[] hashedPassword = CryptToolbox.hashByteArraySHA256(hashBase);
		stream.write(userName.getBytes());
		stream.write(SEPETATOR);
		stream.write(hashedPassword);
		stream.write(salt);
		stream.flush();
		stream.close();
		readSettingsFile();
	}

	public boolean verifyUserData(String userName, String userPassword)
			throws IOException, NoSuchAlgorithmException {
		System.out.println("SettingsFileHandler.verifyUserData()");
		for (Map.Entry<byte[], byte[]> entry : userData.entrySet()) {
			byte[] hash = new byte[Main.HASH_LEN];
			byte[] hashBase = new byte[userPassword.length() + Main.SALT_LEN];
			System.arraycopy(entry.getValue(), 0, hash, 0, Main.HASH_LEN);
			System.arraycopy(userPassword.getBytes(), 0, hashBase, 0,
					userPassword.length());
			System.arraycopy(entry.getValue(), Main.HASH_LEN, hashBase,
					userPassword.length(), Main.SALT_LEN);
			if (Arrays.equals(entry.getKey(), userName.getBytes())
					&& Arrays.equals(
							CryptToolbox.hashByteArraySHA256(hashBase), hash)) {
				return true;
			}
		}
		return false;
	}

	private byte[] readInSingleUser(byte[] input) {
		System.out.println("SettingsFileHandler.readInSingleUserData()");
		int cntr = 0;
		byte[] temp = new byte[MAX_NAME_LEN];
		do {
			if (input[cntr] == SEPETATOR) {
				cntr++;
				break;
			} else {
				temp[cntr] = input[cntr];
				cntr++;
			}
		} while (cntr < MAX_NAME_LEN);

		byte[] userName = new byte[cntr - 1];
		System.arraycopy(temp, 0, userName, 0, cntr - 1);

		byte[] hashAndSalt = new byte[Main.HASH_LEN + Main.SALT_LEN];
		System.arraycopy(input, cntr, hashAndSalt, 0, Main.HASH_LEN);
		System.arraycopy(input, Main.HASH_LEN + cntr, hashAndSalt,
				Main.HASH_LEN, Main.SALT_LEN);

		byte[] result = new byte[input.length - cntr - PASSWORD_INFO_LEN];
		System.arraycopy(input, cntr + PASSWORD_INFO_LEN, result, 0,
				input.length - cntr - PASSWORD_INFO_LEN);
		this.userData.put(userName, hashAndSalt);
		this.users++;
		return result;
	}

	private void readSettingsFile() throws IOException {
		System.out.println("SettingsFileHandler.readSettingsFile()");
		File settingsFile = new File(this.settingsFileName);
		if (!settingsFile.exists()) {
			this.buildNewSettingsFile();
		}
		byte[] temp = new byte[(int) settingsFile.length()];
		FileInputStream stream = new FileInputStream(settingsFile);
		for (int i = 0; i < settingsFile.length(); i++) {
			temp[i] = (byte) stream.read();
		}
		while (temp.length > 1) {
			byte[] subResult = temp;
			temp = this.readInSingleUser(subResult);
		}
		stream.close();
	}
}
