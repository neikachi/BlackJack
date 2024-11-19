package deckManagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> numCards;

    public Deck() {
        numCards = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Rank rank : Rank.values()) {
                numCards.add(new Card(suit, rank));
            }
        }
        shuffle();
    }

    public List<Card> getCards() {
        return numCards;
    }

    public void shuffle() {
        Collections.shuffle(numCards);
    }
}
