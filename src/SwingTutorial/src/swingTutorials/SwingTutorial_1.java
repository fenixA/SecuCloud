package swingTutorials;

import java.awt.Toolkit;
//import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class SwingTutorial_1 extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4862611498603965107L;
	private Toolkit tk;
	
	private int positionCoordinateX, positionCoordinateY, windowWidth=400, windowHeight=300;
	
	
	// Components
	//private 
	
	
	public SwingTutorial_1() {
		tk = Toolkit.getDefaultToolkit();
		
		/* ToDO!! for positioning in middle of the screeen
		Dimension d = tk.getScreenSize();
		
		
		positionCoordinateX = (int)(d.getWidth() - windowWidth /2);
		positionCoordinateY = (int)(d.getHeight() - windowHeight /2);
		*/
		
		
		setTitle("SwingTutorial_1");
		setBounds(positionCoordinateX, positionCoordinateY, windowWidth, windowHeight);
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingTutorial_1 sT = new SwingTutorial_1();
		System.out.println("Testing");
	}
}
