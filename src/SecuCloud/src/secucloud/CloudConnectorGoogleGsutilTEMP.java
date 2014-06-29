package secucloud;

import java.io.*;

public class CloudConnectorGoogleGsutilTEMP extends CloudConnectorAbs {

	private String[] SystemVariables = new String[2];

	public CloudConnectorGoogleGsutilTEMP() {
		SystemPathCollectorGsutilTEMP systempathcollectorgsutil = new SystemPathCollectorGsutilTEMP();
		SystemVariables[0] = systempathcollectorgsutil.getGsutilPath();
		SystemVariables[1] = systempathcollectorgsutil.getPythonPath();
	}

	@Override
	public Boolean upload(String path) {
		try {
			Process uploadProcess = Runtime.getRuntime()
					.exec("cmd /C dir 2>&1");
			// uploadProcess.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					uploadProcess.getInputStream()));
			String stdoutTmpString = reader.readLine();
			while (stdoutTmpString != null) {
				System.out.println(stdoutTmpString);
				stdoutTmpString = reader.readLine();
			}
		} catch (IOException uploadIOException) {
			System.out.println(uploadIOException.toString());
		} /*
		 * catch (InterruptedException uploadInterruptException) {
		 * System.out.println(uploadInterruptException.toString()); }
		 */

		return null;
	}

	@Override
	public String download(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String listDir(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	private void copy(String SrcPath, String DestPath) {

	}

	private void remove(String path) {

	}

}
