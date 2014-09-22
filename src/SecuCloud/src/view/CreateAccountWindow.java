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

public class CreateAccountWindow extends javax.swing.JFrame {
	private static final long serialVersionUID = -7334076302460580234L;

	private JLabel nameLabel;
	private JLabel firstPasswordLabel;
	private JLabel secondPasswordLabel;

	private JTextField nameTextField;
	private JPasswordField firstPasswordTextField;
	private JPasswordField secondPasswordTextField;

	private JButton okButton;

	public CreateAccountWindow() {
		setTitle("Please create new user!");

		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(4, 2));

		this.initComponents();
		this.setResizable(false);
		setVisible(true);
	}

	private void initComponents() {
		this.nameLabel = new JLabel("Name: ", SwingConstants.LEFT);
		this.firstPasswordLabel = new JLabel("Password: ", SwingConstants.LEFT);
		this.secondPasswordLabel = new JLabel("Retype password: ",
				SwingConstants.LEFT);
		this.nameTextField = new JTextField();
		this.firstPasswordTextField = new JPasswordField();
		this.secondPasswordTextField = new JPasswordField();

		this.okButton = new JButton("OK");
		this.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String firstPassword = new String(firstPasswordTextField
						.getPassword());
				String secondPassword = new String(secondPasswordTextField
						.getPassword());
				if (firstPassword.equals(secondPassword)) {
					Main.getInstance().toggle_CreateAccountWindow_okButton(
							nameTextField.getText(), firstPassword);
				} else {
					System.out.println("No matching passwords!");
				}
			}
		});

		this.add(nameLabel);
		this.add(nameTextField);

		this.add(firstPasswordLabel);
		this.add(firstPasswordTextField);

		this.add(secondPasswordLabel);
		this.add(secondPasswordTextField);

		this.add(okButton);

		nameTextField.setText("fenix");
		firstPasswordTextField.setText("183461");
		secondPasswordTextField.setText("183461");

		this.pack();
	}
}
