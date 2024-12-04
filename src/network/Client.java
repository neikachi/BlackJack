package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import javax.swing.JOptionPane;

import gui.RegisterGUI;
import gui.GameGUI;
import gui.IPAddressGUI;
import gui.LoginGUI;

public class Client {
	 static GameGUI gameGUI;
	
	public static void sendMessageToServer(Message guiMsg, ObjectOutputStream output) {
		try {
			output.writeObject(guiMsg);
			output.flush();
			
		} catch (IOException e) {
			e.getStackTrace();
		}
	}

	public static void main(String[] args) throws ClassNotFoundException {

		 String host = new IPAddressGUI().output();
		 
		
		try (Socket socket = new Socket(host, 7777);
				ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			) {
			
			new RegisterGUI(output).outputGUI();
			
			while (true) {
                try {
                    Object serverResponse = input.readObject();
                    Message serverMessage = (Message) serverResponse;
                    System.out.println(serverMessage.getContent());
                    // updateGUI(serverMessage); // Replace with your GUI update logic
                    if (serverMessage.getContent().equals("Registration successful.")) {
                    	JOptionPane.showMessageDialog(null, serverMessage.getContent());
                    	new LoginGUI(output).outputGUI();
                    }
                    
                    if (serverMessage.getContent().equals("login successful")) {
                    	// show the player gui
                    	JOptionPane.showMessageDialog(null, serverMessage.getContent());
                    	boolean isDealer = false;
                    	
                    	if (serverMessage.getRole().equals("dealer")) {
                    		isDealer = true;
                    	}
                    	
                    	gameGUI = new GameGUI();
                    	
                    } else if (serverMessage.getContent().equals("update_dealer_cards")) {
                        List<String> dealerCards = serverMessage.getCards(); // Assuming this is provided
                        gameGUI.updateDealerCards(dealerCards);
                    } else if (serverMessage.getContent().equals("update_player_cards")) {
                        List<String> playerCards = serverMessage.getCards(); // Assuming this is provided
                        gameGUI.updatePlayerCards(playerCards);
                    } else if (serverMessage.getContent().equals("update_status")) {
                        String status = serverMessage.getType(); // Assuming this is provided
                        gameGUI.updateStatus(status);
                    }
                    
                    
                    
      
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
			
			
		} catch (IOException e) {
			e.getStackTrace();
		}
	}
}
//134.154.32.163









