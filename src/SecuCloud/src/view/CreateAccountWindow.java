package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class CreateAccountWindow extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7334076302460580234L;
	private int positionCoordinateX = 400, positionCoordinateY = 400,
			windowWidth = 200, windowHeight = 150;
	
	public CreateAccountWindow(){
		setTitle("Create Account");
		setBounds(positionCoordinateX, positionCoordinateY, windowWidth,
				windowHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);
	}
}
