package unitTests;
import deckManagement.Deck;
import deckManagement.Card;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

class DeckTest {

    private Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck(); 
    }

    @Test
    void testDeckInitialization() {
        List<Card> cards = deck.getCards();
        assertEquals(52, cards.size(), "Deck should contain 52 cards.");

        HashSet<Card> uniqueCards = new HashSet<>(cards);
        assertEquals(52, uniqueCards.size(), "All cards in the deck should be unique.");
    }

    @Test
    void testShuffle() {
        List<Card> originalOrder = deck.getCards();

        deck.shuffle();

        assertEquals(52, deck.getCards().size(), "Deck should still contain 52 cards after shuffling.");

        List<Card> shuffledOrder = deck.getCards();
        assertNotEquals(originalOrder, shuffledOrder, "Order of cards should change after shuffling.");
    }

    @Test
    void testGetCardsReturnsCopy() {
        List<Card> cardsFromDeck = deck.getCards();

        cardsFromDeck.clear();

        List<Card> cardsAfterModification = deck.getCards();

        assertEquals(52, cardsAfterModification.size(), "Original deck should not be affected by modifications to the returned list.");
    }
}
