package GameManager;

import java.util.ArrayList;
import java.util.List;

import network.ConnectionHandler;
import network.Message;

public class Game {
	private static int count = 0;
	private final String id;
	private boolean hasDealer;
	private List<User> users;
	
	public Game() {
		this.id = Integer.toString(count ++);
		this.hasDealer = false;
		this.users = new ArrayList<>();
	}
	
	public Boolean gameFull() {
		if (hasDealer && this.users.size() >= 4) {
			return false;
		}
		return true;
	}
	
	public String getGameId() {
		return this.id;
	}
	
	public boolean getHasDealer() {
		return this.hasDealer;
	}
	
	public void setHasDealer(boolean hasDealer) {
		this.hasDealer = hasDealer;
	}
	
	public void addPlayer(Player player) {
		
	}
	
	public void addDealer(Dealer dealer) {
		
	}
	
	public Message processGameMessage(Message message, int threadId) {
		if (message.getRole().equals("player")) {
			// pass the message to be processed in a player action method
			return this.processPlayerAction(message, threadId);
		} else {
			// pass the message to be processed in a dealer action method
			return this.processDealerAction(message, threadId);
		}
	}
	
	private Message processPlayerAction(Message message, int threadId) {
		// updatae gamestate in there as well as process message
	}
	
	private Message processDealerAction(Message message, int threadId) {
		// update game state
	}
	
	
}

