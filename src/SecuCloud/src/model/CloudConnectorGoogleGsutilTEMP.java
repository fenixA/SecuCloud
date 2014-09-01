package model;

import java.io.*;

import control.SystemPathCollectorGsutilTEMP;

public class CloudConnectorGoogleGsutilTEMP extends CloudConnectorAbs {

	private String[] SystemVariables = new String[2];

	public CloudConnectorGoogleGsutilTEMP() {
		SystemPathCollectorGsutilTEMP systempathcollectorgsutil = new SystemPathCollectorGsutilTEMP();
		SystemVariables[0] = systempathcollectorgsutil.getGsutilPath();
		SystemVariables[1] = systempathcollectorgsutil.getPythonPath();
	}

	@Override
	public Boolean upload(File inputFile) {

		return null;
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
					new String[] { "cmd.exe", "/c",
							"cd " + path + "&&dir 2>&1"});
			// uploadProcess.waitFor();
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
		} /*
		 * catch (InterruptedException uploadInterruptException) {
		 * System.out.println(uploadInterruptException.toString()); }
		 */
	}

	private void copy(String SrcPath, String DestPath) {

	}

	private void remove(String path) {

	}

}
