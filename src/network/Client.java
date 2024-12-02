

package network;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Client {
	
	private Socket socket;
	private ObjectInputStream inputStream;	
	private ObjectOutputStream outputStream;

    // Constructor for initializing connection
    public Client(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }
	
	public void sendMessageToServer(Message guiMsg) {
		try {
			outputStream.writeObject(guiMsg);
			outputStream.flush();
			
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

	public static void main(String[] args) throws ClassNotFoundException {
		String host = IPAddressGUI();
	
    // Receives a message from the server
    public Message receiveMessageFromServer() throws IOException, ClassNotFoundException {
        Object response = inputStream.readObject();
        if (response instanceof Message) {
            return (Message) response;
        }
        return null;
    }
	
	public void disconnect() {
		
	}

	public static void main(String[] args) throws ClassNotFoundException {

		String ipAddress;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter ip address to connect to: ");
		String host = sc.next();
		
		try (Socket socket = new Socket(host, 7777);
				ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			) {
			
			registerGUI(output);
			client.sendMessageToServer(new Message("login", "player", "c.mill3", "password1234!", "content"));
//			output.writeObject(new Message("login", "dealer", "testingPlayer1", "player123", "content"));
//			output.flush();
			
			while (true) {
                try {
                    Object serverResponse = input.readObject();
                    Message serverMessage = (Message) serverResponse;
                    System.out.println(serverMessage.getContent());
                    // updateGUI(serverMessage); // Replace with your GUI update logic
                    if (serverMessage.getContent().equals("Registration successful.")) {
  
                    	loginGUI(output);
                    }
      
                } catch (IOException | ClassNotFoundException e) {
                    // Handle network or data format exceptions
                    e.printStackTrace();
                    // Inform user about connection issues (optional)
                    // Consider retrying or exiting
                    break;
                }
            }
			
			
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
	
	private static String IPAddressGUI() {
	    final String[] res = {null};
	    
	    JFrame frame = new JFrame("Connect to Server");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(400, 200);
	    
	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(3, 1));
	    
	    JLabel label = new JLabel("Enter IP Address to Connect:");
	    label.setHorizontalAlignment(SwingConstants.CENTER);
	    panel.add(label);
	    
	    JTextField ipField = new JTextField();
	    panel.add(ipField);
	    
	    JButton connectButton = new JButton("Connect");
	    panel.add(connectButton);
	    
	    frame.add(panel);
	    
	    // Block execution using a lock
	    final Object lock = new Object();
	    
	    connectButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            String ipAddress = ipField.getText().trim();
	            if (!ipAddress.isEmpty()) {
	                res[0] = ipAddress;
	                JOptionPane.showMessageDialog(frame, "Connecting to: " + ipAddress);
	                synchronized (lock) {
	                    lock.notify();
	                }
	                frame.dispose();
	            } else {
	                JOptionPane.showMessageDialog(frame, "IP Address cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    });
	    
	    frame.setVisible(true);
	    
	    synchronized (lock) {
	        try {
	            lock.wait();
	        } catch (InterruptedException ex) {
	            ex.printStackTrace();
	        }
	    }
	    
	    return res[0];
	}
	
	private static void registerGUI(ObjectOutputStream output) {
		JFrame frame = new JFrame("Register");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,300);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5,1));
		
		JLabel roleLabel = new JLabel("role: ");
		JTextField roleField = new JTextField();
		panel.add(roleLabel);
		panel.add(roleField);
		
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
		panel.add(buttonPanel);
		
		frame.add(panel);
		frame.setVisible(true);
		
		loginButton.addActionListener(e -> {
			loginGUI(output);
			frame.dispose();
		});
	    
		  registerButton.addActionListener(e -> {
	            String role = roleField.getText().trim();
	            String username = usernameField.getText().trim();
	            String password = new String(passwordField.getPassword()).trim();
	            if (!username.isEmpty() && !password.isEmpty()) {
	                sendMessageToServer(new Message("register", role, username, password, "Attempting to register"), output);
	          
	                frame.dispose();
	            }
	       });
	}
	
	private static void loginGUI(ObjectOutputStream output) {
		JFrame frame = new JFrame("Login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,400);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5,1));
		
		JLabel roleLabel = new JLabel("role: ");
		JTextField roleField = new JTextField();
		panel.add(roleLabel);
		panel.add(roleField);
		
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
		panel.add(buttonPanel);
		
	
		
	
		frame.add(panel);
		frame.setVisible(true);
		
		loginButton.addActionListener(e -> {
			String role = roleField.getText().trim();
			String username = usernameField.getText().trim();
			String password = new String(passwordField.getPassword()).trim();
			
			if (!username.isEmpty() && !password.isEmpty()) {
				sendMessageToServer(new Message("login", role, username, password, "attempting to login"), output);
				frame.dispose();
			}
		});
		
		registerButton.addActionListener(e -> {
			registerGUI(output);
			frame.dispose();
		});
	}
}	

//134.154.32.163

















































