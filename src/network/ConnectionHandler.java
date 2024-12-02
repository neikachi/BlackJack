package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import GameManager.Game;

public class ConnectionHandler implements Runnable{
	private Socket connection;
	private MainServer server;
	private ConcurrentLinkedQueue<Message> messages;
	private boolean isLoggedIn;
	private String gameInstanceId;
	
	public ConnectionHandler(Socket currConnection, MainServer server) {
		this.connection = currConnection;
		this.server = server;
		this.messages = new ConcurrentLinkedQueue();
		this.isLoggedIn = false;
	}
	
	@Override
	public void run() {
		try (
				ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
				ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
			) {
				while (true) {
					Object clientObj = input.readObject();
					
					if (clientObj instanceof Message) {
						messages.add((Message) clientObj);
					}
					
					Message nextMsg = messages.poll();
					
					if (nextMsg != null) {
						this.processClientMessage(nextMsg, output);
					}
				}
			
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			try {
				connection.close();
			} catch (IOException e) {
				e.getStackTrace();
			}
		}
	}
	
	private void processClientMessage(Message nextMsg, ObjectOutputStream output) throws IOException {
		if (!isLoggedIn) {
			if (this.server.verifyCredentials(nextMsg)) {
				this.loginUser(nextMsg, output);
			}
		}
	}
	
	private void loginUser(Message nextMsg, ObjectOutputStream output) throws IOException {
		this.isLoggedIn = true;
		
		if (nextMsg.getRole().equals("dealer")) {
			Game newGame = this.server.createGame();
			this.gameInstanceId = newGame.getGameId();
			output.writeObject(new Message("login", "server", "login successful...adding to game as dealer"));
		} else {
			Game existingGame = this.server.findAvailableGame();
			
			if (existingGame != null) {
				this.gameInstanceId = existingGame.getGameId();
				output.writeObject(new Message("login", "server", "login successful...adding to game as player"));
			}
		}
	}

}
