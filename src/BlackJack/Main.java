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
        //creates objects for the player, dealer, and deck
        Deck deck = Deck.getInstance();
        Player player = new Player("Player", deck);
        Dealer dealer = new Dealer(deck, player);

        // Create player commands
        Command phitCommand = new PlayerHitCommand(player);
        Command pstandCommand = new PlayerStandCommand(player);

        // Create dealer commands
        Command dhitCommand = new DealerHitCommand(dealer);
        Command dstandCommand = new DealerStandCommand(dealer);

        // Set commands in player
        player.setHitCommand(phitCommand);
        player.setStandCommand(pstandCommand);
        dealer.setHitCommand(dhitCommand);
        dealer.setStandCommand(dstandCommand);


        //create the invoker
        Invoker invoker = new Invoker();
        Scanner scanner = new Scanner(System.in);


        // Game loop
        boolean playersTurn = true;
        while (playersTurn) {
            System.out.println("Player's turn");
            System.out.println("Hit or Stand?");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("hit")) {
                invoker.addCommand(phitCommand);
                invoker.executeCommands();
                System.out.println("     ");
                invoker.removeCommand(phitCommand);
                if (player.isBusted() || player.isBlackjack()) {
                    playersTurn = false;
                }
            } else if (input.equalsIgnoreCase("stand")) {
                invoker.addCommand(pstandCommand);
                invoker.executeCommands();
                System.out.println("     ");

                invoker.removeCommand(pstandCommand);

                playersTurn = false;
            } else {
                System.out.println("Invalid input. Please enter 'hit' or 'stand'.");
            }
        }

        // Dealer's turn
        while (dealer.getScore() < 17) {
            invoker.addCommand(dhitCommand);
            invoker.executeCommands();
            invoker.removeCommand(dhitCommand);
            if (dealer.isBusted()) {
                break;
            }
        }

        // Display final scores
        //System.out.println("Player's final score: " + player.getScore());
        //System.out.println("Dealer's final score: " + dealer.getScore());

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


