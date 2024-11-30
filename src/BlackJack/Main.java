package BlackJack;

import java.util.Scanner;

/***
 * Main Class
 * This class is used to represent the main class
 */
public class Main {
    public static void main(String[] args) {
       /*
        try{
        Deck deck = Deck.getInstance();
        Card card = deck.draw();
        System.out.println("Card drawn: " + card.getRank() + " of " + card.getSuit());
        } catch (IllegalStateException e) {
            System.out.println("Cannot draw card: " + e.getMessage());
        }

        */
        Deck deck = Deck.getInstance();
        Player player = new Player("Player");
        Dealer dealer = new Dealer(deck, player);


        // Create player commands
        Command phitCommand = new PlayerHitCommand(player, deck);
        Command pstandCommand = new PlayerStandCommand(player);

        // Create dealer commands
        Command dhitCommand = new DealerHitCommand(dealer, deck);
        Command dstandCommand = new DealerStandCommand(dealer);

        // Set commands in player
        player.setHitCommand(phitCommand);
        player.setStandCommand(pstandCommand);
        dealer.setHitCommand(dhitCommand);
        dealer.setStandCommand(dstandCommand);

        Scanner scanner = new Scanner(System.in);


        // Game loop
        boolean playersTurn = true;
        while (playersTurn) {
            System.out.println("Player's turn");
            System.out.println("Hit or Stand?");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("hit")) {
                player.hit();
                if (player.isBusted() || player.isBlackjack()) {
                    playersTurn = false;
                }
            } else if (input.equalsIgnoreCase("stand")) {
                player.stand();
                playersTurn = false;
            } else {
                System.out.println("Invalid input. Please enter 'hit' or 'stand'.");
            }
        }

        // Dealer's turn
        while (dealer.getScore() < 17) {
            dealer.hit();
            if (dealer.isBusted()) {
                break;
            }
        }

        // Display final scores
        System.out.println("Player's final score: " + player.getScore());
        System.out.println("Dealer's final score: " + dealer.getScore());

        if (player.isBusted()) {
            System.out.println("Player busted. Dealer wins!");
        } else if (dealer.isBusted()) {
            System.out.println("Dealer busted. Player wins!");
        } else if (player.getScore() > dealer.getScore()) {
            System.out.println("Player wins!");
        } else if (player.getScore() < dealer.getScore()) {
            System.out.println("Dealer wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }
}


