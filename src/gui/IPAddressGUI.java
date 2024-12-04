package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import network.Message;

public class IPAddressGUI {
		private JFrame frame;
		private JPanel panel;
		
	
		public IPAddressGUI() {
			
		}
		
		public String output() {
			final String[] res = {null};
		    
		    frame = new JFrame("Connect to Server");
		    frame.setLocationRelativeTo(null); // Center the frame
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.setSize(400, 200);
		    
		    panel = new JPanel();
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