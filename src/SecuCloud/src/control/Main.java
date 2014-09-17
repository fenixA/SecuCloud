package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import view.MainWindow;
import model.cloudConnector.CloudConnectorGoogleGsutilTEMP;
import model.container.InformationContainer;

public class Main implements ActionListener {
	public static final int FILEIDENT_LEN = 64;

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

	public void actionPerformed(ActionEvent ac) {

	}

	public void toggle_MainWindow_fileSelected(File selectedFile) {
		this.fileList.add(this.cc.upload(this.fc.encryptFile(selectedFile)));
		if (this.mW != null){
			this.mW.dispose();
			this.mW = new MainWindow(this.SoftwareName);
		}
	}
	
	public ArrayList<InformationContainer> getFileList(){
		return fileList;
	}

	public static void main(String[] args) {
		Main main = Main.getInstance();
		main.mW = null;
		File testFile = new File(
				"./../../data/testByteInput.hex");
		main.toggle_MainWindow_fileSelected(testFile);
		main.mW = new MainWindow(main.SoftwareName);
		System.out.println(System.getProperty("user.dir"));

		//main.cc.listDir("");

		// System.out.println(this.selectedFile.getAbsolutePath().replaceAll(this.selectedFile.getName(),
		// ""));
		// cloudconnector.listDir(this.selectedFile.getAbsolutePath().replaceAll(this.selectedFile.getName(),
		// ""));
		// System.out.println(cloudconnector.download("TestStringDownload"));
		// System.out.println(cloudconnector.listDir("TestStringListDir"));

	}
}
