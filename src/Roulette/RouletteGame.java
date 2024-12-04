package Roulette;

import java.util.Scanner;

public class RouletteGame {
    //making the instance variable private to prevent direct access from other classes
    private static RouletteGame instance;
    private wheelSpin wheel;
    private ColorDeterminer colorDeterminer;

    //making the constructor private to prevent instantiation from other classes
    private RouletteGame() {
        this.wheel = new wheelSpin();
        this.colorDeterminer = new ColorDeterminer();
    }
    //implementing singleton pattern ensuring only one game is created at a time
    public static synchronized RouletteGame getInstance() {
        if (instance == null) {
            instance = new RouletteGame();
        }
        return instance;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        int chips = 100;
        boolean playAgain = true;

        while (playAgain) {
            System.out.println("\nYou have " + chips + " chips.");
            System.out.print("Enter the amount of chips you want to bet: ");
            int betAmount = 0;
            try {
                betAmount = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.next();
                continue;
            }
            if (betAmount > 0 && betAmount <= chips) {
                System.out.println("\nWelcome to the Roulette game!");
                System.out.println("You can place the following types of bets:");
                System.out.println("1. Straight-Up Bet (Pick a number between 0-36)");
                System.out.println("2. Even/Odd Bet");
                System.out.println("3. Red/Black Bet");
                System.out.println("4. Low/High Bet (1-18 or 19-36)");
                System.out.println("5. Dozen Bet (1-12, 13-24, or 25-36)");
                System.out.println("6. Column Bet (First, Second, or Third column)");
                System.out.print("Enter the type of bet you want to place (1-6): ");
                int betType = 0; //int to store the type of bet
                try {
                    betType = scanner.nextInt();
                } catch (Exception e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                    scanner.next();
                    continue;
                }
                int playerNumberBet = -1; //int to store the number the player bets on starts at -1 to avoid null
                String playerColorBet = ""; // string to store the color the player bets on set it to nothing at first
                boolean isEvenBet = false; //boolean to store if the player bets on even
                boolean isLowBet = false;//boolean to store if the player bets on low
                String winningColor;//string to store the color of the winning number
                int dozenBet = -1;
                int columnBet = -1;
                switch (betType) {
                    case 1:
                        System.out.print("Enter the number you want to bet on (0-36): ");
                        playerNumberBet = scanner.nextInt();
                        if (playerNumberBet < 0 || playerNumberBet > 36) {
                            System.out.println("Invalid bet! Number must be between 0 and 36.");
                            continue;
                        }
                        break;
                    case 2:
                        System.out.print("Bet on Even or Odd (Enter 'Even' or 'Odd'): ");
                        String evenOddBet = scanner.next();
                        if (!evenOddBet.equalsIgnoreCase("Even") && !evenOddBet.equalsIgnoreCase("Odd")) {
                            System.out.println("Invalid input! Enter 'Even' or 'Odd'.");
                            continue;
                        }
                        isEvenBet = evenOddBet.equalsIgnoreCase("Even");
                        break;
                    case 3:
                        System.out.print("Bet on Red or Black (Enter 'Red' or 'Black'): ");
                        playerColorBet = scanner.next();
                        if (!playerColorBet.equalsIgnoreCase("Red") && !playerColorBet.equalsIgnoreCase("Black")) {
                            System.out.println("Invalid color! Enter 'Red' or 'Black'.");
                            continue;
                        }
                        break;
                    case 4:
                        System.out.print("Bet on Low (1-18) or High (19-36) (Enter 'Low' or 'High'): ");
                        winningColor = scanner.next();
                        isLowBet = winningColor.equalsIgnoreCase("Low");
                        break;
                    case 5:
                        System.out.print("Bet on Dozen (Enter '1' for 1-12, '2' for 13-24, '3' for 25-36): ");
                        try {
                            dozenBet = scanner.nextInt();
                        } catch (Exception e) {
                            System.out.println("Invalid input! Please enter a valid number.");
                            scanner.next();
                            continue;
                        }
                        if (dozenBet < 1 || dozenBet > 3) {
                            System.out.println("Invalid bet! Enter '1', '2', or '3'.");
                            continue;
                        }
                        break;
                    case 6:
                        System.out.print("Bet on Column (Enter '1' for first column, '2' for second column, '3' for third column): ");
                        try {
                            columnBet = scanner.nextInt();
                        } catch (Exception e) {
                            System.out.println("Invalid input! Please enter a valid number.");
                            scanner.next();
                            continue;
                        }

                        if (columnBet < 1 || columnBet > 3) {
                            System.out.println("Invalid bet! Enter '1', '2', or '3'.");
                            continue;
                        }
                        break;
                    default:
                        System.out.println("Invalid bet type!");
                        continue;
                }
                int winningNumber = wheel.spinWheel();
                winningColor = Roulette.ColorDeterminer.getColor(winningNumber);
                System.out.println("\nThe roulette wheel landed on: " + winningNumber + " (" + winningColor + ")");
                boolean playerWins = false;
                switch (betType) {
                    case 1:
                        playerWins = playerNumberBet == winningNumber;
                        break;
                    case 2:
                        playerWins = winningNumber != 0 && ((winningNumber & 1) == 0) == isEvenBet;
                        break;
                    case 3:
                        playerWins = winningColor.equalsIgnoreCase(playerColorBet);
                        break;
                    case 4:
                        playerWins = winningNumber != 0 && winningNumber <= 18 == isLowBet;
                    case 5:
                        if (dozenBet == 1) {
                            playerWins = winningNumber >= 1 && winningNumber <= 12;
                        } else if (dozenBet == 2) {
                            playerWins = winningNumber >= 13 && winningNumber <= 24;
                        } else if (dozenBet == 3) {
                            playerWins = winningNumber >= 25 && winningNumber <= 36;
                        }
                        break;
                    case 6:
                        if (columnBet == 1) {
                            playerWins = (winningNumber - 1) % 3 == 0;
                        } else if (columnBet == 2) {
                            playerWins = (winningNumber - 2) % 3 == 0;
                        } else if (columnBet == 3) {
                            playerWins = (winningNumber - 3) % 3 == 0;
                        }
                        break;
                }

                if (playerWins) {
                    System.out.println("Congratulations! You won your bet!");
                    chips += betAmount;
                } else {
                    System.out.println("Sorry, you lost. Better luck next time!");
                    chips -= betAmount;
                }

                System.out.println("You now have " + chips + " chips.");
                if (chips <= 0) {
                    System.out.println("You're out of chips! Game over.");
                    playAgain = false;
                    break;
                }

                System.out.print("\nWould you like to play again? (yes/no): ");
                String response = scanner.next();
                playAgain = response.equalsIgnoreCase("yes");
            } else {
                System.out.println("Invalid bet amount! Bet must be between 1 and " + chips + ".");
            }
        }

        System.out.println("\nThanks for playing Roulette! Goodbye!");
        scanner.close();
    }

    public static void main(String[] args) {
        RouletteGame game = new RouletteGame();
        game.play();
    }

}



