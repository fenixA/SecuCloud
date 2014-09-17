package control;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;

import javax.crypto.NoSuchPaddingException;

import view.CreateAccountWindow;
import view.MainWindow;
import model.cloudConnector.CloudConnectorGoogleGsutilTEMP;
import model.container.InformationContainer;

public class Main {
	public static final int FILE_IDENT_LEN = 64;
	public static final String ENCRYPTED_DATA_LOCATION = "./../../data/encrypted/";

	private static Main instance;
	private MainWindow mW;
	private CreateAccountWindow caW;
	private FileComputer fc;
	private CloudConnectorGoogleGsutilTEMP cc;

	private String softwareName;
	private String userName;
	private String userPassword;
	private ArrayList<InformationContainer> fileList = new ArrayList<InformationContainer>();

	public Main() {
		this.softwareName = "SecuCloud";
		this.cc = new CloudConnectorGoogleGsutilTEMP();
		this.fc = new FileComputer();
	}

	// Singleton
	public static Main getInstance() {
		if (Main.instance == null) {
			Main.instance = new Main();
		}
		return Main.instance;
	}

	public void toggle_MainWindow_fileSelected(File selectedFile) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IOException {
		this.fileList.add(this.cc.upload(this.fc.encryptFile(selectedFile)));
		this.reloadMainWindow();
	}

	public void toggle_CreateAccountWindow_okButton(String userName,
			String userPassword) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.caW.dispose();
	}

	private void reloadMainWindow() {
		if (this.mW != null) {
			this.mW.dispose();
		}
		this.drawMainWindow();
	}

	private void drawMainWindow() {
		this.mW = new MainWindow(this.softwareName);
	}

	private void startup() throws InterruptedException, IOException {
		File settings = new File("./../../data/settings.txt");
		if (!settings.exists()) {
			settings.createNewFile();

			Main.getInstance().caW = new CreateAccountWindow();
			while (caW.isVisible()) {
				Thread.sleep(50);
			}

		} else if (true) {
		}
		Main.getInstance().drawMainWindow();
	}

	public ArrayList<InformationContainer> getFileList() {
		return this.fileList;
	}

	public static void main(String[] args) throws InterruptedException,
			IOException {
		Main main = Main.getInstance();
		main.startup();

		// Test code:
		File testFile = new File("./../../data/testByteInput.hex");
		// main.toggle_MainWindow_fileSelected(testFile);
		// /test

		// main.drawMainWindow();

		// main.cc.listDir("");

		// cloudconnector.listDir(this.selectedFile.getAbsolutePath().replaceAll(this.selectedFile.getName(),
		// ""));

	}
}
