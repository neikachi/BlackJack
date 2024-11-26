package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {
	private final static int PORT = 7777;
	private ConcurrentHashMap<String, Socket> activeGames;
	
	public MainServer() {
		this.activeGames = new ConcurrentHashMap<String, Socket>();
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
