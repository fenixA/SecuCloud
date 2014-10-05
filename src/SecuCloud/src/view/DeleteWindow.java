package view;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

// TODO: Auto-generated Javadoc
/**
 * The Class DeleteWindow.
 */
public class DeleteWindow {

	/**
	 * Handle input.
	 *
	 * @param fileName the file name
	 * @param encryptedName the encrypted name
	 * @return true, if successful
	 */
	public boolean HandleInput(String fileName, String encryptedName) {

		UIManager.put("OptionPane.yesButtonText", "Yes");
		UIManager.put("OptionPane.noButtonText", "No");

		int choice = JOptionPane.showOptionDialog(null,
				"Data on Cloud not available:\n\n" + fileName + " ("
						+ encryptedName + ")\n" + "\nDelete from filelist?",
				"Cloud not synchronous!", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, null, null);

		if (choice == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}
}
