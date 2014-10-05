package model.cc;

import java.util.Vector;

import model.InformationContainer;

/**
 * The Interface CloudConnector defines the interface, a class needs to connect a cloud service.
 */
public interface CloudConnector {

	/**
	 * Upload.
	 *
	 * @param informationContainer the {@link InformationContainer} to be uploaded
	 * @return the {@link InformationContainer} with the added cloud information.
	 */
	public InformationContainer upload(InformationContainer informationContainer);

	/**
	 * Download.
	 *
	 * @param informationContainer the {@link InformationContainer} of the file to be downloaded.
	 * @return true, if successful
	 */
	public boolean download(InformationContainer informationContainer);

	/**
	 * List the cloud directory.
	 *
	 * @return the vector
	 */
	public Vector<String> listDir();
	
	/**
	 * Removes a file from the cloud.
	 *
	 * @param informationContainer the {@link InformationContainer} of the file to be removed.
	 * @return true, if successful
	 */
	public boolean remove(InformationContainer informationContainer);
}
