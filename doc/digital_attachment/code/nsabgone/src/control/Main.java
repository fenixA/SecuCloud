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

import control.util.CryptToolbox;
import view.NotificationWindow;
import view.CreateAccountWindow;
import view.LoginWindow;
import view.MainWindow;
import view.DeleteWindow;
import model.InformationContainer;
import model.InformationContainerStorer;
import model.cc.CloudConnectorGoogleGsutil;

/**
 * The Class Main. It is called in startup and is the connection between the
 * different software parts.
 */
public class Main {

	/** The Constant FILE_IDENT_LEN. */
	public static final int FILE_IDENT_LEN = 64;

	/** The Constant AES_KEY_LEN. */
	public static final int AES_KEY_LEN = 16;

	/** The Constant AES_BLOCK_SIZE. */
	public static final int AES_BLOCK_SIZE = 16;

	/** The Constant SALT_LEN. */
	public static final int SALT_LEN = 64;

	/** The Constant HASH_LEN. */
	public static final int HASH_LEN = 32;
	
	public static final String CLOUD_SEPERATOR = "/";

	/** The Constant USER_HOME. */
	public static final String USER_HOME = System.getProperty("user.home");

	/** The Constant DOWNLOAD_EXTENSION. */
	public static final String DOWNLOAD_EXTENSION = ".down";

	/** The Constant UPLOAD_EXTENSION. */
	public static final String UPLOAD_EXTENSION = ".up";

	/** The root softwares directory. */
	private String ROOT_DIR;

	/** The general settings file. */
	private String SETTINGS_FILE;

	/** The users directory. */
	private String USER_DIR;

	/** The users data directory. */
	private String USER_DATA_DIR;

	/** The users download directory. */
	private String USER_DOWNLOAD_DIR;

	/** The users temporary directory. */
	private String USER_TEMP_DIR;

	/** The singleton instance. */
	private static Main instance;

	/** The main window. */
	private MainWindow mainWindow;

	/** The about notification window. */
	private NotificationWindow aboutNotification;

	/** The help notification window. */
	private NotificationWindow helpNotification;

	/** The create account window. */
	private CreateAccountWindow createAccountWindow;

	/** The delete window. */
	private DeleteWindow deleteWindow;

	/** The login window. */
	private LoginWindow loginWindow;

	/** The settings file handler. */
	private SettingsFileHandler settingsFileHandler;

	/** The information container storer. */
	private InformationContainerStorer informationContainerStorer;

	/** The software name. */
	private String softwareName;

	/** The user name. */
	private String userName;

	/** The user password. */
	private String userPassword;

	/** The bucket. */
	private String bucket;

	/** The vector of started threads. */
	public Vector<Thread> threadVector = new Vector<Thread>();

	// getter n setter
	/**
	 * Gets the users name.
	 * 
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Gets the software name.
	 * 
	 * @return the software name
	 */
	public String getSoftwareName() {
		return this.softwareName;
	}

	/**
	 * Gets the users data directory.
	 * 
	 * @return the users data directory
	 */
	public String getUSER_DATA_DIR() {
		return USER_DATA_DIR;
	}

	/**
	 * Gets the users download directory.
	 * 
	 * @return the users download directory
	 */
	public String getUSER_DOWNLOAD_DIR() {
		return USER_DOWNLOAD_DIR;
	}

	/**
	 * Gets the user temporary directory.
	 * 
	 * @return the users temporary directory
	 */
	public String getUSER_TEMP_DIR() {
		return USER_TEMP_DIR;
	}

	/**
	 * Gets the bucket.
	 * 
	 * @return the bucket
	 */
	public String getBucket() {
		return bucket;
	}

	/**
	 * Gets the single instance of Main.
	 * 
	 * @return single instance of Main
	 */
	public static Main getInstance() {
		if (Main.instance == null) {
			Main.instance = new Main();
		}
		return Main.instance;
	}

	/**
	 * Instantiates a new main.
	 */
	public Main() {
		this.softwareName = "NSAbGONE";
	}

	/**
	 * Exit. Toggled when close actions in any ways are performed. It iterates
	 * through the thread vector and waits for each process to cleanly finish
	 * before saving the actual state and closing the application.
	 * 
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws NoSuchProviderException
	 *             the no such provider exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws ShortBufferException
	 *             the short buffer exception
	 * @throws IllegalBlockSizeException
	 *             the illegal block size exception
	 * @throws BadPaddingException
	 *             the bad padding exception
	 * @throws InvalidAlgorithmParameterException
	 *             the invalid algorithm parameter exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Collect working paths.
	 */
	private void collectWorkingPaths() {
		ROOT_DIR = USER_HOME + File.separator + softwareName;
		SETTINGS_FILE = ROOT_DIR + File.separator + "settings.cfg";
	}

	/**
	 * Draws a login window.
	 */
	private void drawLoginWindow() {
		if (loginWindow != null) {
			loginWindow.dispose();
		}
		loginWindow = new LoginWindow();
	}

	/**
	 * Draws a main window.
	 */
	public void drawMainWindow() {
		if (mainWindow != null) {
			mainWindow.dispose();
		}
		mainWindow = new MainWindow(this.softwareName);
	}

	/**
	 * Draws a create account window.
	 */
	private void drawCreateAccountWindow() {
		if (createAccountWindow != null) {
			createAccountWindow.dispose();
		}
		createAccountWindow = new CreateAccountWindow();
	}

	/**
	 * Draws a delete window.
	 */
	private void drawDeleteWindow() {
		deleteWindow = new DeleteWindow();
	}

	/**
	 * Draws a about notification.
	 */
	private void drawAboutNotification() {
		aboutNotification = new NotificationWindow();
	}

	/**
	 * Draws a help notification.
	 */
	private void drawHelpNotification() {
		helpNotification = new NotificationWindow();
	}

	/**
	 * Builds the users directory structure.
	 */
	private void buildUserDirectory() {
		File user_dir = new File(ROOT_DIR + File.separator + userName);
		if (!user_dir.exists()) {
			user_dir.mkdir();
		}
		USER_DIR = user_dir.getAbsolutePath();
		File user_data_dir = new File(USER_DIR + File.separator + "data");
		if (!user_data_dir.exists()) {
			user_data_dir.mkdir();
		}
		USER_DATA_DIR = user_data_dir.getAbsolutePath();
		File user_download_dir = new File(USER_DIR + File.separator
				+ "download");
		if (!user_download_dir.exists()) {
			user_download_dir.mkdir();
		}
		USER_DOWNLOAD_DIR = user_download_dir.getAbsolutePath();
		File user_temp_dir = new File(USER_DIR + File.separator + "temp");
		if (!user_temp_dir.exists()) {
			user_temp_dir.mkdir();
		}
		USER_TEMP_DIR = user_temp_dir.getAbsolutePath();
	}

	/**
	 * Tries to perform a login with the given user data.
	 * 
	 * @param userName
	 *            the users name
	 * @param userPassword
	 *            the users password
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws NoSuchProviderException
	 *             the no such provider exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws ShortBufferException
	 *             the short buffer exception
	 * @throws IllegalBlockSizeException
	 *             the illegal block size exception
	 * @throws BadPaddingException
	 *             the bad padding exception
	 * @throws InvalidAlgorithmParameterException
	 *             the invalid algorithm parameter exception
	 */
	private void tryLogin(String userName, String userPassword, String bucket)
			throws IOException, InterruptedException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException,
			InvalidAlgorithmParameterException {
		if (settingsFileHandler.verifyUserData(userName, userPassword)) {
			this.userName = userName;
			this.userPassword = userPassword;
			this.bucket = bucket;
			buildUserDirectory();
			this.informationContainerStorer = new InformationContainerStorer(
					this.userPassword);
			this.informationContainerStorer.loadFileList();
			this.drawMainWindow();
			this.checkSynchronicity();
		} else {
			drawLoginWindow();
		}
	}

	/**
	 * Check synchronicity. Checks if all files stored in the file list are sill
	 * saved in the cloud.
	 */
	private void checkSynchronicity() {
		Vector<String> lost = FileListHandler.getInstance()
				.synchronizeCloudStorage(
						new CloudConnectorGoogleGsutil().listDir());
		if (lost != null) {
			for (int i = 0; i < lost.size(); i++) {
				InformationContainer informationContainer = FileListHandler
						.getInstance().selectByEncryptedName(lost.get(i));
				if (informationContainer.getCloudLocation().contains(
						this.bucket)) {
					drawDeleteWindow();
					if (this.deleteWindow.handleInput(
							informationContainer.getName(), lost.get(i),
							DeleteWindow.command.syncStatus) == true) {
						FileListHandler.getInstance().deleteFile(
								informationContainer);
					}
				}
			}
		}
	}

	/**
	 * Startup. Called on program startup. Checks if there are saved
	 * configurations and users and determines if a account needs to be created
	 * or a login is possible.
	 * 
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void startup() throws InterruptedException, IOException {
		collectWorkingPaths();
		File temp = new File(ROOT_DIR);
		if (!temp.exists()) {
			temp.mkdirs();
		}
		settingsFileHandler = new SettingsFileHandler(SETTINGS_FILE);
		if (settingsFileHandler.users < 1) {
			drawCreateAccountWindow();
		} else {
			drawLoginWindow();
		}
	}

	/**
	 * Is called when a new file is selected to be added in the cloud.
	 * 
	 * @param selectedFile
	 *            A file object of the file to encrypt and upload.
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws NoSuchProviderException
	 *             the no such provider exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InvalidAlgorithmParameterException
	 *             the invalid algorithm parameter exception
	 */
	public void toggle_MainWindow_fileSelected(File selectedFile)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException, IOException,
			InvalidAlgorithmParameterException {
		InformationContainer informationContainer = CryptToolbox
				.encryptAndUploadFileCTR(selectedFile);
		FileListHandler.getInstance().addFile(informationContainer);
	}

	/**
	 * Called when a file is selected to be deleted from the cloud.
	 * 
	 * @param encryptedName
	 *            the encrypted name of the file to delete.
	 */
	public void toggle_MainWindow_delete(String encryptedName) {
		InformationContainer informationContainer = FileListHandler
				.getInstance().selectByEncryptedName(encryptedName);
		drawDeleteWindow();
		if (this.deleteWindow.handleInput(informationContainer.getName(),
				informationContainer.getEncryptedName(),
				DeleteWindow.command.klickEvent) == true) {
			Thread t = new Thread(new ThreadInstanceCreator(
					ThreadInstanceCreator.command.removeFile,
					informationContainer));
			t.start();
			threadVector.add(t);
			FileListHandler.getInstance().deleteFile(informationContainer);
		}
	}

	/**
	 * Is called when a file was selected do be downloaded and decrypted.
	 * 
	 * @param encryptedName
	 *            the encrypted name of file to download.
	 */
	public void toggle_MainWindow_download(String encryptedName) {
		InformationContainer informationContainer = FileListHandler
				.getInstance().selectByEncryptedName(encryptedName);
		CryptToolbox.downloadAndDecryptFileCTR(informationContainer);
	}

	/**
	 * Is called when the user has typed their data for a new account.
	 * 
	 * @param userName
	 *            the users name
	 * @param userPassword
	 *            the users password
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 */
	public void toggle_CreateAccountWindow_okButton(String userName,
			String userPassword) throws IOException, NoSuchAlgorithmException {
		this.createAccountWindow.dispose();
		settingsFileHandler.addUser(userName, userPassword);
		drawLoginWindow();
	}

	/**
	 * Is called if the user want's to see the window with "about" information.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void toggle_MainWindow_about() throws IOException {
		drawAboutNotification();
		;
		this.aboutNotification.aboutNotification();
	}

	/**
	 * Is called if the user want's to see the help window.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void toggle_MainWindow_help() throws IOException {
		drawHelpNotification();
		this.helpNotification.helpNotification();
	}

	/**
	 * Is called if the user has typed in their user data and wants to login.
	 * 
	 * @param userName
	 *            the users name
	 * @param userPassword
	 *            the users password
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws NoSuchProviderException
	 *             the no such provider exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws ShortBufferException
	 *             the short buffer exception
	 * @throws IllegalBlockSizeException
	 *             the illegal block size exception
	 * @throws BadPaddingException
	 *             the bad padding exception
	 * @throws InvalidAlgorithmParameterException
	 *             the invalid algorithm parameter exception
	 */
	public void toggle_LoginWindow_okButton(String userName,
			String userPassword, String bucket) throws IOException,
			InterruptedException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchProviderException,
			NoSuchPaddingException, ShortBufferException,
			IllegalBlockSizeException, BadPaddingException,
			InvalidAlgorithmParameterException {
		this.loginWindow.dispose();
		tryLogin(userName, userPassword, bucket);
	}

	/**
	 * Is called if a user wants to create an additional account.
	 */
	public void toggle_LoginWindow_createButton() {
		this.loginWindow.dispose();
		drawCreateAccountWindow();
	}

	/**
	 * The main method. Triggers the programs startup.
	 * 
	 * @param args
	 *            the arguments
	 * @throws InterruptedException
	 *             the interrupted exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws InvalidKeyException
	 *             the invalid key exception
	 * @throws NoSuchAlgorithmException
	 *             the no such algorithm exception
	 * @throws NoSuchProviderException
	 *             the no such provider exception
	 * @throws NoSuchPaddingException
	 *             the no such padding exception
	 * @throws ShortBufferException
	 *             the short buffer exception
	 * @throws IllegalBlockSizeException
	 *             the illegal block size exception
	 * @throws BadPaddingException
	 *             the bad padding exception
	 * @throws InvalidAlgorithmParameterException
	 *             the invalid algorithm parameter exception
	 */
	public static void main(String[] args) throws InterruptedException,
			IOException, InvalidKeyException, NoSuchAlgorithmException,
			NoSuchProviderException, NoSuchPaddingException,
			ShortBufferException, IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException {
		Main main = Main.getInstance();
		main.startup();
	}
}
