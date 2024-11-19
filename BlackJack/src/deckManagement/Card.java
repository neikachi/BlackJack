package deckManagement;

public class Card {
    private final Suit suit;
    private Rank rank;

    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    public Card(Suit suit, Rank rank) {
        if (rank == null) {
            throw new IllegalArgumentException("Rank cannot be null.");
        }
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public int getValue() {
        return rank.getValue();
    }

    public int getAlternateValue() {
        return rank.getAlternateValue();
    }

    public void setAce(Rank rank) {
        if (rank == null) {
            throw new IllegalArgumentException("Rank cannot be null.");
        }
        this.rank = rank;
    }
}

