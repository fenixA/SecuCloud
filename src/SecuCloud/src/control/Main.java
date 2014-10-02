package control;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Iterator;
import java.util.Vector;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

import control.util.ThreaderInstanceCreator;
import control.util.CryptToolbox;
import control.util.ThreaderInstanceCreator.command;
import view.CreateAccountWindow;
import view.LoginWindow;
import view.MainWindow;
import model.InformationContainer;
import model.InformationContainerStorer;
import model.cc.CloudConnectorGoogleGsutilTEMP;

public class Main {
	public static final int FILE_IDENT_LEN = 64;
	public static final int AES_KEY_LEN = 16;
	public static final int AES_BLOCK_SIZE = 16;
	public static final String USER_HOME = System.getProperty("user.home");

	private String ROOT_DIR;
	private String SETTINGS_FILE;
	private String USER_DIR;
	private String USER_DATA_DIR;
	private String USER_ENCRYPTED_DIR;
	private String USER_DOWNLOAD_DIR;
	private String USER_TEMP_DIR;

	private static Main instance;
	private MainWindow mainWindow;
	private CreateAccountWindow createAccountWindow;
	private LoginWindow loginWindow;
	private SettingsFileHandler settingsFileHandler;
	private InformationContainerStorer informationContainerStorer;

	private String softwareName;
	private String userName;
	private String userPassword;
	private String bucket = "fenixbucket";
	public Vector<Thread> threadVector = new Vector<Thread>();

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

	public String getUSER_DOWNLOAD_DIR() {
		return USER_DOWNLOAD_DIR;
	}
	
	public String getUSER_TEMP_DIR() {
		return USER_TEMP_DIR;
	}

	public String getBucket() {
		return bucket;
	}

	public static Main getInstance() {
		if (Main.instance == null) {
			Main.instance = new Main();
		}
		return Main.instance;
	}

	public Main() {
		this.softwareName = "SecuCloud";
	}

	public void exit() throws InterruptedException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException,
			InvalidAlgorithmParameterException, IOException {
		System.out.println("Main.exit()");
		this.mainWindow.dispose();
		if (this.informationContainerStorer.storeFileList()) {
			Iterator<Thread> it = threadVector.iterator();
			while (it.hasNext()) {
				Thread t = it.next();
				t.join();
			}
			System.exit(0);
		} else {
			System.out.println("Saving of assignments failed...");
		}
	}

	public void drawMainWindow() {
		if (mainWindow != null) {
			mainWindow.dispose();
		}
		mainWindow = new MainWindow(this.softwareName);
	}

	private void collectWorkingPaths() {
		ROOT_DIR = USER_HOME + "/" + softwareName;
		SETTINGS_FILE = ROOT_DIR + "/settings.cfg";
	}

	private void drawLoginWindow() {
		if (loginWindow != null) {
			loginWindow.dispose();
		}
		loginWindow = new LoginWindow();
	}

	private void drawCreateAccountWindow() {
		if (createAccountWindow != null) {
			createAccountWindow.dispose();
		}
		createAccountWindow = new CreateAccountWindow();
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
		File user_download_dir = new File(USER_DIR + "/download");
		if (!user_download_dir.exists()) {
			user_download_dir.mkdir();
		}
		USER_DOWNLOAD_DIR = user_download_dir.getAbsolutePath();
		File user_temp_dir = new File(USER_DIR + "/temp");
		if (!user_temp_dir.exists()) {
			user_temp_dir.mkdir();
		}
		USER_TEMP_DIR = user_temp_dir.getAbsolutePath();
	}

	private void tryLogin(String userName, String userPassword)
			throws IOException, InterruptedException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException,
			InvalidAlgorithmParameterException {
		if (settingsFileHandler.verifyUserData(userName, userPassword)) {
			this.userName = userName;
			this.userPassword = userPassword;
			buildUserDirectory();
			this.informationContainerStorer = new InformationContainerStorer(
					this.userPassword);
			this.informationContainerStorer.loadFileList();
			this.drawMainWindow();
		} else {
			drawLoginWindow();
		}
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
			settingsFileHandler.buildNewSettingsFile();
			drawCreateAccountWindow();
		} else {
			drawLoginWindow();
		}
	}

	public void toggle_MainWindow_fileSelected(File selectedFile)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException, IOException,
			InvalidAlgorithmParameterException {
		InformationContainer informationContainer = CryptToolbox
				.encryptFileCTR(selectedFile);
		FileListHandler.getInstance().addFile(informationContainer);
	}

	public void toggle_MainWindow_delete(String encryptedName) {
		InformationContainer informationContainer = FileListHandler
				.getInstance().selectByEncryptedName(encryptedName);
		Thread t = new Thread(new ThreaderInstanceCreator(command.removeFile,
				informationContainer));
		t.start();
		threadVector.add(t);
		FileListHandler.getInstance().deleteFile(informationContainer);
	}

	public void toggle_MainWindow_download(String encryptedName) {
		InformationContainer informationContainer = FileListHandler
				.getInstance().selectByEncryptedName(encryptedName);
		CryptToolbox.decryptFileCTR(informationContainer);
	}

	public void toggle_CreateAccountWindow_okButton(String userName,
			String userPassword) throws IOException {
		this.createAccountWindow.dispose();
		settingsFileHandler.addUser(userName, userPassword);
		drawLoginWindow();
	}

	public void toggle_LoginWindow_okButton(String userName, String userPassword)
			throws IOException, InterruptedException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException,
			InvalidAlgorithmParameterException {
		this.loginWindow.dispose();
		tryLogin(userName, userPassword);
	}

	public void toggle_LoginWindow_createButton() {
		this.loginWindow.dispose();
		drawCreateAccountWindow();
	}

	public static void main(String[] args) throws InterruptedException,
			IOException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException,
			ShortBufferException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException {
		Main main = Main.getInstance();
		main.startup();
	}
}
