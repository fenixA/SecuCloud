package control;

import java.io.File;
import java.io.IOException;

import control.SystemInformationCollector.OsID;

public class SystemPathCollectorGsutilTEMP {
	public static String getPythonPath() {
		if (SystemInformationCollector.getOperationSystem() == OsID.linux) {
			try {
				Process testPythonVersion = Runtime.getRuntime().exec(
						new String[] { "python2", "-V" });
				testPythonVersion.waitFor();
				if (testPythonVersion.exitValue() == 0) {
					return "python2";
				} else {
					System.out.println("Please install python2.6 or 2.7...");
					System.exit(1);
				}
			} catch (IOException uploadIOException) {
				System.out.println(uploadIOException.toString());
			}

			catch (InterruptedException uploadInterruptException) {
				System.out.println(uploadInterruptException.toString());
			}

		} else if (SystemInformationCollector.getOperationSystem() == OsID.windows) {
			File tempFile = new File("C:\\Python27\\python.exe");
			if (tempFile.exists()) {
				return tempFile.getAbsolutePath();
			}
			tempFile = new File("C:\\Program Files\\Python27\\python.exe");
			if (tempFile.exists()) {
				return tempFile.getAbsolutePath();
			} else {
				System.out
						.println("Please check your python installation. It should be installed to the standard path...");
				System.exit(1);
			}

		} else {
			System.out.println("Unsupported OS...");
			System.exit(1);
		}
		return "";
	}
	
	public static String getGsutilPath() {
		return "./../../ext/gsutil/gsutil.py";
		//return "C:\\Users\\fenix\\Desktop\\IS_Projekt\\ext\\gsutil\\gsutil.py";
	}
}
