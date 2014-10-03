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

	public Vector<String> synchronizeCloudStorage(Vector<String> cloudFiles) {
		Vector<String> lost = new Vector<String>();
		for (InformationContainer informationContainer : fileList) {
			if (!cloudFiles.contains(informationContainer.getEncryptedName())) {
				lost.add(informationContainer.getEncryptedName());
			}
		}
		if (lost.size() == 0) {
			return null;
		}
		return lost;

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
