package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import control.Main;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginWindow.
 */
public class LoginWindow extends JFrame {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5433548214205850844L;
	
	/** The name label. */
	private JLabel nameLabel;
	
	/** The password label. */
	private JLabel passwordLabel;

	/** The name text field. */
	private JTextField nameTextField;
	
	/** The password text field. */
	private JPasswordField passwordTextField;

	/** The ok button. */
	private JButton okButton;
	
	/** The create button. */
	private JButton createButton;

	/**
	 * Instantiates a new login window.
	 */
	public LoginWindow() {
		setTitle("Login");

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(3, 2));
		this.setPreferredSize(new Dimension(300, 125));

		this.initComponents();
		this.setResizable(false);
		setVisible(true);
	}

	/**
	 * Inits the components.
	 */
	private void initComponents() {
		this.nameLabel = new JLabel("Name: ", SwingConstants.LEFT);
		this.passwordLabel = new JLabel("Password: ", SwingConstants.LEFT);
		this.nameTextField = new JTextField();
		this.passwordTextField = new JPasswordField();

		this.okButton = new JButton("OK");
		this.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = new String(passwordTextField.getPassword());
				try {
					Main.getInstance().toggle_LoginWindow_okButton(
							nameTextField.getText(), password);
				} catch (IOException | InterruptedException
						| InvalidKeyException | NoSuchAlgorithmException
						| NoSuchProviderException | NoSuchPaddingException
						| ShortBufferException | IllegalBlockSizeException
						| BadPaddingException
						| InvalidAlgorithmParameterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		this.createButton = new JButton("Create");
		this.createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getInstance().toggle_LoginWindow_createButton();
			}
		});

		this.add(nameLabel);
		this.add(nameTextField);

		this.add(passwordLabel);
		this.add(passwordTextField);

		this.add(createButton);
		this.add(okButton);

		nameTextField.setText("fenix");
		passwordTextField.setText("183461");

		this.pack();
	}
}
