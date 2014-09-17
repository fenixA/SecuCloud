package control;

public class SystemInformationCollector {
	public enum OsID {
		linux, windows, other
	}

	public static OsID getOperationSystem() {
		String system = System.getProperty("os.name").toLowerCase();
		if (system.contains("linux")) {
			return OsID.linux;
		} else if (system.contains("windows")) {
			return OsID.windows;
		} else {
			return OsID.other;
		}
	}
}
