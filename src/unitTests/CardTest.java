package unitTests;
import deckManagement.Card;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CardTest {

    @Test
    void testConstructorAndGetters() {
        Card card = new Card(Card.Suit.HEARTS, Card.Rank.ACE);
        assertEquals(Card.Suit.HEARTS, card.getSuit());
        assertEquals(Card.Rank.ACE, card.getRank());
    }

    @Test
    void testGetValue() {
        Card card = new Card(Card.Suit.CLUBS, Card.Rank.TEN);
        assertEquals(10, card.getValue());

        Card aceCard = new Card(Card.Suit.SPADES, Card.Rank.ACE);
        assertEquals(1, aceCard.getValue());
    }

    @Test
    void testRankValues() {
        assertEquals(2, Card.Rank.TWO.getValue());
        assertEquals(10, Card.Rank.JACK.getValue());
        assertEquals(1, Card.Rank.ACE.getValue());
    }

    @Test
    void testSuitEnum() {
        assertEquals(Card.Suit.HEARTS, Card.Suit.valueOf("HEARTS"));
        assertEquals(Card.Suit.DIAMONDS, Card.Suit.valueOf("DIAMONDS"));
    }

    @Test
    void testRankEnum() {
        assertEquals(Card.Rank.KING, Card.Rank.valueOf("KING"));
        assertEquals(10, Card.Rank.KING.getValue());
    }
}
