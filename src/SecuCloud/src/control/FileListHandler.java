package control;

import java.util.Vector;

import model.InformationContainer;

public class FileListHandler {
	private static FileListHandler instance;
	private Vector<InformationContainer> fileList = new Vector<InformationContainer>();

	public Vector<InformationContainer> getFileList() {
		return this.fileList;
	}

	public void addFile(InformationContainer informationContainer) {
		this.fileList.add(informationContainer);
		Main.getInstance().drawMainWindow();
		System.out.println(informationContainer.getCloudLocation());
	}

	public void deleteFile(InformationContainer informationContainer) {
		this.fileList.remove(informationContainer);
		Main.getInstance().drawMainWindow();
	}

	// Singleton
	public static FileListHandler getInstance() {
		if (FileListHandler.instance == null) {
			FileListHandler.instance = new FileListHandler();
		}
		return FileListHandler.instance;
	}

	public void synchronizeCloudStorage(Vector<String> cloudFiles) {
		for (InformationContainer informationContainer : fileList) {
			if(!cloudFiles.contains(informationContainer.getEncryptedName())){
				System.out.println("shit!!!");
			}
		}
	}

	public InformationContainer selectByEncryptedName(String encryptedName) {
		for (InformationContainer informationContainer : fileList) {
			if (informationContainer.getEncryptedName().equals(encryptedName)) {
				return informationContainer;
			}
		}
		return null;
	}
}
