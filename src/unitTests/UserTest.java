package unitTests;
import user.User;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import deckManagement.Card;

class UserTest {

    private User user;


    class TestUser extends User {
        public TestUser(String username, String password, int accBalance, AccountType accountType) {
            super(username, password, accBalance, accountType);
        }
    }

    @BeforeEach
    void setUp() {
        user = new TestUser("TestUser", "password123", 500, User.AccountType.PLAYER);
    }

    @Test
    void testUserInitialization() {
        assertEquals("TestUser", user.getUsername(), "Username should be initialized correctly.");
        assertEquals("password123", user.getPassword(), "Password should be initialized correctly.");
        assertEquals(500, user.getBalance(), "Balance should be initialized correctly.");
        assertEquals(User.AccountType.PLAYER, user.getAccountType(), "Account type should be initialized correctly.");
        assertEquals(User.Status.ACTIVE, user.getStatus(), "Initial status should be ACTIVE.");
        assertTrue(user.getHand().isEmpty(), "Initial hand should be empty.");
    }

    @Test
    void testSetAndGetUsername() {
        user.setUsername("NewUsername");
        assertEquals("NewUsername", user.getUsername(), "Username should be updated correctly.");
    }

    @Test
    void testSetAndGetPassword() {
        user.setPassword("NewPassword");
        assertEquals("NewPassword", user.getPassword(), "Password should be updated correctly.");
    }

    @Test
    void testSetAndGetBalance() {
        user.setBalance(1000);
        assertEquals(1000, user.getBalance(), "Balance should be updated correctly.");
    }

    @Test
    void testSetAndGetAccountType() {
        user.setAccountType(User.AccountType.DEALER);
        assertEquals(User.AccountType.DEALER, user.getAccountType(), "Account type should be updated correctly.");
    }

    @Test
    void testSetAndGetStatus() {
        user.setStatus(User.Status.STANDING);
        assertEquals(User.Status.STANDING, user.getStatus(), "Status should be updated to STANDING.");

        user.setStatus(User.Status.BUSTED);
        assertEquals(User.Status.BUSTED, user.getStatus(), "Status should be updated to BUSTED.");
    }

    @Test
    void testAddCardToHand() {
        Card card = new Card(Card.Suit.HEARTS, Card.Rank.ACE);
        user.addCardToHand(card);
        assertEquals(1, user.getHand().size(), "Hand should contain one card after adding.");
        assertEquals(card, user.getHand().get(0), "The added card should be in the hand.");
    }

    @Test
    void testResetHand() {
        user.addCardToHand(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        user.addCardToHand(new Card(Card.Suit.CLUBS, Card.Rank.TEN));
        assertEquals(2, user.getHand().size(), "Hand should contain two cards before resetting.");
        user.resetHand();
        assertTrue(user.getHand().isEmpty(), "Hand should be empty after resetting.");
    }

    @Test
    void testGetHandValue() {
        user.addCardToHand(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        user.addCardToHand(new Card(Card.Suit.CLUBS, Card.Rank.TEN));
        assertEquals(21, user.getHandValue(), "Hand value should be 21 for ACE and TEN.");


        user.addCardToHand(new Card(Card.Suit.SPADES, Card.Rank.TWO));
        assertEquals(13, user.getHandValue(), "Hand value should adjust for ACE to be 1 when over 21.");
    }

    @Test
    void testPrintHand() {
        user.addCardToHand(new Card(Card.Suit.HEARTS, Card.Rank.ACE));
        user.addCardToHand(new Card(Card.Suit.CLUBS, Card.Rank.TEN));
        user.printHand();
    }
}
