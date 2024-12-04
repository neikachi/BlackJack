package network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import deckManagement.Card;

public class Message implements Serializable{
	private static int count = 0;
	private final int id;
	protected String type;
	protected String role;
	protected String username;
	protected String password;
	protected String content;
	protected ArrayList<Card> cards;
	
	public Message() {
		this.type = "Undefined";
		this.role = "Undefined";
		this.username = "Undefined";
		this.password = "Undefined";
		this.content = "Undefined";
		this.id = count ++;
	}
	
	public Message(String type, String role, String content) {
		this.type = type;
		this.role = role;
		this.username = "undefined";
		this.password = "undefined";
		this.content = content;
		this.id = count ++;
	}
	
	public Message(String type, String role, String username, String password, String content) {
		this.type = type;
		this.role = role;
		this.username = username;
		this.password = password;
		this.content = content;
		this.id = count ++;
	}
	
	public Message(String type, String role, String content, ArrayList<Card> cards) {
		this.type = type;
		this.role = role;
		this.username = "undefined";
		this.password = "undefined";
		this.content = content;
		this.id = count ++;
		this.cards = cards;
		this.cards = new ArrayList<Card>();
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getRole() {
		return this.role;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public List<String> getCards() {
        List<String> cardStrings = new ArrayList<>();
        for (Card card : cards) {
            cardStrings.add(card.toString()); // Assuming `Card` has a meaningful `toString()` method
        }
        return cardStrings;
    }
}
