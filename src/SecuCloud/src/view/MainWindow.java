package view;

import javax.swing.*;

import model.container.InformationContainer;

import java.awt.*;
import java.awt.event.ActionEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.ListIterator;

import control.Main;

public class MainWindow extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3172688540921699213L;
	// private Toolkit tk;
	private int positionCoordinateX = 400, positionCoordinateY = 400,
			windowWidth = 400, windowHeight = 300;
	// Components
	private JMenuBar menuBar;
	private JTable table;
	JScrollPane scrollPane;

	private JMenu titleFile;
	private JMenu titleHelp;

	private JMenuItem entryFileSelect;
	private JMenuItem entryFileEmpty;
	private JMenuItem entryHelpHelp;
	private JMenuItem entryHelpInfo;
	private JMenuItem entryHelpAbout;

	public MainWindow(String title) {
		/*
		 * ToDO!! for positioning in middle of the screen tk
		 * =Toolkit.getDefaultToolkit();
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
		this.add(scrollPane, BorderLayout.CENTER);

		setVisible(true);
	}

	private Object[][] initTable(){
		int ctr = 0;
		ArrayList<InformationContainer> fileList = Main.getInstance().getFileList();
		Object[][] result = new Object[fileList.size()][];
		ListIterator<InformationContainer> listIterator = fileList.listIterator();
        while (listIterator.hasNext()) {
            Object[] temp = new Object[4];
            InformationContainer tempFileElement =  listIterator.next();
            temp[0] = tempFileElement.getPlainName();
            temp[1] = tempFileElement.getEncryptedName();
            temp[2] = tempFileElement.getTimestamp();
            temp[3] = new File(tempFileElement.getLocalEncryptedFileLocation()).length();
            result[ctr] = temp;
            ctr++;
        }
		return result;
	}
	
	private void initComponents() {
		Object rowData[][] = this.initTable();
		Object columnNames[] = { "Name", "DataKey", "Uploaded", "FileSize" };

		this.table = new JTable(rowData, columnNames);
		this.menuBar = new JMenuBar();
		scrollPane = new JScrollPane(table);

		this.titleFile = new JMenu("File");
		this.titleHelp = new JMenu("About");

		this.entryFileSelect = new JMenuItem("Select");
		this.entryFileEmpty = new JMenuItem("Empty");
		this.entryHelpHelp = new JMenuItem("Help");
		this.entryHelpInfo = new JMenuItem("Info");
		this.entryHelpAbout = new JMenuItem("About");

		this.entryFileSelect
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent ac) {
						JFileChooser fc = new JFileChooser();
						fc.showOpenDialog(null);
						Main.getInstance().toggle_MainWindow_fileSelected(
								fc.getSelectedFile());
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
