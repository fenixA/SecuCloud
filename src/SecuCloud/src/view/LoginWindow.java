package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
				String password = new String(passwordTextField
						.getPassword());
				Main.getInstance().toggle_LoginWindow_okButton(
						nameTextField.getText(), password);
			}
		});

		this.add(nameLabel);
		this.add(nameTextField);

		this.add(passwordLabel);
		this.add(passwordTextField);

		this.add(okButton);

		nameTextField.setText("fenix");
		passwordTextField.setText("183461");

		this.pack();
	}
}
