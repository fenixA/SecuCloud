package control;

import java.util.Vector;

import model.InformationContainer;

public class FileListHandler {
	private static FileListHandler instance;
	private Vector<InformationContainer> fileList = new Vector<InformationContainer>();

	public Vector<InformationContainer> getFileList() {
		return this.fileList;
	}

	public void addFile(InformationContainer ic) {
		this.fileList.add(ic);
		Main.getInstance().drawMainWindow();
		System.out.println("Filehandler.addFile()");
	}

	public void purgeFile(InformationContainer ic) {
		Main.getInstance().drawMainWindow();
	}

	// Singleton
	public static FileListHandler getInstance() {
		if (FileListHandler.instance == null) {
			FileListHandler.instance = new FileListHandler();
		}
		return FileListHandler.instance;
	}

	public InformationContainer selectByEncryptedName(
			String encryptedName) {
		for(InformationContainer ic : fileList ){
			if (ic.getEncryptedName().equals(encryptedName)){
				return ic;
			}
		}
		return null;
	}
}
