package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameGUI extends JFrame {
    private JPanel dealerPanel;
    private JPanel playerPanel;
    private JLabel statusLabel;

    public GameGUI() {
        setTitle("Blackjack Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Dealer section
        dealerPanel = new JPanel();
        dealerPanel.setBorder(BorderFactory.createTitledBorder("Dealer's Cards"));
        dealerPanel.setLayout(new FlowLayout());
        add(dealerPanel, BorderLayout.NORTH);

        // Player section
        playerPanel = new JPanel();
        playerPanel.setBorder(BorderFactory.createTitledBorder("Your Cards"));
        playerPanel.setLayout(new FlowLayout());
        add(playerPanel, BorderLayout.SOUTH);

        // Center area for status or messages
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        statusLabel = new JLabel("Waiting for game to start...", SwingConstants.CENTER);
        centerPanel.add(statusLabel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void updateDealerCards(List<String> cards) {
        SwingUtilities.invokeLater(() -> {
            dealerPanel.removeAll();
            for (String card : cards) {
                dealerPanel.add(new JLabel(card));
            }
            dealerPanel.revalidate();
            dealerPanel.repaint();
        });
    }

    public void updatePlayerCards(List<String> cards) {
        SwingUtilities.invokeLater(() -> {
            playerPanel.removeAll();
            for (String card : cards) {
                playerPanel.add(new JLabel(card));
            }
            playerPanel.revalidate();
            playerPanel.repaint();
        });
    }

    public void updateStatus(String status) {
        SwingUtilities.invokeLater(() -> statusLabel.setText(status));
    }
}

