package user;

import java.util.ArrayList;
import java.util.List;

import deckManagement.Card;

public class Player extends User {
    // Fields
    private ArrayList<ArrayList<Card>> splitHands;  // Additional hand for split
    private double currentBet;
    private Status status;


    // Constructor
    public Player(String username, String password, int accBalance, AccountType accountType) {
        super(username, password, accBalance, accountType);
        this.hand = new ArrayList<>();
        this.splitHands = null;  // Initialized during a split
        this.currentBet = 0.0;
        this.status = status.ACTIVE;
    }
    
    // comment

    
    // Methods
    
 // Method to place a bet
    public void placeBet(double amount) {
        if (amount > 0 && amount <= balance) {
            currentBet = amount;
            balance -= amount;  // Deduct the bet from the balance
            System.out.println("Bet placed: $" + amount);
        } else {
            System.out.println("Invalid bet amount or insufficient balance.");
        }
    }

    // Getter for the current bet
    public double getCurrentBet() {
        return currentBet;
    }
    
    public ArrayList<ArrayList<Card>> getSplitHands() {
        return splitHands;
    }
    
    public void hit(Card card) {
        hand.add(card);
        System.out.println("Player hits and receives: " + card);
    }

    public void stand() {
        this.status = status.STANDING;
        System.out.println("Player stands.");
    }

    public void doubleDown(Card card) {
        if (currentBet <= balance) {
            balance -= currentBet;
            currentBet *= 2;
            hit(card);
            stand();  // Must stand after doubling down
            System.out.println("Player doubled down. Bet is now: " + currentBet);
        } else {
            System.out.println("Not enough balance to double down.");
        }
    }

    public boolean canSplit() {
        if (hand.size() == 2 && hand.get(0).getRank() == hand.get(1).getRank()) {
        	return true;
        }
        else {
        	return false;
        }
    }
    
    public void split(Card firstCard, Card secondCard) {
        if (canSplit()) {
        	// Initialize splitHands if it's not already initialized
        	splitHands = new ArrayList<>();

        	// Create a new hand by removing a card from the original hand and adding it to the new hand
        	ArrayList<Card> newHand = new ArrayList<>();
        	newHand.add(hand.remove(1));  // Remove the second card from the original hand
        	splitHands.add(newHand);  // Add the new hand (list of cards) to splitHands

        	// Create a second hand with the second card and add it to splitHands
        	ArrayList<Card> secondHand = new ArrayList<>();
        	secondHand.add(secondCard);  // Add the second card to a new hand
        	splitHands.add(secondHand);  // Add the new hand (list of cards) to splitHands            System.out.println("Player split their hand.");
        } else {
            System.out.println("Cannot split: Cards must be identical.");
        }
    }
    
    public void hitSplitHand(int handIndex, Card card) {
    	if (handIndex >= 0 && handIndex < splitHands.size()) {
    		splitHands.get(handIndex).add(card);
    	}
    }
}