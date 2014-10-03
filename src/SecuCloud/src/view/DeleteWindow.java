package view;

import javax.swing.JOptionPane;

public class DeleteWindow {
	
	public boolean handleQuit(){
		int choice = JOptionPane.showOptionDialog(null,
				"Datensatz nicht vorhanden, löschen?",
				"Datensatz nicht vorhanden",
				JOptionPane.YES_NO_OPTION,
      			JOptionPane.QUESTION_MESSAGE,
      			null, null, null);
			 
		if (choice == JOptionPane.YES_OPTION){
			return true;
		} return false;
	}	
}
