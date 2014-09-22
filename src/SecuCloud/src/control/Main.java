package control;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Iterator;
import java.util.Vector;

import javax.crypto.NoSuchPaddingException;

import view.CreateAccountWindow;
import view.LoginWindow;
import view.MainWindow;
import model.InformationContainer;
import model.cc.CloudConnectThreader;
import model.cc.CloudConnector.command;

public class Main {
	public static final int FILE_IDENT_LEN = 64;
	public static final int AES_KEY_LEN = 16;

	public static final String USER_HOME = System.getProperty("user.home");
	private String ROOT_DIR;
	private String SETTINGS_FILE;
	private String USER_DIR;
	private String USER_DATA_DIR;
	private String USER_ENCRYPTED_DIR;

	private static Main instance;
	private MainWindow mainWindow;
	private CreateAccountWindow createAccountWindow;
	private LoginWindow loginWindow;
	private SettingsFileHandler settingsFileHandler;

	private String softwareName;
	private String userName;
	private String userPassword;
	private String bucket = "fenixbucket";
	private Vector<Thread> cloudConnectThreadVector = new Vector<Thread>();

	// getter n setter
	public String getUSER_ENCRYPTED_DATA_DIR() {
		return USER_ENCRYPTED_DIR;
	}

	public String getUserName() {
		return userName;
	}

	public String getUSER_DATA_DIR() {
		return USER_DATA_DIR;
	}

	public String getBucket() {
		return bucket;
	}

	public Main() {
		this.softwareName = "SecuCloud";
	}

	// Singleton
	public static Main getInstance() {
		if (Main.instance == null) {
			Main.instance = new Main();
		}
		return Main.instance;
	}

	public void toggle_MainWindow_fileSelected(File selectedFile)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException, IOException {
		InformationContainer informationContainer = CryptToolbox
				.encryptFile(selectedFile);
		Thread t = new Thread(new CloudConnectThreader(command.upload,
				informationContainer));
		t.start();
		cloudConnectThreadVector.add(t);
		FileListHandler.getInstance().addFile(informationContainer);
	}

	public void toggle_CreateAccountWindow_okButton(String userName,
			String userPassword) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.createAccountWindow.dispose();
	}

	public void toggle_LoginWindow_okButton(String userName, String userPassword) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.loginWindow.dispose();
	}

	public void drawMainWindow() {
		if (mainWindow != null) {
			mainWindow.dispose();
		}
		mainWindow = new MainWindow(this.softwareName);
	}

	private void collectWorkingPaths() {
		ROOT_DIR = USER_HOME + "/" + softwareName;
		SETTINGS_FILE = ROOT_DIR + "/settings.txt";
	}

	private void buildUserDirectory() {
		File user_dir = new File(ROOT_DIR + "/" + userName);
		if (!user_dir.exists()) {
			user_dir.mkdir();
		}
		USER_DIR = user_dir.getAbsolutePath();
		File user_encrypted_dir = new File(USER_DIR + "/encrypted");
		if (!user_encrypted_dir.exists()) {
			user_encrypted_dir.mkdir();
		}
		USER_ENCRYPTED_DIR = user_encrypted_dir.getAbsolutePath();
		System.out.println(USER_ENCRYPTED_DIR);
		File user_data_dir = new File(USER_DIR + "/data");
		if (!user_data_dir.exists()) {
			user_data_dir.mkdir();
		}
		USER_DATA_DIR = user_data_dir.getAbsolutePath();
	}

	private boolean tryLogin() throws IOException, InterruptedException {
		boolean flag = false;
		while (!flag) {
			loginWindow = new LoginWindow();
			while (loginWindow.isVisible()) {
				Thread.sleep(50);
			}
			flag = settingsFileHandler.verifyUserData(userName, userPassword);
		}
		return flag;
	}

	private void startup() throws InterruptedException, IOException {
		collectWorkingPaths();
		settingsFileHandler = new SettingsFileHandler(SETTINGS_FILE);
		File temp = new File(ROOT_DIR);
		if (!temp.exists()) {
			temp.mkdirs();
		}
		File settings = new File(SETTINGS_FILE);
		if (!settings.exists()) {
			createAccountWindow = new CreateAccountWindow();
			while (createAccountWindow.isVisible()) {
				Thread.sleep(50);
			}
			settingsFileHandler.buildNewSettingsFile();
			settingsFileHandler.addUser(userName, userPassword);
		}
		tryLogin();
		buildUserDirectory();
	}

	public void exit() throws InterruptedException {
		System.out.println("Main.exit()");
		Iterator<Thread> it = cloudConnectThreadVector.iterator();
		while (it.hasNext()) {
			Thread t = it.next();
			t.join();
		}
		System.exit(0);
	}

	/*
	 * private void mainloop() throws InterruptedException { while (true) {
	 * Iterator<Thread> it = CloudConnectThreadVector .iterator();
	 * CloudConnectThreader t; while (it.hasNext()) { t = it.next(); if
	 * (!t.isAlive()) { t.join();
	 * System.out.println(t.getReturnValueIdentifyer()); } } } }
	 */

	public static void main(String[] args) throws InterruptedException,
			IOException {
		Main main = Main.getInstance();
		main.startup();
		main.drawMainWindow();

		// Test code:
		// File testFile = new File("./../../data/testByteInput.hex");
		// main.toggle_MainWindow_fileSelected(testFile);
		// /test

		// main.drawMainWindow();

		// main.cc.listDir("");

		// cloudconnector.listDir(this.selectedFile.getAbsolutePath().replaceAll(this.selectedFile.getName(),
		// ""));

	}
}
