package network;

import java.io.Serializable;

public class Message implements Serializable{
	private static int count = 0;
	private final int id;
	protected String type;
	protected String role;
	protected String text;
	
	public Message() {
		this.type = "Undefined";
		this.role = "Undefined";
		this.text = "Undefined";
		this.id = count ++;
	}
	
	public Message(String type, String role, String text) {
		this.type = type;
		this.role = role;
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
	
	public void setText(String text) {
		this.text = text;
	}
	
}
