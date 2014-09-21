package control;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;

import javax.crypto.NoSuchPaddingException;

import view.CreateAccountWindow;
import view.LoginWindow;
import view.MainWindow;
import model.InformationContainer;
import model.cloudConnector.CloudConnectorGoogleGsutilTEMP;

public class Main {
	public static final int FILE_IDENT_LEN = 64;
	public static final int AES_KEY_LEN = 16;

	public static final String USER_HOME = System.getProperty("user.home");
	private String ROOT_DIR;
	private String SETTINGS_FILE;
	private String GENERAL_DATA_DIR;
	private String USER_DATA_DIR;
	private String USER_ENCRYPTED_DATA_DIR;

	private static Main instance;
	private MainWindow mainWindow;
	private CreateAccountWindow createAccountWindow;
	private LoginWindow loginWindow;
	private FileComputer fileComputer;
	private CloudConnectorGoogleGsutilTEMP cloudConnectorGSUTIL;
	private SettingsFileHandler settingsFileHandler;

	private String softwareName;
	private String userName;
	private String userPassword;
	private ArrayList<InformationContainer> fileList = new ArrayList<InformationContainer>();

	// getter n setter
	public String getUSER_ENCRYPTED_DATA_DIR() {
		return USER_ENCRYPTED_DATA_DIR;
	}
	
	public String getUserName() {
		return userName;
	}

	public Main() {
		this.softwareName = "SecuCloud";
		this.cloudConnectorGSUTIL = new CloudConnectorGoogleGsutilTEMP();
		this.fileComputer = new FileComputer();
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
		this.fileList.add(this.cloudConnectorGSUTIL.upload(this.fileComputer.encryptFile(selectedFile)));
		this.reloadMainWindow();
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

	private void reloadMainWindow() {
		if (mainWindow != null) {
			mainWindow.dispose();
		}
		drawMainWindow();
	}

	private void drawMainWindow() {
		mainWindow = new MainWindow(this.softwareName);
	}

	private void collectWorkingPaths() {
		ROOT_DIR = USER_HOME + "/" + softwareName;
		GENERAL_DATA_DIR = ROOT_DIR + "/data";
		SETTINGS_FILE = ROOT_DIR + "/settings.txt";
	}

	private void buildUserDirectory() {
		File user_data_dir = new File(GENERAL_DATA_DIR + "/" + userName);
		if (!user_data_dir.exists()) {
			user_data_dir.mkdir();
		}
		USER_DATA_DIR = user_data_dir.getAbsolutePath();
		File user_encrypted_dir = new File(USER_DATA_DIR + "/encrypted");
		if (!user_encrypted_dir.exists()) {
			user_encrypted_dir.mkdir();
		}
		USER_ENCRYPTED_DATA_DIR = user_encrypted_dir.getAbsolutePath();
	}
	
	private boolean tryLogin() throws IOException, InterruptedException{
		boolean flag = false;
		while(!flag){
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
		File temp = new File(GENERAL_DATA_DIR);
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
			buildUserDirectory();
			settingsFileHandler.addUser(userName, userPassword);
		}
		tryLogin();
	}

	public ArrayList<InformationContainer> getFileList() {
		return fileList;
	}

	public static void main(String[] args) throws InterruptedException,
			IOException {
		Main main = Main.getInstance();
		main.startup();
		
		

		main.drawMainWindow();

		// Test code:
		//File testFile = new File("./../../data/testByteInput.hex");
		// main.toggle_MainWindow_fileSelected(testFile);
		// /test

		// main.drawMainWindow();

		// main.cc.listDir("");

		// cloudconnector.listDir(this.selectedFile.getAbsolutePath().replaceAll(this.selectedFile.getName(),
		// ""));

	}
}
