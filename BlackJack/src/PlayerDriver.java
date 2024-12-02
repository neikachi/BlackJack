import deckManagement.Card;

import java.util.Scanner;

public class PlayerDriver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a Dealer and a Table
        Dealer dealer = new Dealer("Dealer1", "password", 0, Dealer.AccountType.ADMIN, 101);
        Table table = new Table(1, dealer);

        // Allow players to join the table
        System.out.println("Welcome to Table " + table.getTableID() + "! Max capacity: " + table.getCapacity());
        System.out.println("Enter the number of players who want to join (max 5): ");
        int numPlayers = scanner.nextInt();

        for (int i = 0; i < numPlayers; i++) {
            if (table.getAvailableSeats() == 0) {
                System.out.println("Table is full. No more players can join.");
                break;
            }
            System.out.println("Enter Player " + (i + 1) + " name: ");
            String playerName = scanner.next();
            Player player = new Player("Player" + (i + 1), "password123", 1000, Player.AccountType.STANDARD, i + 1, playerName, 500);
            table.joinTable(player);
        }

        // Display table info
        table.displayTableInfo();

        // Start the game if there are players
        if (table.getPlayers().isEmpty()) {
            System.out.println("No players joined the table. Game cannot start.");
        } else {
            System.out.println("\nStarting the game...");
            dealer.createGame(); // Ensure deck is initialized
            dealer.shuffleDeck();

            for (Player player : table.getPlayers()) {
                System.out.println("\nPlayer: " + player.getName());
                System.out.println("Your current balance: $" + player.getBalance());

                // Player places an initial bet
                System.out.print("Place your bet: ");
                double bet = scanner.nextDouble();
                player.placeBet(bet);

                // Dealer deals initial two cards to the player
                System.out.println("\nDealing cards...");
                for (int i = 0; i < 2; i++) {
                    Card card = dealer.dealCard();
                    if (card != null) {
                        System.out.println("You drew: " + card.getRank() + " of " + card.getSuit());
                        player.hit(card);
                    } else {
                        System.out.println("No more cards available. Ending game.");
                        return;
                    }
                }

                // Dealer deals one card to themselves
                Card dealerCard = dealer.dealCard();
                if (dealerCard != null) {
                    System.out.println("Dealer's face-up card: " + dealerCard.getRank() + " of " + dealerCard.getSuit());
                } else {
                    System.out.println("Dealer couldn't deal a card. Deck might be empty.");
                }

                // Player's turn
                boolean playerTurn = true;
                while (playerTurn) {
                    System.out.println("\nYour current hand value: " + player.getHand());
                    System.out.println("Your current balance: $" + player.getBalance());
                    System.out.println("1. Hit\n2. Stand\n3. Double Down\n4. Quit");
                    System.out.print("Choose your action: ");
                    int choice = scanner.nextInt();

                    switch (choice) {
                        case 1: // Hit
                            Card newCard = dealer.dealCard();
                            if (newCard != null) {
                                System.out.println("You drew: " + newCard.getRank() + " of " + newCard.getSuit());
                                player.hit(newCard);
                            } else {
                                System.out.println("No more cards available. Ending your turn.");
                                playerTurn = false;
                            }
                            if (player.getStatus() == Player.PlayerStatus.BUSTED) {
                                System.out.println("You busted! Dealer wins.");
                                playerTurn = false;
                            }
                            break;

                        case 2: // Stand
                            System.out.println("You chose to stand.");
                            playerTurn = false;
                            break;

                        case 3: // Double Down
                            if (player.getBalance() >= bet) {
                                Card doubledCard = dealer.dealCard(); // Dealer deals one additional card for double down
                                if (doubledCard != null) {
                                    player.doubleDown(doubledCard); // Pass the card to the doubleDown method
                                    System.out.println("You drew: " + doubledCard.getRank() + " of " + doubledCard.getSuit());
                                } else {
                                    System.out.println("No more cards available. Ending your turn.");
                                }
                                playerTurn = false; // Player's turn ends after doubling down
                            } else {
                                System.out.println("Insufficient balance to double down.");
                            }
                            break;

                        case 4: // Quit
                            System.out.println("You quit the game.");
                            return;

                        default:
                            System.out.println("Invalid choice. Please choose again.");
                    }
                }
            }


         // Dealer's turn
            System.out.println("\nDealer's turn...");
            while (dealer.getHand() < 17) {
                Card dealerCard = dealer.dealCard();
                if (dealerCard != null) {
                    System.out.println("Dealer drew: " + dealerCard.getRank() + " of " + dealerCard.getSuit());
                    dealer.setHand(dealer.getHand() + dealerCard.getValue());
                } else {
                    System.out.println("No more cards available for the dealer.");
                    break;
                }
            }
            System.out.println("Dealer's final hand value: " + dealer.getHand());

            // Determine results
            System.out.println("\nDetermining winners...");
            for (Player player : table.getPlayers()) 
            {
                if (player.getStatus() == Player.PlayerStatus.BUSTED) 
                {
                    System.out.println(player.getName() + " busted and lost.");
                } 
                else if (dealer.getHand() > 21 || player.getHand() > dealer.getHand()) 
                {
                    System.out.println(player.getName() + " wins!");
                    player.setBalance(player.getBalance() + (player.getCurrentBet() * 2));
                } 
                else if (player.getHand() == dealer.getHand()) 
                {
                    System.out.println(player.getName() + " ties with the dealer.");
                    player.setBalance(player.getBalance() + player.getCurrentBet());
                } else 
                {
                    System.out.println(player.getName() + " loses.");
                }
            }


         // Display final balances
         System.out.println("\nFinal Balances:");
         for (Player player : table.getPlayers()) {
             System.out.println(player.getName() + ": $" + player.getBalance());
         }
        scanner.close();
    }
}

}

