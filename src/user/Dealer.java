package user;

import deckManagement.Card;
import deckManagement.DeckCollection;

public class Dealer extends User {
    // Constructor
    public Dealer(String username, String password) {
        super(username, password, 0, AccountType.DEALER); // Set balance to 0
    }

    // Deal a card to a player
    public void dealCard(Player player, DeckCollection deckCollection) {
        Card card = deckCollection.dealCard();
        if (card != null) {
            player.hit(card);
            System.out.println("Dealer deals a card to " + player.getUsername() + ": " + card);
        }
    }

    // Dealer hits (draws a card for themselves)
    public void hit(DeckCollection deckCollection) {
        Card card = deckCollection.dealCard();
        if (card != null) {
            hand.add(card);
            System.out.println("Dealer hits and receives: " + card);
        }
    }

    // Determine if the dealer should continue hitting
    public void playTurn(DeckCollection deckCollection) {
        while (getHandValue() < 17) {
            hit(deckCollection);
        }
        if (getHandValue() > 21) {
            setStatus(Status.BUSTED);
        } else {
            setStatus(Status.STANDING);
        }
    }
}