package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

import GameManager.Game;

public class ConnectionHandler implements Runnable{
	private static int count = 0;
	private final int id;
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
		this.id = count ++;
	}
	
	@Override
	public void run() {
		try (	ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
				ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
			) {
				while (true) {
					Object clientObj = input.readObject();
					System.out.println("client" + clientObj.toString());
					Message msg = (Message) clientObj;
//					if (clientObj instanceof Message) {
//					messages.add((Message) clientObj);
//					}
					
//					Message nextMsg = messages.poll();
					
//					if (msg.getType().equals("login")) {
//						output.writeObject("hello from the server");
//					}
					
//					if (msg != null) {
					this.processClientMessage(msg, output, input);
//					}
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
	
	private Integer getThreadId() {
		return this.id;
	}
	
	private String getGameInstanceId () {
		return this.gameInstanceId;
	}
	
	private boolean getIsLoggedIn() {
		return this.isLoggedIn;
	}
	
	private void setGameInstanceId(String gameInstanceId) {
		this.gameInstanceId = gameInstanceId;
	}
	
	private void setIsLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	
	///////// helper methods
	
	private Game getAssignedGame() {
		return this.server.getActiveGameById(this.getGameInstanceId());
	}
	
	private void processClientMessage(Message msg, ObjectOutputStream output, ObjectInputStream input) throws IOException {
	    System.out.println("Processing message from client: " + msg.getContent());
	    if (!this.getIsLoggedIn()) {
	        if (this.server.credentialsInDatabase(msg.getUsername(), msg.getPassword())) {
	            this.loginUserToGame(msg, output, input);
	        } else {
	            this.server.registerUserInDatabase(msg.getRole(), msg.getUsername(), msg.getPassword());
	            output.writeObject(new Message("login", "server", "Registration successful."));
	            output.flush();
	        }
	        this.setIsLoggedIn(true);
	        return;
	    }

	    Game currGame = this.getAssignedGame();
	    if (currGame != null) {
	        Message res = currGame.processGameMessage(msg, this.getThreadId());
	        System.out.println("Game response: " + res.getContent());
	        output.writeObject(res);
	        output.flush();
	    } else {
	        System.out.println("No game assigned to client.");
	        output.writeObject(new Message("error", "server", "No game found."));
	        output.flush();
	    }
	}
	
	private void loginUserToGame(Message nextMsg, ObjectOutputStream output, ObjectInputStream input) throws IOException {
		if (nextMsg.getRole().equals("dealer")) {
			Game newGame = this.server.createGame();
			this.setGameInstanceId(newGame.getGameId());
//			Dealer dealer = Dealer(nextMsg.getUsername(), output, input);
//			newGame.addDealer(dealer);
			output.writeObject(new Message("login", "server", "login successful...adding to game as dealer"));
			output.flush();
		} else {
			Game existingGame = this.server.findAvailableGame();
			
			if (existingGame != null) {
				this.setGameInstanceId(existingGame.getGameId());
//				Player player = Player(nextMsg.getUsername(), output, input);
//				existingGame.addPlayer(player);
				output.writeObject(new Message("login", "server", "login successful...adding to game as player"));
				output.flush();
			} else {
				output.writeObject(new Message("login", "server", "No available games found, please try again later"));
				output.flush();
			}
		}
	}

}
