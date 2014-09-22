package control.threads;

import java.io.File;
import java.util.ArrayList;

import control.FileComputer;
import control.Main;
import model.InformationContainer;
import model.cloudConnector.CloudConnectorGoogleGsutilTEMP;

public class Uploader implements Runnable {

	private File selectedFile;
	private FileComputer fileComputer;
	private CloudConnectorGoogleGsutilTEMP cloudConnectorGSUTIL;
	
	public Uploader(
			CloudConnectorGoogleGsutilTEMP cloudConnectorGSUTIL,
			FileComputer fileComputer, File selectedFile) {
		this.cloudConnectorGSUTIL = cloudConnectorGSUTIL;
		this.fileComputer = fileComputer;
		this.selectedFile = selectedFile;

	}

	public void run() {
		private ArrayList<InformationContainer> fileList = Main.getInstance().getFileList();
		fileList.add(this.cloudConnectorGSUTIL.upload(this.fileComputer
				.encryptFile(selectedFile)));

	}

}
