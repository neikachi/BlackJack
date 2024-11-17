package network;

import java.io.IOException;
import java.net.Socket;

public class ConnectionHandler implements Runnable{
	private Socket connection;
	
	public ConnectionHandler(Socket currConnection) {
		this.connection = currConnection;
	}
	
	@Override
	public void run() {
		try {
			
		} catch (InterruptedException e ) {
			Thread.currentThread().interrupt();
		} finally {
			try {
				connection.close();
			} catch (IOException e) {
				e.getStackTrace();
			}
		}
	}
}
