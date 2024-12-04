package user;

import java.util.ArrayList;
import java.util.List;

import deckManagement.Card;

public class Player extends User {

    // Fields
	
    private ArrayList<ArrayList<Card>> splitHands;  // Additional hand for split
    private double currentBet;
    private Status status;
    private int accBalance;

 
    // Constructor
    public Player(String username, String password, int accBalance, AccountType accountType) {
        super(username, password, accBalance, accountType);
        this.hand = new ArrayList<>();
        this.splitHands = null;  // Initialized during a split
        this.currentBet = 0.0;
        this.setAccBalance(accBalance);
        this.status = Status.ACTIVE;
    }
    
    // comment

    public void setStatus(Status playerStatus) {
        this.status = playerStatus;
    }

    public Status getStatus() {
        return status;
    }
    //method
    
 // Method to place a bet
    public void placeBet(double amount) {
        System.out.println("Attempting to place bet: $" + amount);
        System.out.println("Current balance before placing bet: $" + getAccBalance());
        if (amount > 0 && amount <= getAccBalance()) {
            currentBet = amount;
            setAccBalance((int) (getAccBalance() - amount));
            System.out.println("Bet placed: $" + amount);
            System.out.println("Current balance after placing bet: $" + getAccBalance());
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
        System.out.println("Current status before standing: " + this.status);
        this.status = Status.STANDING; // Update the status to STANDING
        System.out.println("Player stands. Status is now: " + this.status);
    }


    public void doubleDown(Card card) {
        if (currentBet <= getAccBalance()) {
            setAccBalance((int) (getAccBalance() - currentBet));
            currentBet *= 2;
            hit(card);
            stand();  // Must stand after doubling down
            System.out.println("Player doubled down. Bet is now: " + currentBet);
        } else {
            System.out.println("Not enough balance to double down.");
        }
    }

    public boolean canSplit() {
        System.out.println("Checking if player can split...");
        System.out.println("Hand size: " + hand.size());
        if (hand.size() == 2) {
            System.out.println("First card rank: " + hand.get(0).getRank());
            System.out.println("Second card rank: " + hand.get(1).getRank());
            if (hand.get(0).getRank() == hand.get(1).getRank()) {
                return true;
            }
        }
        return false;
    }
    
    public void split() {
        if (canSplit()) {
            // Initialize splitHands
            splitHands = new ArrayList<>();

            // First hand: contains the first card
            ArrayList<Card> firstHand = new ArrayList<>();
            firstHand.add(hand.get(0));
            splitHands.add(firstHand);

            // Second hand: contains the second card
            ArrayList<Card> secondHand = new ArrayList<>();
            secondHand.add(hand.get(1));
            splitHands.add(secondHand);

            // Clear the original hand
            hand.clear();

            System.out.println("Player split their hand.");
        } else {
            System.out.println("Cannot split: Cards must be identical.");
        }
    }

    
    public void hitSplitHand(int handIndex, Card card) {
        if (splitHands != null && handIndex >= 0 && handIndex < splitHands.size()) {
            splitHands.get(handIndex).add(card);
            System.out.println("Player hits split hand " + handIndex + " and receives: " + card);
        } else {
            System.out.println("Invalid hand index or split hands not initialized.");
        }
    }

	public int getAccBalance() {
		return accBalance;
	}

	public void setAccBalance(int accBalance) {
		this.accBalance = accBalance;
	}


}

