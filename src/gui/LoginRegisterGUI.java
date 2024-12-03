package gui;

import javax.swing.*;
import user.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginRegisterGUI {
    private JFrame frame;
    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel messageLabel;

    private User user;  // Reference to the User object for login and registration logic

    public LoginRegisterGUI() {
        frame = new JFrame("Blackjack Login");
        panel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 250);
        
        panel.setLayout(null);

        // Username Label and Field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 165, 25);
        panel.add(usernameField);

        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);

        // Login and Register Buttons
        loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setBounds(180, 80, 90, 25);
        panel.add(registerButton);

        // Message Label for error or success feedback
        messageLabel = new JLabel("");
        messageLabel.setBounds(10, 110, 250, 25);
        panel.add(messageLabel);

        // Add the panel to the frame
        frame.add(panel);

        // Add action listeners for login and registration
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                if (username.isEmpty() || password.isEmpty()) {
                    messageLabel.setText("Please enter both username and password.");
                } else {
                    // Call User.login() method
                    if (login(username, password)) {
                        messageLabel.setText("Login successful!");
                        // Proceed to the next screen (game or main menu)
                        new PlayerDealerGUI(null, null, false); 
                        frame.dispose(); // Close the login window
                    } else {
                        messageLabel.setText("Invalid username or password.");
                    }
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                
                if (username.isEmpty() || password.isEmpty()) {
                    messageLabel.setText("Please enter both username and password.");
                } else {
                    // Call User.register() method
                    if (register(username, password)) {
                        messageLabel.setText("Registration successful!");
                        // Proceed to the next screen (game or main menu)
                        new PlayerDealerGUI(null, null, false); 
                        frame.dispose(); // Close the login window
                    } else {
                        messageLabel.setText("Username already taken.");
                    }
                }
            }
        });

        frame.setVisible(true); // Now visible after components are added
    }

    // Dummy login method (replace with actual login logic)
    private boolean login(String username, String password) {
        // Replace this with actual login logic (e.g., check credentials against a database)
        return username.equals("player1") && password.equals("password123");
    }

    // Dummy register method (replace with actual registration logic)
    private boolean register(String username, String password) {
        // Replace this with actual registration logic (e.g., store the user details)
        // For now, we'll just simulate success
        return true;
    }

    public static void main(String[] args) {
        new LoginRegisterGUI(); // Launch the login screen
    }
}