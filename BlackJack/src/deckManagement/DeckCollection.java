package deckManagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeckCollection {
    private List<Card> cards;

    public DeckCollection() {
        cards = new ArrayList<>();
        Deck deck = new Deck();
        cards.addAll(deck.getCards());
    }

    public void shuffleCollection() {
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        if (cards.isEmpty()) {
            System.out.println("The deck collection is empty!");
            return null;
        }
        return cards.remove(cards.size() - 1);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}

