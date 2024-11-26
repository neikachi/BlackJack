package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {
	private final static int PORT = 7777;
	private static int
	private ConcurrentHashMap<String, Game> activeGames;
	
	public MainServer() {
		this.activeGames = new ConcurrentHashMap<String, Game>();
	}
	
	public void start() throws IOException{
		ExecutorService pool = Executors.newFixedThreadPool(100);
		
		try (ServerSocket server = new ServerSocket(PORT)) {
			
			while (true) {
				Socket connection = server.accept();
//				String connectionId = getConnectionId(connection);
				
//				clientServers.put(connectionId, connection);
				
				ConnectionHandler currConnection = new ConnectionHandler(connection);
				
				pool.submit(currConnection);
			}
			
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
	
	public Optional<Game> findAvailableGame() {
		for (Game game: this.activeGames.values()) {
			if (!(game.gameFull())) {
				return Optional.of(game);
			}
		}
		
		return Optional.empty();
	}
	
	public void createGame() {
		Game newGame = new Game();
		
		this.addGameToCollection(newGame.getGameId(), newGame);
	}
	
	private void addGameToCollection(String gameId, Game newGame) {
		this.activeGames.put(gameId, newGame);
	}
	
	
	
	
	
//	private String getConnectionId(Socket connection) {
//		return connection.getInetAddress().toString() + ":" + connection.getPort();
//	}
	
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
