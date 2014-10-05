package view;

import javax.swing.JOptionPane;

import control.Main;

// TODO: Auto-generated Javadoc
/**
 * The Class NotificationWindow.
 */
public class NotificationWindow {

	/**
	 * Help notification.
	 */
	public void helpNotification() {

		String message = "1. Upload File:\n"
				+ "File > Select > choose file from local directory\n"
				+ "The chosen file will be locally encrypted and uploaded to the Cloud.\n\n"
				+ "2. Download File:\n"
				+ "Right click on a listed file > Download\n"
				+ "The chosen file will be downloaded and instantly decrypted to an local directory.\n\n"
				+ "3. Delete File:\n"
				+ "Right click on a listed file > Delete\n"
				+ "The chosen file will be deleted from the Cloud and the stored Key thrown away.\n\n"
				+ "4. Synchronizing Delete-PopUp:\n"
				+ "At each start the software will check if there are files in the Cloud which have\n"
				+ "no entry on the local Information Container.\n"
				+ "This occurs if you delete files from Cloud manually.\n"
				+ " ";
		String title = "Help";
		JOptionPane.showMessageDialog(null, message, title,
				JOptionPane.QUESTION_MESSAGE);
	}

	/**
	 * About notification.
	 */
	public void aboutNotification() {

		String message = Main.getInstance().getSoftwareName()
				+ "\n\nVersion: "
				+ Main.getInstance().getSoftwareName()
				+ " Release 1\n"
				+ "Build id: 20141007-SS14\n\n"
				+ "Copyright Felix Friedrich and Michael Holzwarth, 2014.\n"
				+ "IS-Project in cooperation with Aalen University of Applied Sciences.\n\n"
				+ "This product includes software developed by Google Inc., \n"
				+ "Python Software Foundation and Oracle Corporation.\n"
				+ " ";
		String title = "About";
		JOptionPane.showMessageDialog(null, message, title,
				JOptionPane.INFORMATION_MESSAGE);
	}
}
