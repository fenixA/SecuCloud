package model.cloudConnector;

import java.io.*;

import model.container.InformationContainer;
import control.SystemPathCollectorGsutilTEMP;

public class CloudConnectorGoogleGsutilTEMP implements CloudConnector {

	private String[] SystemVariables = new String[2];

	public CloudConnectorGoogleGsutilTEMP() {
		SystemPathCollectorGsutilTEMP systempathcollectorgsutil = new SystemPathCollectorGsutilTEMP();
		SystemVariables[0] = systempathcollectorgsutil.getGsutilPath();
		SystemVariables[1] = systempathcollectorgsutil.getPythonPath();
	}

	@Override
	public InformationContainer upload(InformationContainer input) {
		try {
			Process uploadProcess = Runtime.getRuntime().exec(
					new String[] {
							SystemPathCollectorGsutilTEMP.getPythonPath(),
							SystemPathCollectorGsutilTEMP.getGsutilPath(),
							"cp", input.getLocalEncryptedFileLocation(),
							"gs://fenixbucket/" + input.getEncryptedName() });
			input.setCloudFileLocation("gs://fenixbucket/" + input.getEncryptedName());
			uploadProcess.waitFor();
		} catch (IOException uploadIOException) {
			System.out.println(uploadIOException.toString());
		}

		catch (InterruptedException uploadInterruptException) {
			System.out.println(uploadInterruptException.toString());
		}
		System.out.println("input.getEncryptedName() : " + input.getEncryptedName());
		return input;
	}

	@Override
	public String download(File inputFile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void listDir(String path) {
		try {
			Process listProcess = Runtime.getRuntime().exec(
					new String[] {
							SystemPathCollectorGsutilTEMP.getPythonPath(),
							SystemPathCollectorGsutilTEMP.getGsutilPath(),
							"ls", "gs://", path });
			listProcess.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					listProcess.getInputStream()));
			String line;
			while (true) {
				line = reader.readLine();
				if (line == null) {
					break;
				}
				System.out.println(line);
			}
		} catch (IOException uploadIOException) {
			System.out.println(uploadIOException.toString());
		}

		catch (InterruptedException uploadInterruptException) {
			System.out.println(uploadInterruptException.toString());
		}
	}

	private void copy(String SrcPath, String DestPath) {

	}

	private void remove(String path) {

	}

}
