package view;

//import java.awt.MenuBar;
//import java.awt.Toolkit;
//import java.awt.Dimension;


//import javax.swing.JFrame;
//import javax.swing.JMenu;
//import javax.swing.JMenuBar;
//import javax.swing.JMenuItem;
import javax.swing.*;

import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.File;

import control.Main;

public class MainWindow extends javax.swing.JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3172688540921699213L;
	// private Toolkit tk;
	private int positionCoordinateX = 400, positionCoordinateY = 400, windowWidth = 400,
			windowHeight = 300;
	// Components
	private JMenuBar menuBar;
	
	private JMenu titleFile;
	private JMenu titleHelp;
	
	private JMenuItem entryFileSelect;
	private JMenuItem entryFileEmpty;
	private JMenuItem entryHelpHelp;
	private JMenuItem entryHelpInfo;
	private JMenuItem entryHelpAbout;
	
	public String tempString;
	
	
	public MainWindow(String title) {
		/*
		 * ToDO!! for positioning in middle of the screen
		 * tk =Toolkit.getDefaultToolkit();
		 * 
		 * Dimension d = tk.getScreenSize();
		 * 
		 * 
		 * positionCoordinateX = (int)(d.getWidth() - windowWidth /2);
		 * positionCoordinateY = (int)(d.getHeight() - windowHeight /2);
		 */

		setTitle(title);
		setBounds(positionCoordinateX, positionCoordinateY, windowWidth,
				windowHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.initComponents();
		
		setVisible(true);
	}

	private void initComponents() {
		this.menuBar = new JMenuBar();
		
		this.titleFile = new JMenu("File");
		this.titleHelp = new JMenu("About");
		
		this.entryFileSelect = new JMenuItem("Select");
		this.entryFileEmpty = new JMenuItem("Empty");
		this.entryHelpHelp = new JMenuItem("Help");
		this.entryHelpInfo = new JMenuItem("Info");
		this.entryHelpAbout = new JMenuItem("About");
		
		this.entryFileSelect.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent ac){
				JFileChooser fc = new JFileChooser();
                fc.showOpenDialog(null);
                Main.getInstance().toggle_AboutWindow_fileSelected(fc.getSelectedFile());
                
			}
		});
		
		this.titleFile.add(entryFileSelect);
		this.titleFile.add(entryFileEmpty);
		this.titleHelp.add(entryHelpHelp);
		this.titleHelp.add(entryHelpInfo);
		this.titleHelp.add(entryHelpAbout);
		
		this.menuBar.add(titleFile);
		this.menuBar.add(titleHelp);
		
		this.setJMenuBar(this.menuBar);
	}
}
