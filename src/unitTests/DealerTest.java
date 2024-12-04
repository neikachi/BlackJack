package unitTests;
import user.Dealer;
import user.Player;
import user.User;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import deckManagement.Card;
import deckManagement.DeckCollection;

class DealerTest {

    private Dealer dealer;
    private Player player;
    private DeckCollection deckCollection;

    @BeforeEach
    void setUp() {
        dealer = new Dealer("Dealer1", "password123");
        player = new Player("Player1", "password123", 100, User.AccountType.PLAYER);
        deckCollection = new DeckCollection(); 
    }

    @Test
    void testDealerInitialization() {
        assertEquals("Dealer1", dealer.getUsername(), "Dealer username should be initialized correctly.");
        assertEquals(0, dealer.getBalance(), "Dealer balance should be initialized to 0.");
        assertEquals(User.AccountType.DEALER, dealer.getAccountType(), "Dealer account type should be set to DEALER.");
    }

    @Test
    void testDealCard() {
        int initialHandSize = player.getHand().size();
        dealer.dealCard(player, deckCollection);

        assertEquals(initialHandSize + 1, player.getHand().size(), "Player's hand size should increase by 1 after being dealt a card.");
        assertEquals(155, deckCollection.remainingCards(), "Remaining cards in the deck should decrease by 1 after dealing.");
    }

    @Test
    void testDealerHit() {
        int initialHandSize = dealer.getHand().size();
        dealer.hit(deckCollection);

        assertEquals(initialHandSize + 1, dealer.getHand().size(), "Dealer's hand size should increase by 1 after hitting.");
        assertEquals(155, deckCollection.remainingCards(), "Remaining cards in the deck should decrease by 1 after the dealer hits.");
    }

    @Test
    void testDealerPlayTurn() {
        dealer.playTurn(deckCollection);

        int handValue = dealer.getHandValue();
        assertTrue(handValue >= 17, "Dealer's hand value should be at least 17 after playing their turn.");
        assertTrue(dealer.getStatus() == User.Status.STANDING || dealer.getStatus() == User.Status.BUSTED,
                   "Dealer's status should be STANDING or BUSTED after playing their turn.");
    }
}
