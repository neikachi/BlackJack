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
    private gameStatus gameStatus;
    private Map<Integer, Player> threadToPlayerMap; // Mapping thread IDs to players
    private int threadId;

    // Enum for game status
    public enum gameStatus {
        WAITING, IN_PROGRESS, FINISHED
    }

    // Constructor
    public Game() {
        this.id = Integer.toString(count++);
        this.hasDealer = true;
        this.players = new ArrayList<>();
        this.deckCollection = new DeckCollection();
        this.gameStatus = gameStatus.WAITING;
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

    public gameStatus getGameStatus() {
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
        gameStatus = gameStatus.IN_PROGRESS;
        System.out.println("Game started! Deck shuffled and ready.");
    }

    // Process Messages
    public Message processGameMessage(Message message, int threadId) {
    	
    	this.threadId = threadId;
    	
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
            return processGameMessage(new Message("action", "player", action), threadId);
        }
        return processGameMessage(new Message("error", "server", "Player not found."), threadId);
    }

    private Message processPlayerAction(Player player, String action) {
  
        switch (action.toUpperCase()) {
            case "HIT":
                // Create a message to notify the dealer to deal a card
                return processGameMessage(new Message("hitRequest", "game", "Player " + player.getUsername() + " requests a hit."), this.threadId);
            case "STAND":
                return processGameMessage(new Message("playerAction", "player", action), this.threadId);
            case "DOUBLE_DOWN":
                if (player.getBalance() >= player.getCurrentBet()) {
                    player.placeBet(player.getCurrentBet()); // Double the bet
                    Card doubleDownCard = deckCollection.dealCard();
                    if (doubleDownCard != null) {
                        player.doubleDown(doubleDownCard);
                        return null;
                    }
                } else {
                    System.out.println("Insufficient funds to double down.");
                    return null;
                }
            case "SPLIT":
                if (player.canSplit()) {
                    Card firstCard = player.getHand().get(0);
                    Card secondCard = player.getHand().get(1);
                    player.split(firstCard, secondCard);
                    // Deal one card to each split hand
                    processGameMessage( new Message("playerAction", "player", "update_player_cards"), this.threadId);
                    processGameMessage( new Message("playerAction", "player", "update_player_cards"), this.threadId);
                    player.hitSplitHand(0, deckCollection.dealCard());
                    player.hitSplitHand(1, deckCollection.dealCard());
                } else {
                	return processGameMessage(new Message("error", "server", "Cannot split. First two cards must be identical."), this.threadId);
                }
            default:
            	return processGameMessage(new Message("error", "server", "Invalid action: " + action), this.threadId);
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
            return processGameMessage(new Message("dealerAction", "dealer", "Dealer deals" + dealCardToPlayer(threadId).getContent() + "to " + player), threadId);
        }
            
        return processGameMessage(new Message("error", "server", "Dealer not found."), threadId);
    }
    	
	 // Method to deal a card to a specific player
	 public Message dealCardToPlayer(int threadId) {
	     Player player = findPlayerByThreadId(threadId); // Locate the player by thread ID
	     if (player != null && dealer != null) {
	         dealer.dealCard(player, deckCollection); // Use the Dealer's dealCard method
	         return processGameMessage(new Message("dealerAction", "dealer", "Card dealt to player: " + player.getUsername()), threadId);
	     }
	     return processGameMessage(new Message("error", "server", "Dealer or player not found."), threadId);
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
