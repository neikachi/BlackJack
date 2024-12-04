package unitTests;
import user.Player;
import user.User;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import deckManagement.Card;
import deckManagement.Deck;

class PlayerTest {

    private Player player;
    private Deck deck;

    @BeforeEach
    void setUp() {
        player = new Player("Player1", "password123", 500, User.AccountType.PLAYER);
        deck = new Deck(); 
    }

    @Test
    void testPlayerInitialization() {
        assertEquals("Player1", player.getUsername(), "Player username should be initialized correctly.");
        assertEquals(500, player.getAccBalance(), "Player balance should be initialized to 500.");
        assertEquals(User.AccountType.PLAYER, player.getAccountType(), "Player account type should be set to PLAYER.");
        assertTrue(player.getHand().isEmpty(), "Player's hand should be empty initially.");
        assertEquals(0.0, player.getCurrentBet(), "Current bet should be initialized to 0.0.");
    }

    @Test
    void testPlaceBet() {
        player.placeBet(100.0);
        assertEquals(100.0, player.getCurrentBet(), "Current bet should be updated to 100.0.");
        assertEquals(400.0, player.getAccBalance(), "Balance should decrease to 400.0 after placing the bet.");


        player.placeBet(600.0);
        assertEquals(100.0, player.getCurrentBet(), "Current bet should remain unchanged after an invalid bet.");
        assertEquals(400.0, player.getAccBalance(), "Balance should remain unchanged after an invalid bet.");
    }

    @Test
    void testHit() {
        Card card = deck.getCards().get(0);
        player.hit(card);

        assertEquals(1, player.getHand().size(), "Player's hand size should increase by 1 after hitting.");
        assertEquals(card, player.getHand().get(0), "The dealt card should be added to the player's hand.");
    }


    @Test
    void testStand() {
        System.out.println("Initial status: " + player.getStatus());
        player.stand();
        System.out.println("Status after calling stand(): " + player.getStatus());
        assertEquals(User.Status.STANDING, player.getStatus(), "Player's status should be updated to STANDING.");
    }



    @Test
    void testDoubleDown() {
        Card card = deck.getCards().get(0);
        player.placeBet(100.0);
        player.doubleDown(card);

        assertEquals(200.0, player.getCurrentBet(), "Current bet should double after doubling down.");
        assertEquals(300.0, player.getAccBalance(), "Balance should decrease correctly after doubling down.");
        assertEquals(1, player.getHand().size(), "Player's hand should contain one card after doubling down.");
        assertEquals(User.Status.STANDING, player.getStatus(), "Player should automatically stand after doubling down.");
    }

    @Test
    void testCanSplit() {

        Card card1 = new Card(Card.Suit.HEARTS, Card.Rank.TWO);
        Card card2 = new Card(Card.Suit.HEARTS, Card.Rank.TWO);
        player.hit(card1);
        player.hit(card2);

        assertTrue(player.canSplit(), "Player should be able to split identical cards.");

        player.getHand().clear();
        Card card3 = new Card(Card.Suit.HEARTS, Card.Rank.TWO);
        Card card4 = new Card(Card.Suit.CLUBS, Card.Rank.THREE);
        player.hit(card3);
        player.hit(card4);

        assertFalse(player.canSplit(), "Player should not be able to split non-identical cards.");
    }


    @Test
    void testSplit() {
        Card card1 = new Card(Card.Suit.HEARTS, Card.Rank.TWO);
        Card card2 = new Card(Card.Suit.HEARTS, Card.Rank.TWO);
        player.hit(card1);
        player.hit(card2);

        assertTrue(player.canSplit(), "Player should be able to split identical cards.");

        player.split();

        assertNotNull(player.getSplitHands(), "Split hands should be initialized.");
        assertEquals(2, player.getSplitHands().size(), "There should be two split hands.");

        assertEquals(1, player.getSplitHands().get(0).size(), "First split hand should have one card.");
        assertEquals(card1, player.getSplitHands().get(0).get(0), "First split hand should contain the first card.");

        assertEquals(1, player.getSplitHands().get(1).size(), "Second split hand should have one card.");
        assertEquals(card2, player.getSplitHands().get(1).get(0), "Second split hand should contain the second card.");
    }


    @Test
    void testHitSplitHand() {
        Card card1 = new Card(Card.Suit.HEARTS, Card.Rank.TWO);
        Card card2 = new Card(Card.Suit.HEARTS, Card.Rank.TWO);
        player.hit(card1);
        player.hit(card2);

        player.split();

        Card newCard = new Card(Card.Suit.SPADES, Card.Rank.FOUR);
        player.hitSplitHand(0, newCard);

        assertEquals(2, player.getSplitHands().get(0).size(), "First split hand should have two cards after hitting.");
        assertEquals(newCard, player.getSplitHands().get(0).get(1), "The new card should be added to the first split hand.");
    }
}
