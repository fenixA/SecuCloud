package secucloud;

import java.io.*;

public class CloudConnectorGoogleGsutil extends CloudConnectorAbs {

	@Override
	Boolean upload(String path) {
		try {
			Process uploadProcess = Runtime.getRuntime().exec("cmd /C dir 2>&1");
			//uploadProcess.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					uploadProcess.getInputStream()));
			String stdoutTmpString = reader.readLine();
			while (stdoutTmpString != null) {
				System.out.println(stdoutTmpString);
				stdoutTmpString = reader.readLine();
			}
		} catch (IOException uploadIOException) {
			System.out.println(uploadIOException.toString());
		} /*catch (InterruptedException uploadInterruptException) {
			System.out.println(uploadInterruptException.toString());
		}*/

		return null;
	}

	@Override
	String download(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	String listDir(String path) {
		// TODO Auto-generated method stub
		return null;
	}

}
