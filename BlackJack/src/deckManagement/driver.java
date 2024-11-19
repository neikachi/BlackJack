package deckManagement;

public class driver {
    public static void main(String[] args) {
        // Initialize a DeckCollection
        DeckCollection deckCollection = new DeckCollection();
        deckCollection.shuffleCollection(); // Assuming shuffle method is available in DeckCollection

        // Draw five cards and display their values
        for (int i = 0; i < 5; i++) {
            Card card = deckCollection.dealCard(); // Draw a card from DeckCollection
            if (card != null) {
                System.out.println("Drew a " + card.getRank() + " of " + card.getSuit());
                System.out.println("Standard Value: " + card.getValue());
                System.out.println();
            } else {
                System.out.println("Deck is empty!");
                break;
            }
        }
    }
}