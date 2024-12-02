package network;

import java.io.File;
import java.io.FileWriter;
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
	private ConcurrentHashMap<String, String[]> databaseCredentials = new ConcurrentHashMap<>();
	
	public MainServer() {
		this.loadDatabaseCredentials();
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
	
	private void loadDatabaseCredentials() {
		File inFile = new File(this.sourceName);
		
		if (!inFile.exists()) {
			System.out.println("File not found: " + this.sourceName);
			return;
		}
		
		try {
			
			Scanner scanner = new Scanner(inFile);
			
			while (scanner.hasNextLine()) {
				String data = scanner.nextLine().replaceAll("\\s", "");
				String[] info = data.split(",");
				String role = info[0];
				String username = info[1];
				String password = info[2];
				
				this.databaseCredentials.put(username, new String[] {role, password});
			}
			
			scanner.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String outputFormatedCredentials() {
		StringBuilder res = new StringBuilder();
		
		this.databaseCredentials.forEach((username, values) -> {
			String role = values[0];
			String password = values[1];
			res.append(role).append(",").append(username).append(",").append(password).append("\n");
		});
		
		return res.toString();
	}
	
	private void saveDatabaseCredentials() {
		File file = new File(this.sourceName);
		
		try {
			FileWriter myWriter = new FileWriter(this.sourceName);
			myWriter.write(this.outputFormatedCredentials());
			myWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean credentialsInDatabase(String username, String password) {
		String[] userData = this.databaseCredentials.get(username);
		
		if (userData != null && userData[1].equals(password)) {
			return true;
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



























