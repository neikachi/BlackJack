package GameManager;

public class Game {
	private static int count = 0;
	private final String id;
	private boolean hasDealer;
	
	public Game() {
		this.id = Integer.toString(count ++);
		this.hasDealer = false;
	}

	public void addPlayer() {
		
	}
	
	public Boolean gameFull() {
		return false;
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
}

