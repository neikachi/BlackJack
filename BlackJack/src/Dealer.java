import deckManagement.Card;
import deckManagement.DeckCollection;

public class Dealer extends User {

    private DeckCollection deckCollection;
    private int hand; // Total hand value for the dealer

    public Dealer(String username, String password, int accBalance, AccountType accountType, int dealerID) {
        super(username, password, accBalance, accountType);
        this.deckCollection = null;
        this.hand = 0;
    }

    public int getHand() {
        return hand;
    }

    public void setHand(int hand) {
        this.hand = hand;
    }

    public void createGame() {
        System.out.println("Dealer creating a new game...");
        this.deckCollection = new DeckCollection();
        System.out.println("Game started with a fresh deck.");
    }

    public void shuffleDeck() {
        if (deckCollection != null) {
            deckCollection.shuffleCollection();
            System.out.println("Deck shuffled.");
        } else {
            System.out.println("No deck available to shuffle.");
        }
    }

    public Card dealCard() {
        if (deckCollection != null && !deckCollection.isEmpty()) {
            return deckCollection.dealCard();
        } else {
            System.out.println("No deck available to deal cards.");
            return null;
        }
    }

    public void reset() {
        System.out.println("Resetting dealer for the next game...");
        this.hand = 0;
        this.deckCollection = null;
    }
}

