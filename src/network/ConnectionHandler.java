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
		this.messages = new ConcurrentLinkedQueue<>();
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
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String getGameInstanceId () {
		return this.gameInstanceId;
	}
	
	private void setGameInstanceId(String gameInstanceId) {
		this.gameInstanceId = gameInstanceId;
	}
	
	private void processClientMessage(Message msg, ObjectOutputStream output) throws IOException {
		if (!isLoggedIn) {
			if (this.server.credentialsInDatabase(msg.getUsername(), msg.getPassword())) {
				this.loginUserToGame(msg, output);
			} else {
				this.server.registerUserInDatabase(msg.getRole(), msg.getUsername(), msg.getPassword());
			}
		}
	}
	
	private void loginUserToGame(Message nextMsg, ObjectOutputStream output) throws IOException {
		this.isLoggedIn = true;
		
		if (nextMsg.getRole().equals("dealer")) {
			Game newGame = this.server.createGame();
			this.setGameInstanceId(newGame.getGameId());
			output.writeObject(new Message("login", "server", "login successful...adding to game as dealer"));
		} else {
			Game existingGame = this.server.findAvailableGame();
			
			if (existingGame != null) {
				this.setGameInstanceId(existingGame.getGameId());
				output.writeObject(new Message("login", "server", "login successful...adding to game as player"));
			} else {
				output.writeObject(new Message("login", "server", "No available games found, please try again later"));
			}
		}
	}

}
