package network;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import GameManager.Game;

public class MainServer {
	ExecutorService pool = Executors.newFixedThreadPool(100);
	private final static int PORT = 7777;
	private String sourceName = "src/resources/credentials.txt";
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
	
	public boolean credentialsInDatabase(String username, String password) {
		File inFile = new File(this.sourceName);
		
		if (!inFile.exists()) {
			System.out.println("File not found: " + this.sourceName);
			return false;
		}
		
		try {
			
			Scanner scanner = new Scanner(inFile);
			
			while (scanner.hasNextLine()) {
				String data = scanner.nextLine().replaceAll("\\s", "");
				String[] info = data.split(",");
				String currUsername = info[1];
				String currPassword = info[2];
				
				if (currUsername.equals(username) && currPassword.equals(password)) {
					return true;
				}
			}
			
			scanner.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void registerUserInDatabase (String role, String username, String password) {
		
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



























