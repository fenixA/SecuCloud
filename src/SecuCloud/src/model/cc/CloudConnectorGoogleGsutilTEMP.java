package model.cc;

import java.io.*;
import java.util.Vector;

import model.InformationContainer;
import control.Main;
import control.SystemPathCollectorGsutilTEMP;

public class CloudConnectorGoogleGsutilTEMP implements CloudConnector {
	private static final String GS_PROTOCOL = "gs://";
	private static final String CMD_COPY = "cp";
	private static final String CMD_LIST = "ls";
	// private static final String CMD_CREATEBUCKET = "mb";
	// private static final String CMD_MOVE = "mv";
	private static final String CMD_DELETE = "rm";

	@Override
	public InformationContainer upload(InformationContainer informationContainer) {
		try {
			Process uploadProcess = Runtime
					.getRuntime()
					.exec(new String[] {
							SystemPathCollectorGsutilTEMP.getPythonPath(),
							SystemPathCollectorGsutilTEMP.getGsutilPath(),
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

	@Override
	public boolean download(InformationContainer informationContainer) {
		try {
			Process downloadProcess = Runtime.getRuntime().exec(
					new String[] {
							SystemPathCollectorGsutilTEMP.getPythonPath(),
							SystemPathCollectorGsutilTEMP.getGsutilPath(),
							CMD_COPY,
							GS_PROTOCOL
									+ informationContainer.getCloudLocation(),
							Main.getInstance().getUSER_TEMP_DIR() + "/"
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

	@Override
	public Vector<String> listDir() {
		System.out.println("CloudConnectorGoogleGsutilTEMP.listDir()");
		Vector<String> result = new Vector<String>();
		try {
			Process listProcess = Runtime.getRuntime().exec(
					new String[] {
							SystemPathCollectorGsutilTEMP.getPythonPath(),
							SystemPathCollectorGsutilTEMP.getGsutilPath(),
							CMD_LIST,
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
				String tmp = line.replaceAll(GS_PROTOCOL + Main.getInstance().getBucket() + "/", "");
				result.add(tmp);
			}
		} catch (IOException listDirIOException) {
			System.out.println(listDirIOException.toString());
		} catch (InterruptedException listDirInterruptException) {
			System.out.println(listDirInterruptException.toString());
		}
		return result;
	}

	public boolean remove(InformationContainer informationContainer) {
		try {
			Process deleteProcess = Runtime
					.getRuntime()
					.exec(new String[] {
							SystemPathCollectorGsutilTEMP.getPythonPath(),
							SystemPathCollectorGsutilTEMP.getGsutilPath(),
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
