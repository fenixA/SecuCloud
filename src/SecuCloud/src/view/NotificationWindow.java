package view;

import javax.swing.JOptionPane;

import control.Main;

public class NotificationWindow {

	public void helpNotification() {

		String message = "Helping Shit";
		String title = "About";
		JOptionPane.showMessageDialog(null, message, title,
				JOptionPane.PLAIN_MESSAGE);
	}

	public void aboutNotification() {

		String message = Main.getInstance().getSoftwareName() + "\n\n" + "Blah Blah";
		String title = "About";
		JOptionPane.showMessageDialog (null,
				message,
				title,
				JOptionPane.PLAIN_MESSAGE);
	}
}
