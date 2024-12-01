package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import GameManager.Game;

public class MainServer {
	ExecutorService pool = Executors.newFixedThreadPool(100);
	private final static int PORT = 7777;
	private ConcurrentHashMap<String, Game> activeGames;
	
	public MainServer() {
		this.activeGames = new ConcurrentHashMap<String, Game>();
	}
	
	public void start() throws IOException{
		try (ServerSocket server = new ServerSocket(PORT)) {
			
			while (true) {
				Socket connection = server.accept();
				ConnectionHandler currConnection = new ConnectionHandler(connection, this);
				
				pool.submit(currConnection);
			}
			
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
	
	public Game findAvailableGame() {
		for (Game game: this.activeGames.values()) {
			if (!(game.gameFull())) {
				return game;
			}
		}
		
		return null;
	}
	
	public Game createGame() {
		Game newGame = new Game();
		
		this.addGameToCollection(newGame.getGameId(), newGame);
		
		newGame.setHasDealer(true);
		
		return newGame;
	}
	
	private void addGameToCollection(String gameId, Game newGame) {
		this.activeGames.put(gameId, newGame);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			MainServer server = new MainServer();
			server.start();
			
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

}
