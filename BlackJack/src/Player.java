import deckManagement.Card;

public class Player extends User {

    private int playerID;
    private String name;
    private double balance;
    private int hand; // Total value of the player's hand
    private double currentBet; // The current bet placed by the player
    private PlayerStatus status;

    public enum PlayerStatus {
        ACTIVE, STANDING, BUSTED, BLACKJACK
    }

    public Player(String username, String password, int accBalance, AccountType accountType,
                  int playerID, String name, double balance) {
        super(username, password, accBalance, accountType);
        this.playerID = playerID;
        this.name = name;
        this.balance = balance;
        this.hand = 0;
        this.currentBet = 0.0;
        this.status = PlayerStatus.ACTIVE;
    }

    // Getter for current bet
    public double getCurrentBet() {
        return currentBet;
    }

    // Other getters
    public int getHand() {
        return hand;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    // Setters
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setHand(int hand) {
        this.hand = hand;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    // Methods
    public void hit(Card card) {
        if (card == null) {
            System.out.println(name + " cannot hit because no card is available.");
            return;
        }
        System.out.println(name + " chose to hit.");
        this.hand += card.getValue();
        if (hand > 21) {
            this.status = PlayerStatus.BUSTED;
            System.out.println(name + " busted!");
        } else if (hand == 21) {
            this.status = PlayerStatus.BLACKJACK;
            System.out.println(name + " got Blackjack!");
        }
    }

    public void stand() {
        System.out.println(name + " chose to stand.");
        this.status = PlayerStatus.STANDING;
    }

    public void placeBet(double amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance to place the bet.");
        } else {
            this.currentBet = amount;
            this.balance -= amount;
            System.out.println(name + " placed a bet of $" + amount);
        }
    }

    public void doubleDown(Card card) {
        if (balance >= currentBet) {
            this.balance -= currentBet;
            this.currentBet *= 2;
            System.out.println(name + " chose to double down! New bet: $" + currentBet);
            this.hit(card);
        } else {
            System.out.println("Insufficient balance to double down.");
        }
    }

    public void reset() {
        System.out.println("Resetting " + name + " for the next round.");
        this.hand = 0;
        this.currentBet = 0.0;
        this.status = PlayerStatus.ACTIVE;
    }
}



