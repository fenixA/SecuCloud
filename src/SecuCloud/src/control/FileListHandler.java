package control;

import java.util.Vector;

import model.InformationContainer;

// TODO: Auto-generated Javadoc
/**
 * The Class FileListHandler.
 */
public class FileListHandler {
	
	/** The instance. */
	private static FileListHandler instance;
	
	/** The file list. */
	private Vector<InformationContainer> fileList = new Vector<InformationContainer>();

	/**
	 * Gets the file list.
	 *
	 * @return the file list
	 */
	public Vector<InformationContainer> getFileList() {
		return this.fileList;
	}

	/**
	 * Adds the file.
	 *
	 * @param informationContainer the information container
	 */
	public void addFile(InformationContainer informationContainer) {
		this.fileList.add(informationContainer);
		Main.getInstance().drawMainWindow();
	}

	/**
	 * Delete file.
	 *
	 * @param informationContainer the information container
	 */
	public void deleteFile(InformationContainer informationContainer) {
		this.fileList.remove(informationContainer);
		Main.getInstance().drawMainWindow();
	}

	// Singleton
	/**
	 * Gets the single instance of FileListHandler.
	 *
	 * @return single instance of FileListHandler
	 */
	public static FileListHandler getInstance() {
		if (FileListHandler.instance == null) {
			FileListHandler.instance = new FileListHandler();
		}
		return FileListHandler.instance;
	}

	/**
	 * Synchronize cloud storage.
	 *
	 * @param cloudFiles the cloud files
	 * @return the vector
	 */
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

	/**
	 * Select by encrypted name.
	 *
	 * @param encryptedName the encrypted name
	 * @return the information container
	 */
	public InformationContainer selectByEncryptedName(String encryptedName) {
		for (InformationContainer informationContainer : fileList) {
			if (informationContainer.getEncryptedName().equals(encryptedName)) {
				return informationContainer;
			}
		}
		return null;
	}
}
