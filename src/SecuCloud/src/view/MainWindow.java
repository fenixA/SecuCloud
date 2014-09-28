package view;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.swing.*;

import model.InformationContainer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ListIterator;
import java.util.Vector;

import control.FileListHandler;
import control.Main;

public class MainWindow extends javax.swing.JFrame {
	private static final long serialVersionUID = 3172688540921699213L;
	private int positionCoordinateX = 400, positionCoordinateY = 400,
			windowWidth = 400, windowHeight = 300;

	// Components
	private JMenuBar menuBar;
	private JTable table;
	private NonEditableJTable nonEditableJTable;
	JPopupMenu popupMenu;
	JScrollPane scrollPane;

	private JMenu titleFile;
	private JMenu titleHelp;

	private JMenuItem entryFileSelect;
	private JMenuItem entryFileClose;
	private JMenuItem entryHelpHelp;
	private JMenuItem entryHelpInfo;
	private JMenuItem entryHelpAbout;
	private JMenuItem deleteEntry;

	private JButton selectButton;

	public MainWindow(String title) {
		this.setTitle(title);
		this.setBounds(positionCoordinateX, positionCoordinateY, windowWidth,
				windowHeight);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					Main.getInstance().exit();
				} catch (InterruptedException | InvalidKeyException
						| NoSuchAlgorithmException | NoSuchProviderException
						| NoSuchPaddingException | ShortBufferException
						| IllegalBlockSizeException | BadPaddingException
						| InvalidAlgorithmParameterException | IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		this.initComponents();
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(selectButton, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	private Object[][] initTable() {
		int ctr = 0;
		Vector<InformationContainer> fileList = FileListHandler.getInstance()
				.getFileList();
		Object[][] result = new Object[fileList.size()][];
		ListIterator<InformationContainer> listIterator = fileList
				.listIterator();
		while (listIterator.hasNext()) {
			Object[] temp = new Object[4];
			InformationContainer tempFileElement = listIterator.next();
			temp[0] = tempFileElement.getName();
			temp[1] = tempFileElement.getEncryptedName();
			temp[2] = tempFileElement.getTimestamp();
			temp[3] = new File(tempFileElement.getLocalEncryptedLocation())
					.length();
			result[ctr] = temp;
			ctr++;
		}
		return result;
	}

	private void initComponents() {
		Object rowData[][] = this.initTable();
		Object columnNames[] = { "Name", "DataKey", "Uploaded", "FileSize" };
		nonEditableJTable = new NonEditableJTable(rowData, columnNames);
		this.table = new JTable(nonEditableJTable);
		
		popupMenu = new JPopupMenu();
		deleteEntry = new JMenuItem("Delete");
		deleteEntry.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               System.out.println("test");
            }
        });
        popupMenu.add(deleteEntry);
        table.setComponentPopupMenu(popupMenu);
		this.menuBar = new JMenuBar();
		scrollPane = new JScrollPane(table);

		this.titleFile = new JMenu("File");
		this.titleHelp = new JMenu("About");

		this.entryFileSelect = new JMenuItem("Select");
		this.entryFileClose = new JMenuItem("Close");
		this.entryHelpHelp = new JMenuItem("Help");
		this.entryHelpInfo = new JMenuItem("Info");
		this.entryHelpAbout = new JMenuItem("About");

		this.entryFileSelect
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent ac) {
						JFileChooser fc = new JFileChooser();
						fc.showOpenDialog(null);
						try {
							Main.getInstance().toggle_MainWindow_fileSelected(
									fc.getSelectedFile());
						} catch (InvalidKeyException | NoSuchAlgorithmException
								| NoSuchProviderException
								| NoSuchPaddingException | IOException e) {
							e.printStackTrace();
						}
					}
				});

		this.entryFileClose
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent ac) {
						try {
							Main.getInstance().exit();
						} catch (InvalidKeyException | NoSuchAlgorithmException
								| NoSuchProviderException
								| NoSuchPaddingException | ShortBufferException
								| IllegalBlockSizeException
								| BadPaddingException
								| InvalidAlgorithmParameterException
								| InterruptedException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

		this.titleFile.add(entryFileSelect);
		this.titleFile.add(entryFileClose);
		this.titleHelp.add(entryHelpHelp);
		this.titleHelp.add(entryHelpInfo);
		this.titleHelp.add(entryHelpAbout);

		this.menuBar.add(titleFile);
		this.menuBar.add(titleHelp);

		this.setJMenuBar(this.menuBar);

		this.selectButton = new JButton("Select");
	}
}
