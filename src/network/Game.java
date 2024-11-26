package network;

public class Game {
	private static int count = 0;
	private final String id;
	
	public Game() {
		this.id = Integer.toString(count ++);
	}

	public void addPlayer() {
		
	}
	
	public Boolean gameFull() {
		
	}
	
	public String getGameId() {
		return this.id;
	}
}
