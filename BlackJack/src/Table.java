
import java.util.ArrayList;
import java.util.List;

public class Table {

    // Fields
    private int tableID;
    private Dealer dealer;
    private List<Player> players;
    private static final int MAX_CAPACITY = 5; // Maximum players per table

    // Constructor
    public Table(int tableID, Dealer dealer) {
        this.tableID = tableID;
        this.dealer = dealer;
        this.players = new ArrayList<>();
    }

    // Getters and Setters
    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getCapacity() {
        return MAX_CAPACITY;
    }

    public int getAvailableSeats() {
        return MAX_CAPACITY - players.size();
    }

    // Methods
    public boolean joinTable(Player player) {
        if (players.size() < MAX_CAPACITY) {
            players.add(player);
            System.out.println(player.getName() + " joined Table " + tableID + ".");
            return true;
        } else {
            System.out.println("Table " + tableID + " is full. Cannot join.");
            return false;
        }
    }

    public void displayTableInfo() {
        System.out.println("Table ID: " + tableID);
        System.out.println("Dealer: " + dealer.getUsername());
        System.out.println("Current Players: ");
        for (Player player : players) {
            System.out.println("- " + player.getName());
        }
        System.out.println("Available Seats: " + getAvailableSeats());
    }
}
