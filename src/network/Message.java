package network;

import java.io.Serializable;

public class Message implements Serializable{
	private static int count = 0;
	private final int id;
	protected String type;
	protected String role;
	protected String username;
	protected String password;
	protected String text;
	
	public Message() {
		this.type = "Undefined";
		this.role = "Undefined";
		this.username = "Undefined";
		this.password = "Undefined";
		this.text = "Undefined";
		this.id = count ++;
	}
	
	public Message(String type, String role, String username, String password, String text) {
		this.type = type;
		this.role = role;
		this.username = username;
		this.password = password;
		this.text = text;
		this.id = count ++;
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
	
	public String getText() {
		return this.text;
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
	
	public void setPassword() {
		this.password = password;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
}
