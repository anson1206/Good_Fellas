package Roulette;

import java.util.Scanner;

public class RouletteGame {
    private wheelSpin wheel;
    private ColorDeterminer colorDeterminer;

    public RouletteGame() {
        this.wheel = new wheelSpin();
        this.colorDeterminer = new ColorDeterminer();
    }
    public void play() {
        Scanner scanner = new Scanner(System.in);
        int chips = 100;
        boolean playAgain = true;

        while (playAgain) {
            System.out.println("\nYou have " + chips + " chips.");
            System.out.print("Enter the amount of chips you want to bet: ");
            int betAmount = scanner.nextInt();
            if (betAmount > 0 && betAmount <= chips) {
                System.out.println("\nWelcome to the Roulette game!");
                System.out.println("You can place the following types of bets:");
                System.out.println("1. Straight-Up Bet (Pick a number between 0-36)");
                System.out.println("2. Even/Odd Bet");
                System.out.println("3. Red/Black Bet");
                System.out.println("4. Low/High Bet (1-18 or 19-36)");
                System.out.print("Enter the type of bet you want to place (1-4): ");
                int betType = scanner.nextInt(); //int to store the type of bet
                
                int playerNumberBet = -1; //int to store the number the player bets on starts at -1 to avoid null
                String playerColorBet = ""; // string to store the color the player bets on set it to nothing at first
                boolean isEvenBet = false; //boolean to store if the player bets on even
                boolean isLowBet = false;//boolean to store if the player bets on low
                String winningColor;//string to store the color of the winning number


            }
        }
    }
}



