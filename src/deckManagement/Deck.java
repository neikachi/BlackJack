package deckManagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        shuffle();  // Shuffle when created
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);  // Return a copy to prevent external modification
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }
}