package GameManager;

import java.util.ArrayList;
import java.util.List;

import network.ConnectionHandler;

public class Game {
	private static int count = 0;
	private final String id;
	private boolean hasDealer;
	private List<Player> players;
	
	public Game() {
		this.id = Integer.toString(count ++);
		this.hasDealer = false;
		this.players = new ArrayList<>();
	}
	
	public Boolean gameFull() {
		if (hasDealer && this.players.size() >= 4) {
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
	
	
}

