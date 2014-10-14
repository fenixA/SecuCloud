package model.cc;

import java.io.*;
import java.util.Vector;

import model.InformationContainer;
import control.Main;
import control.SystemPathCollector;

/**
 * The Class CloudConnectorGoogleGsutilTEMP.
 */
public class CloudConnectorGoogleGsutil implements CloudConnector {

	/** The Constant GS_PROTOCOL. */
	private static final String GS_PROTOCOL = "gs://";

	/** The Constant CMD_COPY. */
	private static final String CMD_COPY = "cp";

	/** The Constant CMD_LIST. */
	private static final String CMD_LIST = "ls";
	// private static final String CMD_CREATEBUCKET = "mb";
	// private static final String CMD_MOVE = "mv";
	/** The Constant CMD_DELETE. */
	private static final String CMD_DELETE = "rm";

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.cc.CloudConnector#upload(model.InformationContainer)
	 */
	@Override
	public InformationContainer upload(InformationContainer informationContainer) {
		try {
			File testFile = new File(Main.getInstance().getUSER_DATA_DIR()
					+ File.separator + "test");
			FileOutputStream stream = new FileOutputStream(testFile);
			stream.write(new String(SystemPathCollector.getPythonPath()
					+ SystemPathCollector.getGsutilPath() + CMD_COPY
					+ informationContainer.getLocalEncryptedLocation()
					+ GS_PROTOCOL + informationContainer.getCloudLocation())
					.getBytes());
			stream.flush();
			stream.close();
			Process uploadProcess = Runtime
					.getRuntime()
					.exec(new String[] {
							SystemPathCollector.getPythonPath(),
							SystemPathCollector.getGsutilPath(),
							CMD_COPY,
							informationContainer.getLocalEncryptedLocation(),
							GS_PROTOCOL
									+ informationContainer.getCloudLocation() });
			uploadProcess.waitFor();
		} catch (IOException uploadIOException) {
			System.out.println(uploadIOException.toString());
		}

		catch (InterruptedException uploadInterruptException) {
			System.out.println(uploadInterruptException.toString());
		}
		return informationContainer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.cc.CloudConnector#download(model.InformationContainer)
	 */
	@Override
	public boolean download(InformationContainer informationContainer) {
		try {
			Process downloadProcess = Runtime.getRuntime().exec(
					new String[] {
							SystemPathCollector.getPythonPath(),
							SystemPathCollector.getGsutilPath(),
							CMD_COPY,
							GS_PROTOCOL
									+ informationContainer.getCloudLocation(),
							Main.getInstance().getUSER_TEMP_DIR()
									+ Main.CLOUD_SEPERATOR
									+ informationContainer.getEncryptedName()
									+ Main.DOWNLOAD_EXTENSION });
			downloadProcess.waitFor();
		} catch (IOException downloadIOException) {
			System.out.println(downloadIOException.toString());
			return false;
		} catch (InterruptedException downloadInterruptException) {
			System.out.println(downloadInterruptException.toString());
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.cc.CloudConnector#listDir()
	 */
	@Override
	public Vector<String> listDir() {
		System.out.println("CloudConnectorGoogleGsutil.listDir()");
		Vector<String> result = new Vector<String>();
		try {
			Process listProcess = Runtime.getRuntime().exec(
					new String[] { SystemPathCollector.getPythonPath(),
							SystemPathCollector.getGsutilPath(), CMD_LIST,
							GS_PROTOCOL + Main.getInstance().getBucket() });
			listProcess.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					listProcess.getInputStream()));
			String line;
			while (true) {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				String tmp = line.replaceAll(
						GS_PROTOCOL + Main.getInstance().getBucket()
								+ Main.CLOUD_SEPERATOR, "");
				result.add(tmp);
			}
		} catch (IOException listDirIOException) {
			System.out.println(listDirIOException.toString());
		} catch (InterruptedException listDirInterruptException) {
			System.out.println(listDirInterruptException.toString());
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.cc.CloudConnector#remove(model.InformationContainer)
	 */
	public boolean remove(InformationContainer informationContainer) {
		try {
			Process deleteProcess = Runtime
					.getRuntime()
					.exec(new String[] {
							SystemPathCollector.getPythonPath(),
							SystemPathCollector.getGsutilPath(),
							CMD_DELETE,
							GS_PROTOCOL
									+ informationContainer.getCloudLocation() });
			deleteProcess.waitFor();
		} catch (IOException deleteIOException) {
			System.out.println(deleteIOException.toString());
			return false;
		} catch (InterruptedException deleteInterruptException) {
			System.out.println(deleteInterruptException.toString());
			return false;
		}
		return true;
	}

}
