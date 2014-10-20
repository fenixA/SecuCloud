package control;

/**
 * The Class SystemInformationCollector collects information about the system
 * the application is running on.
 */
public class SystemInformationCollector {

	/**
	 * The Enum OsID. represents the different operating systems.
	 */
	public enum OsID {
		linux, windows, other
	}

	/**
	 * Gets the operation system.
	 * 
	 * @return the operation system
	 */
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
