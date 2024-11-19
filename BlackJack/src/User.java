public class User {

    // Fields
    private String username;
    private String password;
    private int accBalance;
    private AccountType accountType;

    // Enum for account type
    public enum AccountType {
        STANDARD, PREMIUM, ADMIN
    }

    // Constructor
    public User(String username, String password, int accBalance, AccountType accountType) {
        this.username = username;
        this.password = password;
        this.accBalance = accBalance;
        this.accountType = accountType;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccBalance() {
        return accBalance;
    }

    public void setAccBalance(int accBalance) {
        this.accBalance = accBalance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    // Methods

    // Simulates user login
    public boolean login(String inputUsername, String inputPassword) {
        if (this.username.equals(inputUsername) && this.password.equals(inputPassword)) {
            System.out.println("Login successful!");
            return true;
        } else {
            System.out.println("Invalid username or password.");
            return false;
        }
    }

    // Simulates user registration
    public static User register(String username, String password, int initialBalance, AccountType accountType) {
        System.out.println("Registration successful! Welcome, " + username + "!");
        return new User(username, password, initialBalance, accountType);
    }

    // Simulates joining a game
    public void joinGame() {
        if (accBalance > 0) {
            System.out.println(username + " joined the game.");
        } else {
            System.out.println("Insufficient balance to join the game.");
        }
    }

    // Simulates leaving a game
    public void leaveGame() {
        System.out.println(username + " left the game.");
    }
}

