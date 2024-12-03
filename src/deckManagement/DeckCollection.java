package deckManagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeckCollection {
    private List<Card> cards;

    public DeckCollection() {
        cards = new ArrayList<>();
        for (int i = 0; i < 3; i++) {  // Adding three decks
            Deck deck = new Deck();
            cards.addAll(deck.getCards());
        }
        shuffleCollection();
    }

    public void shuffleCollection() {
        Collections.shuffle(cards);  // Shuffle combined decks
    }

    public Card dealCard() {
        if (cards.isEmpty()) {
            System.out.println("The deck collection is empty! Reshuffling...");
            resetDeckCollection();  // Reshuffle when empty
        }
        return cards.remove(cards.size() - 1);  // Deal the top card
    }

    private void resetDeckCollection() {
        cards.clear();
        for (int i = 0; i < 3; i++) {  // Rebuild the deck collection
            Deck deck = new Deck();
            cards.addAll(deck.getCards());
        }
        shuffleCollection();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public int remainingCards() {
        return cards.size();
    }
}