package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;

import view.MainWindow;
import control.crypt.Crypt;
//import model.CloudConnectorGoogleGsutilTEMP;
import model.cloudConnector.CloudConnectorGoogleGsutilTEMP;
import model.container.FileToKeyTableElement;
import model.container.FileToKeyTableElement.encryptionIdent;

public class Main implements ActionListener {
	public static final int FILEIDENT_LEN = 64;

	private static Main instance;
	private String SoftwareName;
	private MainWindow mW;
	private CloudConnectorGoogleGsutilTEMP cc;
	private ArrayList<FileToKeyTableElement> fileList = new ArrayList<FileToKeyTableElement>();

	public Main() {
		this.SoftwareName = "SecuCloud";
		this.cc = new CloudConnectorGoogleGsutilTEMP();
	}

	// Singleton
	public static Main getInstance() {
		if (Main.instance == null) {
			Main.instance = new Main();
		}
		return Main.instance;
	}

	public void actionPerformed(ActionEvent ac) {

	}

	public void toggle_MainWindow_fileSelected(File selectedFile) {
		byte[] tempKey = Toolbox.generateRandomKey(16);
		String encryptedName = Toolbox.generateLocationString();
		FileToKeyTableElement temp = new FileToKeyTableElement(
				selectedFile.getAbsolutePath(),
				selectedFile.getAbsolutePath().replaceAll(selectedFile.getName(), "") + encryptedName,
				encryptedName,
				selectedFile.getName(),
				null,
				tempKey,
				encryptionIdent.AES_CTR);
		System.out.println("encryptedName: " + temp.getEncryptedName());
		System.out.println("File selected: " + selectedFile.getAbsolutePath());
		System.out.println(tempKey);

		Crypt.encryptFileSymAesCTR(selectedFile,
				temp.getLocalEncryptedFileLocation(), tempKey);
		this.fileList.add(this.cc.upload(temp));
		if (this.mW != null){
			this.mW.dispose();
			this.mW = new MainWindow(this.SoftwareName);
		}		
	}
	
	public ArrayList<FileToKeyTableElement> getFileList(){
		return fileList;
	}

	public static void main(String[] args) {
		Main main = Main.getInstance();
		main.mW = null;
		File testFile = new File(
				"C:\\Users\\fenix\\Desktop\\IS_Projekt\\data\\testByteInput.hex");
		main.toggle_MainWindow_fileSelected(testFile);
		main.mW = new MainWindow(main.SoftwareName);

		//main.cc.listDir("");

		// System.out.println(this.selectedFile.getAbsolutePath().replaceAll(this.selectedFile.getName(),
		// ""));
		// cloudconnector.listDir(this.selectedFile.getAbsolutePath().replaceAll(this.selectedFile.getName(),
		// ""));
		// System.out.println(cloudconnector.download("TestStringDownload"));
		// System.out.println(cloudconnector.listDir("TestStringListDir"));

	}
}