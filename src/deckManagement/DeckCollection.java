package deckManagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class DeckCollection {
    private List<Card> cards;

    // Default constructor to initialize the collection with a full deck of cards
    public DeckCollection() {
        cards = new ArrayList<>();
        Deck deck = new Deck(); // Assuming Deck has a method to provide the list of cards
        cards.addAll(deck.getCards()); // Assuming getCards() in Deck returns a List<Card>
    }

    // Shuffle the collection
    public void shuffleCollection() {
        Collections.shuffle(cards);
    }

    // Method to deal a card from the collection
    public Card dealCard() {
    	if (cards.isEmpty()) {
            System.out.println("The deck collection is empty!");
            return null;
        }
        Card currCard = cards.remove(cards.size() - 1);

        if (currCard.getRank() == Rank.ACE) {
            // Give the user the option to decide the rank of the ACE
            System.out.println("You drew an ACE! Would you like it to be 1 or 11?");
            
            Scanner scanner = new Scanner(System.in);
            int aceValue = 0;

            while (aceValue != 1 && aceValue != 11) {
                System.out.print("Enter 1 or 11: ");
                try {
                    aceValue = scanner.nextInt();
                    if (aceValue != 1 && aceValue != 11) {
                        System.out.println("Invalid choice. Please choose either 1 or 11.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number (1 or 11).");
                    scanner.next(); // Clear invalid input
                }
            }
            

            // Set the ACE value based on user choice
            currCard.setAce(aceValue == 1 ? Rank.ACE : Rank.ACE); // Maintain the same rank
            System.out.println("ACE value set to: " + aceValue);
        }

        return currCard;
    }

    // Method to check if the collection is empty
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}


//  if (card.getRank() == Card.Rank.ACE) {
//System.out.println("Alternate Value (ACE as 1): " + card.getAlternateValue());
