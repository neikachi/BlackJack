package gui;

import javax.swing.*;

import deckManagement.Card;
import network.Client;
import network.Message;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PlayerDealerGUI {
    private JFrame frame;
    private JPanel panel;
    private JLabel playerHandLabel;
    private JLabel dealerHandLabel;
    private ArrayList<JLabel> otherPlayerHandLabels; // For multiple players' hands
    private JButton playerHitButton;
    private JButton dealerHitButton;
    private JButton splitButton;
    private JButton standButton;
    private JButton doubleDownButton;
    private JButton dealButton;
    private Client client; // Use the Client class for networking
    private boolean isDealer;
    
    private ArrayList<Card> playerHand;
    private ArrayList<Card> dealerHand;
    private ArrayList<List<Card>> otherPlayersHands; // To hold hands of other players

    public PlayerDealerGUI(Client client, boolean isDealer) {
        this.client = client;
        this.isDealer = isDealer;
        this.playerHand = new ArrayList<>();
        this.dealerHand = new ArrayList<>();
        this.otherPlayersHands = new ArrayList<>();
        this.otherPlayerHandLabels = new ArrayList<>();

        frame = new JFrame("Blackjack Game");
        panel = new JPanel();
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(new GridLayout(5, 1)); // You can switch to a layout manager for better scaling

        
        // Player Hand
        playerHandLabel = new JLabel("Player's Hand: ");
        panel.add(playerHandLabel);

        // Dealer Hand
        dealerHandLabel = new JLabel("Dealer's Hand: ");
        panel.add(dealerHandLabel);

        // Other Players' Hands
        // Max 4 players (and the dealer)
        for (int i = 0; i < 4; i++) {
            JLabel otherPlayerLabel = new JLabel("Other Player " + (i+1) + "'s Hand: ");
            otherPlayerHandLabels.add(otherPlayerLabel);
            panel.add(otherPlayerLabel);
        }
        
        // Create buttons
        playerHitButton = new JButton("Hit");
        playerHitButton.setBounds(10, 20, 100, 25);
        panel.add(playerHitButton);
        
        splitButton = new JButton("Split");
        splitButton.setBounds(120, 20, 100, 25);
        panel.add(splitButton);

        standButton = new JButton("Stand");
        standButton.setBounds(230, 20, 100, 25);
        panel.add(standButton);

        doubleDownButton = new JButton("Double Down");
        doubleDownButton.setBounds(10, 60, 150, 25);
        panel.add(doubleDownButton);

        // Dealer Buttons
        dealerHitButton = new JButton("Hit (Dealer)");
        dealerHitButton.setBounds(10, 100, 150, 25);
        panel.add(dealerHitButton);

        dealButton = new JButton("Deal");
        dealButton.setBounds(170, 60, 100, 25);
        panel.add(dealButton);

        // Adjust visibility based on role
        playerHitButton.setVisible(!isDealer);
        splitButton.setVisible(!isDealer);
        standButton.setVisible(!isDealer);
        doubleDownButton.setVisible(!isDealer);
        dealButton.setVisible(isDealer);
        dealerHitButton.setVisible(isDealer); 
        
        frame.add(panel);
        frame.setVisible(true);

        
        // Action listeners for the buttons
        playerHitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendActionToServer("HIT");
            }
        });
        
        splitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendActionToServer("SPLIT");
            }
        });

        standButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendActionToServer("STAND");
            }
        });

        doubleDownButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendActionToServer("DOUBLE_DOWN");
            }
        });

        dealButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    client.sendMessageToServer(new Message("deal", "dealer", "Dealer deals card"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Failed to send deal request to the server.");
                }
            }
        });
        
        dealerHitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    client.sendMessageToServer(new Message("dealerHit", "dealer", "Dealer hits"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Failed to send dealer hit request to the server.");
                }
            }
        });

        // Start a background thread to listen for server updates
        new Thread(() -> listenForServerUpdates()).start();
    }

    private void sendActionToServer(String action) {
        try {
            client.sendMessageToServer(new Message("action", action, action), null);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to send action to the server.");
        }
    }

    public void listenForServerUpdates() {
        try {
            while (true) {
                Message message = client.receiveMessageFromServer();
                if (message != null) {
                    if (message.getType().equals("updateHand")) {
                        // Get the content (string representation of the hand)
                        String handString = (String) message.getContent();
                        
                        // Parse the string into a List<Card>
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error receiving updates from the server.");
        }
    }

    private void handleServerUpdate(Message message) {
        SwingUtilities.invokeLater(() -> {
            String content = message.getContent();
            JOptionPane.showMessageDialog(frame, content, "Game Update", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", 7777); // Connect using robust client logic
            new PlayerDealerGUI(client, false); // 'false' for player role
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}