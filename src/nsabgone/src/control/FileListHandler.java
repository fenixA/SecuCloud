package control;

import java.util.ListIterator;
import java.util.Vector;

import model.InformationContainer;

/**
 * The Class FileListHandler is a singleton class, which handles system wide a
 * Vector of InformationContainers to represent a list of the files saved in the
 * cloud with their additional information.
 */
public class FileListHandler {

	/** The singleton instance. */
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
	
	public Vector<InformationContainer> getFilesInBucket(){
		Vector<InformationContainer> result =  new Vector<InformationContainer>();
		ListIterator<InformationContainer> listIterator = fileList
				.listIterator();
		while (listIterator.hasNext()) {
			InformationContainer tempFileElement = listIterator.next();
			if(tempFileElement.getCloudLocation().contains(Main.getInstance().getBucket())){
				result.add(tempFileElement);
			}
		}
		return result;
	}
	/**
	 * Adds given InformationContainer to file list.
	 * 
	 * @param informationContainer
	 *            the information container to add.
	 */
	public void addFile(InformationContainer informationContainer) {
		this.fileList.add(informationContainer);
		Main.getInstance().drawMainWindow();
	}

	/**
	 * Delete given InformationContainer from file list.
	 * 
	 * @param informationContainer
	 *            the information container to delete.
	 */
	public void deleteFile(InformationContainer informationContainer) {
		this.fileList.remove(informationContainer);
		Main.getInstance().drawMainWindow();
	}

	// Singleton
	/**
	 * Gets the singleton instance of FileListHandler.
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
	 * Synchronize cloud storage. A function to check if all files in file list
	 * are contained in the given Vector.
	 * 
	 * @param cloudFiles
	 *            A Vector with all files in the cloud by their encrypted name.
	 * @return A Vector of files missing in the cloud but having a
	 *         representation in file list.
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
	 * Select by encrypted name. Returns the InformationContainer of file
	 * list with given encrypted name.
	 * 
	 * @param encryptedName
	 *            The encrypted name of the selected file.
	 * @return The InformationContainer with the given encrypted name.
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
