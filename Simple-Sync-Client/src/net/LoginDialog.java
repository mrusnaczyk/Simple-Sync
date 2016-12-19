package net;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.Settings;


public class LoginDialog extends Authenticator {
	private static final int WINDOW_WIDTH = 330;
	private static final int WINDOW_HEIGHT = 210;

	private JFrame frame;
	private JButton btnLogin;
	private JTextField txtUsername;
	private JTextField txtHost;
	private JPasswordField txtPassword;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JLabel lblHost;

	private PasswordAuthentication response = null;

	public LoginDialog() {
		this("");
		this.show();
	}

	public LoginDialog(String host) {
		frame = new JFrame("Simple Sync Login");

		txtHost = new JTextField(host);
		txtUsername = new JTextField();
		txtPassword = new JPasswordField();
		btnLogin = new JButton("Login");

		lblUsername = new JLabel("Username: ");
		lblPassword = new JLabel("Password: ");
		lblHost = new JLabel("Host: ");

		if (!host.isEmpty()) {
			txtHost.setEditable(false);
		} else {
			txtHost.setEditable(true);
		}

		btnLogin.addActionListener(new submitClick());
		btnLogin.setActionCommand("Login");

		txtUsername.setSize(220, 25);
		txtPassword.setSize(220, 25);
		txtHost.setSize(220, 25);
		btnLogin.setSize(300, 35);

		lblUsername.setSize(80, 50);
		lblPassword.setSize(80, 55);
		lblHost.setSize(80, 50);

		txtHost.setLocation(80, 15);
		txtUsername.setLocation(80, 55);
		txtPassword.setLocation(80, 95);
		btnLogin.setLocation(15, 135);

		lblHost.setLocation(10, 0);
		lblUsername.setLocation(10, 40);
		lblPassword.setLocation(10, 80);

		frame.add(lblUsername);
		frame.add(lblPassword);
		frame.add(lblHost);
		frame.add(txtUsername);
		frame.add(txtPassword);
		frame.add(txtHost);
		frame.add(btnLogin);

		frame.setLayout(null);
		frame.setResizable(false);
		frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("lock.png"));
		frame.setBackground(Color.red);
		frame.getContentPane().setBackground(new Color(236, 240, 241));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	public void show() {
		frame.setVisible(true);
	}

	public PasswordAuthentication getPasswordAuthentication() {
		return this.response;
	}

	private class submitClick implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			btnLogin.setText("Logging in...");
			response = new PasswordAuthentication(txtUsername.getText(), txtPassword.getPassword());
			txtPassword.setText("");
			Settings.domain = txtHost.getText();

			synchronized (main.Main.syncObject) {
				main.Main.syncObject.notify();
			}
			frame.dispose();
		}
	}

}
