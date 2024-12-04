package user;

import java.util.ArrayList;
import java.util.List;

import deckManagement.Card;

public abstract class User {

    // Fields
    protected String username;
    protected String password;
    protected int accBalance; 
    protected AccountType accountType;
    protected ArrayList<Card> hand;  // Shared functionality for managing cards
    
    // Enum for account type
    public enum AccountType {
        PLAYER, DEALER
    }

    // Enum for player status
    public enum Status {
        ACTIVE, STANDING, BUSTED
    }

    protected Status status;
    
    // Constructor
    public User(String username, String password, int accBalance, AccountType accountType) {
        this.username = username;
        this.password = password;
        this.accBalance = accBalance;
        this.accountType = accountType;
        this.hand = new ArrayList<>();  // Initialize the hand here
        this.status = Status.ACTIVE;   // Default status
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

    public int getBalance() {
        return accBalance;
    }

    public void setBalance(int balance) {
        this.accBalance = balance;
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

    // Calculate the total value of the user's hand
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

    // Reset the user's hand to an empty state
    public void resetHand() {
        hand.clear();
    }

    // Add a card to the user's hand
    public void addCardToHand(Card card) {
        hand.add(card);
    }

    // Print the user's current hand
    public void printHand() {
        System.out.println("Current hand for " + username + ":");
        for (Card card : hand) {
            System.out.println(card);
        }
    }
}
