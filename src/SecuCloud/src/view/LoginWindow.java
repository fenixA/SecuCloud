package view;

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

public class LoginWindow extends JFrame {
	private static final long serialVersionUID = -5433548214205850844L;
	private JLabel nameLabel;
	private JLabel passwordLabel;

	private JTextField nameTextField;
	private JPasswordField passwordTextField;

	private JButton okButton;
	private JButton createButton;

	public LoginWindow() {
		setTitle("Login");

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(4, 2));

		this.initComponents();
		this.setResizable(false);
		setVisible(true);
	}

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
