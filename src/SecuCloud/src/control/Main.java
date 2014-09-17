package control;

import java.io.File;
import java.util.ArrayList;

import view.CreateAccountWindow;
import view.MainWindow;
import model.cloudConnector.CloudConnectorGoogleGsutilTEMP;
import model.container.InformationContainer;

public class Main {
	public static final int FILEIDENT_LEN = 64;
	public static final String ENCRYPTED_DATA_LOCATION = "./../../data/encrypted/";

	private static Main instance;
	private String SoftwareName;
	private MainWindow mW;
	private CloudConnectorGoogleGsutilTEMP cc;
	private ArrayList<InformationContainer> fileList = new ArrayList<InformationContainer>();
	private FileComputer fc;

	public Main() {
		this.SoftwareName = "SecuCloud";
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

	public void toggle_MainWindow_fileSelected(File selectedFile) {
		this.fileList.add(this.cc.upload(this.fc.encryptFile(selectedFile)));
		this.reloadMainWindow();
	}

	private void reloadMainWindow() {
		if (this.mW != null) {
			this.mW.dispose();
			this.mW = new MainWindow(this.SoftwareName);
		}
	}
	
	private void drawMainWindow(){
		this.mW = new MainWindow(this.SoftwareName);
	}

	public ArrayList<InformationContainer> getFileList() {
		return fileList;
	}

	public static void main(String[] args) {
		Main main = Main.getInstance();
		File settings = new File("./../../data/settings.txt");
		if (!settings.exists()) {
			CreateAccountWindow caW = new CreateAccountWindow();
		}
		// Test code:
		File testFile = new File("./../../data/testByteInput.hex");
		//main.toggle_MainWindow_fileSelected(testFile);
		System.out.println(System.getProperty("user.dir"));
		// /test
	
		//main.drawMainWindow();

		

		// main.cc.listDir("");

		// System.out.println(this.selectedFile.getAbsolutePath().replaceAll(this.selectedFile.getName(),
		// ""));
		// cloudconnector.listDir(this.selectedFile.getAbsolutePath().replaceAll(this.selectedFile.getName(),
		// ""));
		// System.out.println(cloudconnector.download("TestStringDownload"));
		// System.out.println(cloudconnector.listDir("TestStringListDir"));

	}
}
