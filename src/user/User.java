package user;

import java.util.ArrayList;
import java.util.List;

import deckManagement.Card;

public class User {

    // Fields
    private String username;
    private String password;
    protected int accBalance; 
    private AccountType accountType;
    private Status status;
    protected ArrayList<Card> hand;  // Added hand to manage shared functionality
    
    // Enum for account type
    public enum AccountType {
        PLAYER, DEALER
    }

    // Enum for player status
    public enum Status {
        ACTIVE, STANDING, BUSTED
    }

    
    // Constructor
    public User(String username, String password, int accBalance, AccountType accountType) {
        this.username = username;
        this.password = password;
        this.accBalance = accBalance;
        this.accountType = accountType;
        this.hand = new ArrayList<>();  // Initialize the hand here
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return accBalance;
    }

    public void setBalance(double balance) {
        this.accBalance = accBalance;
    }

    public AccountType getAccountType() {
        return accountType;
    }
  
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Status getStatus() {
    	return status;
    }
    
    public void setStatus(Status status) {
    	this.status = status;
    }
    
    public List<Card> getHand() {
        return hand;
    }
    
    // Methods

    public int getHandValue() {
        int value = 0;
        int aceCount = 0;

        for (Card card : hand) {
            value += card.getValue();
            if (card.getRank() == Card.Rank.ACE) {
                aceCount++;
            }
        }

        // Adjust ACE values (if any) to be 1 if the hand value is over 21
        while (value > 21 && aceCount > 0) {
            value -= 10; // Change ACE from 11 to 1
            aceCount--;
        }

        return value;
    }

    public void resetHand() {
        hand.clear();
    }
}