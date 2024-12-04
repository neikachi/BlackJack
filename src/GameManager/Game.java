package GameManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import deckManagement.Card;
import deckManagement.DeckCollection;
import network.Message;
import user.Dealer;
import user.Player;

public class Game {
    private static int count = 0;
    private final String id;
    private boolean hasDealer;
    private Dealer dealer;
    private List<Player> players;
    private DeckCollection deckCollection;
    private Status gameStatus;
    private Map<Integer, Player> threadToPlayerMap; // Mapping thread IDs to players

    // Enum for game status
    public enum Status {
        WAITING, IN_PROGRESS, FINISHED
    }

    // Constructor
    public Game() {
        this.id = Integer.toString(count++);
        this.hasDealer = false;
        this.players = new ArrayList<>();
        this.deckCollection = new DeckCollection();
        this.gameStatus = Status.WAITING;
        this.threadToPlayerMap = new HashMap<>();
    }

    // Getters
    public String getGameId() {
        return this.id;
    }

    public boolean getHasDealer() {
        return this.hasDealer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Status getGameStatus() {
        return gameStatus;
    }

    // Setters
    public void setHasDealer(boolean hasDealer) {
        this.hasDealer = hasDealer;
    }

    // Add Dealer
    public void addDealer(Dealer dealer) {
        if (!hasDealer) {
            this.dealer = dealer;
            this.hasDealer = true;
        } else {
            throw new IllegalStateException("Dealer already assigned to this game.");
        }
    }

    // Add or Remove Players
    public void addPlayer(Player player, int threadId) {
        if (gameFull()) {
            throw new IllegalStateException("Game is full.");
        }
        players.add(player);
        threadToPlayerMap.put(threadId, player); // Map the thread ID to the player
    }

    public void removePlayer(Player player) {
        players.remove(player);
        threadToPlayerMap.values().remove(player); // Remove the player from the map
    }

    // Game Full Check
    public boolean gameFull() {
        return hasDealer && players.size() >= 4;
    }

    // Start Game
    public void startGame() {
        deckCollection.shuffleCollection();
        dealer.resetHand();
        for (Player player : players) {
            player.resetHand();
            player.setStatus(Player.Status.ACTIVE);
        }
        gameStatus = Status.IN_PROGRESS;
        System.out.println("Game started! Deck shuffled and ready.");
    }

    // Process Messages
    public Message processGameMessage(Message message, int threadId) {
        if (message.getRole().equals("player")) {
            return this.processPlayerAction(message, threadId);
        } else {
            return this.processDealerAction(message, threadId);
        }
    }

    // Player Actions
    private Message processPlayerAction(Message message, int threadId) {
        Player player = findPlayerByThreadId(threadId);
        if (player != null) {
            String action = message.getContent(); // Assuming action is stored in content
            processPlayerAction(player, action);
            return new Message("action", "player", "Player action" + action + "processed.");
        }
        return new Message("error", "server", "Player not found.");
    }

    private void processPlayerAction(Player player, String action) {
        switch (action.toUpperCase()) {
            case "HIT":
                Card card = deckCollection.dealCard();
                if (card != null) {
                    player.hit(card);
                }
                break;
            case "STAND":
                player.setStatus(Player.Status.STANDING);
                break;
            case "DOUBLE_DOWN":
                if (player.getBalance() >= player.getCurrentBet()) {
                    player.placeBet(player.getCurrentBet()); // Double the bet
                    Card doubleDownCard = deckCollection.dealCard();
                    if (doubleDownCard != null) {
                        player.doubleDown(doubleDownCard);
                    }
                } else {
                    System.out.println("Insufficient funds to double down.");
                }
                break;
            case "SPLIT":
                if (player.canSplit()) {
                    Card firstCard = player.getHand().get(0);
                    Card secondCard = player.getHand().get(1);
                    player.split(firstCard, secondCard);
                    // Deal one card to each split hand
                    player.hitSplitHand(0, deckCollection.dealCard());
                    player.hitSplitHand(1, deckCollection.dealCard());
                } else {
                    System.out.println("Cannot split. First two cards must be identical.");
                }
                break;
            default:
                System.out.println("Invalid action: " + action);
        }
    }

    // Dealer Actions
    private Message processDealerAction(Message message, int threadId) {
    	
        String action = message.getContent();

        if (action.equalsIgnoreCase("dealerHit")) {
            dealer.hit(deckCollection);
            return new Message("dealerAction", "dealer", "Dealer hits and draws a card.");
        } else if (action.equalsIgnoreCase("deal")) {
        	Player player = findPlayerByThreadId(threadId);
            dealCardToPlayer(threadId); // Use the new dealCardToPlayer method
            return new Message("dealerAction", "dealer", "Dealer deals" + dealCardToPlayer(threadId).getContent() + "to " + player);
        }
            
        return new Message("error", "server", "Dealer not found.");
    }
    	
	 // Method to deal a card to a specific player
	 public Message dealCardToPlayer(int threadId) {
	     Player player = findPlayerByThreadId(threadId); // Locate the player by thread ID
	     if (player != null && dealer != null) {
	         dealer.dealCard(player, deckCollection); // Use the Dealer's dealCard method
	         return new Message("dealerAction", "dealer", "Card dealt to player: " + player.getUsername());
	     }
	     return new Message("error", "server", "Dealer or player not found.");
	 }

    // Helper Methods
    private Player findPlayerByThreadId(int threadId) {
        return threadToPlayerMap.get(threadId); // Retrieve player by thread ID
    }

//    // Method to send message objects back to client
//    private void sendMessageToClient(Message message, ObjectOutputStream output) {
//		try {
//			output.writeObject(message);
//			output.flush();
//			
//		} catch (IOException e) {
//			e.getStackTrace();
//		}
//	}
    
    // Reset Game
    public void resetGame() {
        dealer.resetHand();
        for (Player player : players) {
            player.resetHand();
            player.setStatus(Player.Status.ACTIVE);
        }
        deckCollection.shuffleCollection();
        gameStatus = Status.WAITING;
    }
}
