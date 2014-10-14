package view;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * The Class DeleteWindow shows a delete dialog.
 */
public class DeleteWindow {

	/**
	 * Handle input.
	 * 
	 * @param fileName
	 *            the file name
	 * @param encryptedName
	 *            the encrypted file name
	 * @return true, if successful
	 */
	public boolean HandleInput(String fileName, String encryptedName, int x) {

		UIManager.put("OptionPane.yesButtonText", "Yes");
		UIManager.put("OptionPane.noButtonText", "No");
		String tmp1 = null;
		String tmp2 = null;
		String tmp3 = null;

		switch (x) {
		case 0:
			tmp1 = "Data on Cloud not available:";
			tmp2 = "Delete from filelist?";
			tmp3 = "Cloud not synchronous!";
			break;
		case 1:
			tmp1 = "Are you sure to delete:";
			tmp2 = "from cloud?";
			tmp3 = "Delete file";
			break;
		default:
			break;
		}

		int choice = JOptionPane.showOptionDialog(null, tmp1 + "\n\n"
				+ fileName + " (" + encryptedName + ")\n" + "\n" + tmp2, tmp3,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				null, null);

		if (choice == JOptionPane.YES_OPTION) {
			return true;
		}
		return false;
	}
}
