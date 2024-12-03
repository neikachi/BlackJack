package network;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Client {
	
	public static void sendMessageToServer(Message guiMsg, ObjectOutputStream output) {
		try {
			output.writeObject(guiMsg);
			output.flush();
			
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

	public static void main(String[] args) throws ClassNotFoundException {
		// TODO Auto-generated method stub
//		String ipAddress;
//		Scanner sc = new Scanner(System.in);
//		
//		System.out.println("Enter ip address to connect to: ");
//		String host = sc.next();
		String host = IPAddressGUI();
		
		try (Socket socket = new Socket(host, 7777)) {
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			
//			sendMessageToServer(new Message("login", "player", "c.mill3", "password1234!", "content"), output);
//			output.writeObject(new Message("login", "dealer", "testingPlayer1", "player123", "content"));
//			output.flush();
			
			while (true) {
				// create serverRes object that reads from server
				Object serverResponse = input.readObject();
				Message serverMessage = (Message) serverResponse;
				System.out.println(serverMessage.getContent());
				// update GUI here
				
//				sendMessageToServer(new Message("login", "dealer", "testingPlayer1", "player123", "content"), output);
				
				// use sendMessageToServer method down here
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
}	































