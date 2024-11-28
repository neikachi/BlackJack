package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	
	public void sendMessageToServer(Message guiMsg, ObjectOutputStream output) {
		try {
			output.writeObject(guiMsg);
			output.flush();
			
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
	
	public void disconnect() {
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try (Socket socket = new Socket("localhost", 7777)) {
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			
//			Message guiMsg = new Message();
			
			while (true) {
				// create serverRes object that reads from server
				// update GUI here
			}
			
			
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

}
