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
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Vector;

import control.FileListHandler;
import control.Main;

/**
 * The Class MainWindow shows the main window of the application.
 */
public class MainWindow extends javax.swing.JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3172688540921699213L;

	/** The window height. */
	private int positionCoordinateX = 400, positionCoordinateY = 400,
			windowWidth = 400, windowHeight = 300;

	// Components
	/** The menu bar. */
	private JMenuBar menuBar;

	/** The table. */
	private JTable table;

	/** The non editable j table. */
	private NonEditableJTable nonEditableJTable;

	/** The popup menu. */
	JPopupMenu popupMenu;

	/** The scroll pane. */
	JScrollPane scrollPane;

	/** The title file. */
	private JMenu titleFile;

	/** The title help. */
	private JMenu titleHelp;

	/** The entry file select. */
	private JMenuItem entryFileSelect;

	/** The entry file close. */
	private JMenuItem entryFileClose;

	/** The entry help help. */
	private JMenuItem entryHelpHelp;

	/** The entry help about. */
	private JMenuItem entryHelpAbout;

	/** The delete entry. */
	private JMenuItem deleteEntry;

	/** The download entry. */
	private JMenuItem downloadEntry;

	/**
	 * Instantiates a new main window.
	 * 
	 * @param title
	 *            the title
	 */
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
		this.setVisible(true);
	}

	/**
	 * Initializes the table.
	 * 
	 * @return the object[][]
	 */
	private Object[][] initTable() {
		int ctr = 0;
		Vector<InformationContainer> fileList = FileListHandler.getInstance()
				.getFilesInBucket();
		Object[][] result = new Object[fileList.size()][];
		ListIterator<InformationContainer> listIterator = fileList
				.listIterator();
		while (listIterator.hasNext()) {
			Object[] temp = new Object[3];
			InformationContainer tempFileElement = listIterator.next();
			temp[0] = tempFileElement.getName();
			temp[1] = tempFileElement.getEncryptedName();
			temp[2] = tempFileElement.getTimestamp();
			result[ctr] = temp;
			ctr++;
		}
		return result;
	}

	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		Object rowData[][] = this.initTable();
		Object columnNames[] = { "Name", "DataKey", "Uploaded" };
		nonEditableJTable = new NonEditableJTable(rowData, columnNames);
		table = new JTable(nonEditableJTable);

		deleteEntry = new JMenuItem("Delete from cloud");
		deleteEntry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (table.getSelectedRow() != -1) {
					String encryptedName = (String) table.getValueAt(row, 1);
					Main.getInstance().toggle_MainWindow_delete(encryptedName);
					popupMenu.setVisible(false);
				}
			}
		});
		downloadEntry = new JMenuItem("Download");
		downloadEntry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (table.getSelectedRow() != -1) {
					String encryptedName = (String) table.getValueAt(row, 1);
					Main.getInstance()
							.toggle_MainWindow_download(encryptedName);
					popupMenu.setVisible(false);
				}
			}
		});

		popupMenu = new JPopupMenu();
		popupMenu.add(downloadEntry);
		popupMenu.add(deleteEntry);

		table.setComponentPopupMenu(popupMenu);

		menuBar = new JMenuBar();
		scrollPane = new JScrollPane(table);

		titleFile = new JMenu("File");
		titleHelp = new JMenu("Help");

		entryFileSelect = new JMenuItem("Select");
		entryFileClose = new JMenuItem("Close");
		entryHelpHelp = new JMenuItem("Help");
		entryHelpAbout = new JMenuItem("About");

		entryFileSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ac) {
				Locale.setDefault(Locale.US);
				JComponent.setDefaultLocale(Locale.US);
				JFileChooser fc = new JFileChooser();
				int status = fc.showOpenDialog(null);
				if (status == JFileChooser.APPROVE_OPTION) {
					try {
						Main.getInstance().toggle_MainWindow_fileSelected(
								fc.getSelectedFile());
					} catch (InvalidKeyException | NoSuchAlgorithmException
							| NoSuchProviderException | NoSuchPaddingException
							| IOException e) {
						e.printStackTrace();
					} catch (InvalidAlgorithmParameterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		this.entryFileClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ac) {
				try {
					Main.getInstance().exit();
				} catch (InvalidKeyException | NoSuchAlgorithmException
						| NoSuchProviderException | NoSuchPaddingException
						| ShortBufferException | IllegalBlockSizeException
						| BadPaddingException
						| InvalidAlgorithmParameterException
						| InterruptedException | IOException e) {
					e.printStackTrace();
				}
			}
		});

		this.entryHelpAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ac) {
				try {
					Main.getInstance().toggle_MainWindow_about();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		this.entryHelpHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ac) {
				try {
					Main.getInstance().toggle_MainWindow_help();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		this.titleFile.add(entryFileSelect);
		this.titleFile.add(entryFileClose);
		this.titleHelp.add(entryHelpHelp);
		this.titleHelp.add(entryHelpAbout);

		this.menuBar.add(titleFile);
		this.menuBar.add(titleHelp);

		this.setJMenuBar(this.menuBar);
	}
}
