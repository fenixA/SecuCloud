package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

//import model.CloudConnectorGoogleGsutilTEMP;
import view.*;
import model.*;

public class Main implements ActionListener {
	private static Main instance;
	private String SoftwareName;
	private MainWindow mW;
	private File selectedFile;
	private String currentSymKey;
	private CloudConnectorGoogleGsutilTEMP cc;

	public String getCurrentSymKey() {
		return currentSymKey;
	}

	public void setSymKey(String currentSymKey) {
		this.currentSymKey = currentSymKey;
	}

	public Main() {
		this.SoftwareName = "SecuCloud";
		this.mW = new MainWindow(SoftwareName);
		this.cc = new CloudConnectorGoogleGsutilTEMP();

	}

	public static Main getInstance() {
		if (Main.instance == null) {
			Main.instance = new Main();
		}
		return Main.instance;
	}

	public void actionPerformed(ActionEvent ac) {

	}

	public void toggle_AboutWindow_fileSelected(File selectedFile) {
		this.selectedFile = selectedFile;
		System.out.println("File selected: " + selectedFile.getAbsolutePath());
		Crypt.encryptFileSymAesECB(this.selectedFile, Main.getInstance()
				.getCurrentSymKey());
		Crypt.decryptFileSymAesECB(new File(selectedFile.getAbsolutePath()
				+ "_ecpt"), Main.getInstance().getCurrentSymKey());

	}

	public static void main(String[] args) {
		Main main = Main.getInstance();
		main.setSymKey("0123456789012345");
		
		
		File testFile = new File(
				"C:\\Users\\fenix\\Desktop\\IS_Projekt\\data\\testByteInput.hex");
		main.toggle_AboutWindow_fileSelected(testFile);
		
		main.cc.listDir(main.selectedFile.getAbsolutePath().replaceAll(main.selectedFile.getName(), ""));

		//System.out.println(this.selectedFile.getAbsolutePath().replaceAll(this.selectedFile.getName(), ""));
		// cloudconnector.listDir(this.selectedFile.getAbsolutePath().replaceAll(this.selectedFile.getName(),
		// ""));
		// System.out.println(cloudconnector.download("TestStringDownload"));
		// System.out.println(cloudconnector.listDir("TestStringListDir"));

	}
}
