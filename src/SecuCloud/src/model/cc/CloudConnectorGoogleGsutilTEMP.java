package model.cc;

import java.io.*;

import model.InformationContainer;
import control.SystemPathCollectorGsutilTEMP;

public class CloudConnectorGoogleGsutilTEMP implements CloudConnector {
	@Override
	public InformationContainer upload(InformationContainer input) {
		try {
			Process uploadProcess = Runtime.getRuntime().exec(
					new String[] {
							SystemPathCollectorGsutilTEMP.getPythonPath(),
							SystemPathCollectorGsutilTEMP.getGsutilPath(),
							"cp", input.getLocalEncryptedFileLocation(),
							"gs://fenixbucket/" + input.getEncryptedName() });
			uploadProcess.waitFor();
		} catch (IOException uploadIOException) {
			System.out.println(uploadIOException.toString());
		}

		catch (InterruptedException uploadInterruptException) {
			System.out.println(uploadInterruptException.toString());
		}
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
		// TODO Auto-generated method stub
	}

	private void remove(String path) {
		// TODO Auto-generated method stub
	}

}
