package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConnectionHandler implements Runnable{
	private Socket connection;
	private ConcurrentLinkedQueue messages;
	
	public ConnectionHandler(Socket currConnection) {
		this.connection = currConnection;
		this.messages = new ConcurrentLinkedQueue();
	}
	
	@Override
	public void run() {
		try {
				ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
				ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
				
				while (true) {
					Object clientObj = input.readObject();
					// create a message object to pass information
					
					// check to see if the incoming message is for a login.
						// if it is not then send them back a message indicating they need to login.
					
					// iterate over the list of game instances and try to find one for the player
						// check the size of each game
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
}
