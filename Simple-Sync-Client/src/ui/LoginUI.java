package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import core.*;

public class LoginUI extends Thread {

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

	public LoginUI() {
		run("", false);
	}

	public LoginUI(String host) {
		run(host, true);
	}

	private void run(String host, boolean hostLocked) {
		frame = new JFrame("Simple Sync Login");

		txtHost = new JTextField(host);
		txtUsername = new JTextField();
		txtPassword = new JPasswordField();
		btnLogin = new JButton("Login");

		lblUsername = new JLabel("Username: ");
		lblPassword = new JLabel("Password: ");
		lblHost = new JLabel("Host: ");

		if (hostLocked) {
			txtHost.setEditable(false);
		} else {
			txtHost.setEditable(true);
		}
		
		frame.addKeyListener(new KeyPressListener());
		
		btnLogin.addActionListener(new ButtonClickListener());
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
	}

	private class ButtonClickListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			String command = e.getActionCommand();
			if (command.equals("Login")) {
				// btnLogin.setText("Logging in...");
				Main.setLoginInfo(txtHost.getText(), txtUsername.getText(), txtPassword.getText());

			}

		}
	}
	
	private class KeyPressListener implements KeyListener{
		public void keyPressed(KeyEvent e){
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_ENTER) {
				// btnLogin.setText("Logging in...");
				Main.setLoginInfo(txtHost.getText(), txtUsername.getText(), txtPassword.getText());

			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}


	}

}
