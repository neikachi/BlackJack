package unitTests;
import deckManagement.DeckCollection;
import deckManagement.Card;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeckCollectionTest {

    private DeckCollection deckCollection;

    @BeforeEach
    void setUp() {
        deckCollection = new DeckCollection(); 
    }

    @Test
    void testDeckCollectionInitialization() {

        assertEquals(156, deckCollection.remainingCards(), "DeckCollection should start with 156 cards.");
    }

    @Test
    void testShuffleCollection() {
        int initialSize = deckCollection.remainingCards();
        
        deckCollection.shuffleCollection();

        assertEquals(initialSize, deckCollection.remainingCards(), "DeckCollection should still contain the same number of cards after shuffling.");
    }

    @Test
    void testDealCard() {
        Card dealtCard = deckCollection.dealCard();


        assertNotNull(dealtCard, "Dealt card should not be null.");

        assertEquals(155, deckCollection.remainingCards(), "Remaining cards should decrease by one after dealing.");
    }

    @Test
    void testDealCardUntilEmpty() {
        for (int i = 0; i < 156; i++) {
            assertNotNull(deckCollection.dealCard(), "Each dealt card should not be null.");
        }

        assertTrue(deckCollection.isEmpty(), "DeckCollection should be empty after dealing all cards.");

        Card reshuffledCard = deckCollection.dealCard();

        assertNotNull(reshuffledCard, "A card should still be dealt after reshuffling.");
        assertEquals(155, deckCollection.remainingCards(), "Remaining cards should reset to 155 after reshuffling and dealing one card.");
    }

    @Test
    void testIsEmpty() {
        assertFalse(deckCollection.isEmpty(), "DeckCollection should not be empty initially.");

        for (int i = 0; i < 156; i++) {
            deckCollection.dealCard();
        }

        assertTrue(deckCollection.isEmpty(), "DeckCollection should be empty after dealing all cards.");
    }

    @Test
    void testRemainingCards() {

        assertEquals(156, deckCollection.remainingCards(), "Initially, DeckCollection should have 156 cards.");

        deckCollection.dealCard();
        assertEquals(155, deckCollection.remainingCards(), "Remaining cards should decrease to 155 after dealing one card.");
    }
}
