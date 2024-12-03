package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	private Socket socket;
	private ObjectInputStream inputStream;	
	private ObjectOutputStream outputStream;

    // Constructor for initializing connection
    public Client(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
    }
	
	public void sendMessageToServer(Message guiMsg) {
		try {
			outputStream.writeObject(guiMsg);
			outputStream.flush();
			
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
	
    // Receives a message from the server
    public Message receiveMessageFromServer() throws IOException, ClassNotFoundException {
        Object response = inputStream.readObject();
        if (response instanceof Message) {
            return (Message) response;
        }
        return null;
    }
	
	public void disconnect() {
		
	}

	public static void main(String[] args) throws ClassNotFoundException {

		String ipAddress;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter ip address to connect to: ");
		String host = sc.next();
		
		try (Socket socket = new Socket(host, 7777)) {
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			
			client.sendMessageToServer(new Message("login", "player", "c.mill3", "password1234!", "content"));
//			output.writeObject(new Message("login", "dealer", "testingPlayer1", "player123", "content"));
//			output.flush();
			
			while (true) {
				// create serverRes object that reads from server
				Object serverResponse = input.readObject();
				Message serverMessage = (Message) serverResponse;
				System.out.println(serverMessage.getContent());
				// update GUI here
				
//				sendMessageToServer(new Message("login", "dealer", "testingPlayer1", "player123", "content"), output);
				
				// use sendMessageToServer method down here
			}
			
			
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

}
