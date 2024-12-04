package gui;

import java.awt.GridLayout;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import network.Client;
import network.Message;

public class LoginGUI {
	ObjectOutputStream output;
	JFrame frame;
	JComboBox<String> roleSelector;
	
	public LoginGUI(ObjectOutputStream output) {
		this.output = output;
	}
	
	public void outputGUI() {
		frame = new JFrame("Login");
	    frame.setSize(400, 300);
	    frame.setLocationRelativeTo(null); // This centers the frame in the middle of the screen
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(6, 1)); // Adjust grid for response area
		
		
		
		roleSelector = new JComboBox<>(new String[]{"Player", "Dealer"});
        roleSelector.setBounds(100, 80, 165, 25);
		
		JLabel userLabel = new JLabel("Username: ");
		JTextField usernameField = new JTextField();
		panel.add(userLabel);
		panel.add(usernameField);
		
		JLabel passLabel = new JLabel("Password:");
	    JPasswordField passwordField = new JPasswordField();
	    panel.add(passLabel);
	    panel.add(passwordField);
		
		JPanel buttonPanel = new JPanel();
		JButton loginButton = new JButton("login");
		JButton registerButton = new JButton("Register");
		
		buttonPanel.add(loginButton);
		buttonPanel.add(registerButton);
		panel.add(roleSelector);
		panel.add(buttonPanel);
		
	
		
	
		frame.add(panel);
		frame.setVisible(true);
		
		loginButton.addActionListener(e -> {
			String role = roleSelector.getSelectedItem().toString();
			String username = usernameField.getText().trim();
			String password = new String(passwordField.getPassword()).trim();
			
			if (!username.isEmpty() && !password.isEmpty()) {
				Client.sendMessageToServer(new Message("login", role, username, password, "attempting to login"), output);
				frame.dispose();
			}
		});
		
		registerButton.addActionListener(e -> {
			new RegisterGUI(output);
			frame.dispose();
		});
	}
}
