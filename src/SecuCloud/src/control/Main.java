package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

//import model.CloudConnectorGoogleGsutilTEMP;
import view.MainWindow;

public class Main implements ActionListener{
	private static Main instance;
	private String SoftwareName;
	private MainWindow mW;
	private File selectedFile;
	
	public Main() {
		this.SoftwareName = "SecuCloud";
		this.mW = new MainWindow(SoftwareName);

	}
	
	public static Main getInstance(){
		 if(Main.instance == null){
			 Main.instance = new Main();
			 }
		 return Main.instance;
	}

	public void actionPerformed(ActionEvent ac){
		
	}

	public void toggle_AboutWindow_fileSelected(File selectedFile){
		this.selectedFile = selectedFile;
		System.out.println("File selected: " + selectedFile.getAbsolutePath());	
	}
	
	public static void main(String[] args) {
		Main.getInstance();
		
		/*
		 * CloudConnectorGoogleGsutilTEMP cloudconnector = new
		 * CloudConnectorGoogleGsutilTEMP();
		 * cloudconnector.upload("TestStringUpload");
		 * System.out.println(cloudconnector.download("TestStringDownload"));
		 * System.out.println(cloudconnector.listDir("TestStringListDir"));
		 */
	}

}
