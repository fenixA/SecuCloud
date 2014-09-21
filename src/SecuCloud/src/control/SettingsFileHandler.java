package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SettingsFileHandler {
	private String SETTINGS_FILE;
	private Map<byte[], byte[]> userData = new HashMap<byte[], byte[]>();

	public SettingsFileHandler(String settings) {
		this.SETTINGS_FILE = settings;
	}

	public void buildNewSettingsFile() throws IOException {
		File settings = new File(this.SETTINGS_FILE);
		settings.createNewFile();
	}

	public void addUser(String userName, String userPassword)
			throws IOException {
		FileOutputStream stream = new FileOutputStream(this.SETTINGS_FILE);
		stream.write(userName.getBytes());
		stream.write(':');
		stream.write(userPassword.getBytes());
		stream.write('\n');
		stream.close();
	}

	public boolean verifyUserdata(String userName, String userPassword)
			throws IOException {
		readSettingsFile();
		for (Map.Entry<byte[], byte[]> entry : userData.entrySet()) {
			if (Arrays.equals(entry.getKey(), userName.getBytes())
					&& Arrays.equals(entry.getValue(), userPassword.getBytes())) {
				return true;
			}
		}
		return false;
	}

	private void readSettingsFile() throws IOException {
		FileInputStream stream = new FileInputStream(new File(
				this.SETTINGS_FILE));
		int cntr = 0, tempPaswordLen = 0, tempNameLen = 0;
		byte[] temp = new byte[256];
		byte[] userName = new byte[128];
		byte[] userPassword = new byte[128];

		while ((temp[cntr] = (byte) stream.read()) != -1) {
			if ((char) temp[cntr] == ':') {
				tempNameLen = cntr;
				System.arraycopy(temp, 0, userName, 0, cntr);
				cntr = -1;
			} else if ((char) temp[cntr] == '\n') {
				tempPaswordLen = cntr;
				System.arraycopy(temp, 0, userPassword, 0, cntr);
				userData.put(Arrays.copyOfRange(userName, 0, tempNameLen),
						Arrays.copyOfRange(userPassword, 0, tempPaswordLen));
				cntr = -1;
			}
			cntr++;
		}
		stream.close();
	}

}
