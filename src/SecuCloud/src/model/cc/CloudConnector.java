package model.cc;

import java.util.Vector;

import model.InformationContainer;

// TODO: Auto-generated Javadoc
/**
 * The Interface CloudConnector.
 */
public interface CloudConnector {

	/**
	 * Upload.
	 *
	 * @param informationContainer the information container
	 * @return the information container
	 */
	public InformationContainer upload(InformationContainer informationContainer);

	/**
	 * Download.
	 *
	 * @param informationContainer the information container
	 * @return true, if successful
	 */
	public boolean download(InformationContainer informationContainer);

	/**
	 * List dir.
	 *
	 * @return the vector
	 */
	public Vector<String> listDir();
	
	/**
	 * Removes the.
	 *
	 * @param informationContainer the information container
	 * @return true, if successful
	 */
	public boolean remove(InformationContainer informationContainer);
}
