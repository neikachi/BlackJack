package gui;

import javax.swing.*;

import network.Client;
import user.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginRegisterGUI {
    private JFrame frame;
    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel messageLabel;
    private JComboBox<String> roleSelector;

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
        
        // Role Selector
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(10, 80, 80, 25);
        panel.add(roleLabel);

        roleSelector = new JComboBox<>(new String[]{"Player", "Dealer"});
        roleSelector.setBounds(100, 80, 165, 25);
        panel.add(roleSelector);

        // Login and Register Buttons
        loginButton = new JButton("Login");
        loginButton.setBounds(10, 160, 80, 25);
        panel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setBounds(180, 160, 90, 25);
        panel.add(registerButton);

        // Message Label for error or success feedback
        messageLabel = new JLabel("");
        messageLabel.setBounds(10, 110, 250, 25);
        panel.add(messageLabel);

        // Add the panel to the frame
        frame.add(panel);

        // Add action listeners for login and registration
     // Add action listeners for login and registration
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    messageLabel.setText("Please enter both username and password.");
                } else {
                    String userRole = login(username, password); // Backend determines the role
                    boolean isDealer = false;

                    if (userRole != null) {
                        if (userRole.equals("Dealer")) {
                            isDealer = true;
                        } else if (userRole.equals("Player")) {
                            isDealer = false;
                        }

                        try {
                            // Create the Client object
                            Client client = new Client("localhost", 7777); // Update "localhost" to the actual IP address
                            messageLabel.setText("Login successful!");
                            
                            // Pass Client and role to the PlayerDealerGUI
                            new PlayerDealerGUI(client, isDealer); 
                            frame.dispose(); // Close the login window
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            messageLabel.setText("Failed to connect to the server.");
                        }
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
                String selectedRole = roleSelector.getSelectedItem().toString(); // Get selected role
                boolean isDealer = selectedRole.equals("Dealer");

                if (username.isEmpty() || password.isEmpty()) {
                    messageLabel.setText("Please enter both username and password.");
                } else {
                    if (register(username, password, selectedRole)) {
                        try {
                            // Create the Client object
                            Client client = new Client("localhost", 7777); // Update "localhost" to the actual IP address
                            messageLabel.setText("Registration successful!");

                            // Pass Client and role to the PlayerDealerGUI
                            new PlayerDealerGUI(client, isDealer); 
                            frame.dispose(); // Close the registration window
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            messageLabel.setText("Failed to connect to the server.");
                        }
                    } else {
                        messageLabel.setText("Username already taken.");
                    }
                }
            }
        });

        frame.setVisible(true); // Now visible after components are added
    }

    private String login(String username, String password) {
        // Replace this with actual backend logic to validate the user
        if (username.equals("player1") && password.equals("password123")) {
            return "Player"; // Return the user's role
        } else if (username.equals("dealer1") && password.equals("dealer123")) {
            return "Dealer";
        }
        return null; // Invalid credentials
    }

    private boolean register(String username, String password, String role) {
        // Replace this with actual backend logic to register the user
        System.out.println("Registering " + role + " with username: " + username);
        // Simulate successful registration
        return true;
    }

    public static void main(String[] args) {
        new LoginRegisterGUI(); // Launch the login screen
    }
}