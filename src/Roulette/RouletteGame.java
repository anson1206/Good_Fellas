package Roulette;

import java.util.InputMismatchException;
import java.util.Scanner;

public class RouletteGame extends RouletteGameTemplate {
    private static RouletteGame instance;
    private wheelSpin wheel;
    private int chips = 100;
    private ColorDeterminer colorDeterminer;
    private Scanner scanner = new Scanner(System.in);

    // Instance variables for bet details
    private int playerNumberBet = -1;
    private String playerColorBet = "";
    private boolean isEvenBet = false;
    private boolean isLowBet = false;
    private int dozenBet = -1;
    private int columnBet = -1;

    private RouletteGame() {
        this.wheel = new wheelSpin();
        this.colorDeterminer = new ColorDeterminer();
    }

    public static synchronized RouletteGame getInstance() {
        if (instance == null) {
            instance = new RouletteGame();
        }
        return instance;
    }

    @Override
    protected int getBetAmount() {
        int betAmount = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Enter the amount of chips you want to bet: ");
            try {
                betAmount = scanner.nextInt();
                if (betAmount <= 0 || betAmount > chips) {
                    System.out.println("Invalid amount! You have " + chips + " chips.");
                    continue;
                }
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        }
        return betAmount;
    }

    @Override
    protected int getBetType() {
        int betType = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.println("\nWelcome to the Roulette game!");
            System.out.println("You can place the following types of bets:");
            System.out.println("1. Straight-Up Bet (Pick a number between 0-36)");
            System.out.println("2. Even/Odd Bet");
            System.out.println("3. Red/Black Bet");
            System.out.println("4. Low/High Bet (1-18 or 19-36)");
            System.out.println("5. Dozen Bet (1-12, 13-24, or 25-36)");
            System.out.println("6. Column Bet (First, Second, or Third column)");
            System.out.print("Enter the type of bet you want to place (1-6): ");
            try {
                betType = scanner.nextInt();
                if (betType >= 1 && betType <= 6) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input! Please enter a valid number (1-6).");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.next(); // Clearing the invalid input
            }
        }
        return betType;
    }

    @Override
    protected boolean placeBet(int betType, int betAmount) {
        boolean validBet = false;
        switch (betType) {
            case 1: // Straight-Up Bet
                System.out.print("Enter the number you want to bet on (0-36): ");
                try {
                    playerNumberBet = scanner.nextInt();
                    if (playerNumberBet >= 0 && playerNumberBet <= 36) {
                        validBet = true;
                    } else {
                        System.out.println("Invalid number! Must be between 0 and 36.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                    scanner.next(); // Clearing the invalid input
                }
                break;
            case 2: // Even/Odd Bet
                System.out.print("Bet on Even or Odd (Enter 'Even' or 'Odd'): ");
                String evenOddBet = scanner.next();
                if ("Even".equalsIgnoreCase(evenOddBet) || "Odd".equalsIgnoreCase(evenOddBet)) {
                    isEvenBet = "Even".equalsIgnoreCase(evenOddBet);
                    validBet = true;
                } else {
                    System.out.println("Invalid input! Enter 'Even' or 'Odd'.");
                }
                break;
            case 3: // Red/Black Bet
                System.out.print("Bet on Red or Black (Enter 'Red' or 'Black'): ");
                playerColorBet = scanner.next();
                if ("Red".equalsIgnoreCase(playerColorBet) || "Black".equalsIgnoreCase(playerColorBet)) {
                    validBet = true;
                } else {
                    System.out.println("Invalid color! Enter 'Red' or 'Black'.");
                }
                break;
            case 4: // Low/High Bet
                System.out.print("Bet on Low (1-18) or High (19-36) (Enter 'Low' or 'High'): ");
                String lowHighBet = scanner.next();
                if ("Low".equalsIgnoreCase(lowHighBet) || "High".equalsIgnoreCase(lowHighBet)) {
                    isLowBet = "Low".equalsIgnoreCase(lowHighBet);
                    validBet = true;
                } else {
                    System.out.println("Invalid input! Enter 'Low' or 'High'.");
                }
                break;
            case 5: // Dozen Bet
                System.out.print("Bet on Dozen (1 for 1-12, 2 for 13-24, 3 for 25-36): ");
                try {
                    dozenBet = scanner.nextInt();
                    if (dozenBet >= 1 && dozenBet <= 3) {
                        validBet = true;
                    } else {
                        System.out.println("Invalid bet! Enter '1', '2', or '3'.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                    scanner.next();
                }
                break;
            case 6: // Column Bet
                System.out.print("Bet on Column (1 for first, 2 for second, 3 for third): ");
                try {
                    columnBet = scanner.nextInt();
                    if (columnBet >= 1 && columnBet <= 3) {
                        validBet = true;
                    } else {
                        System.out.println("Invalid bet! Enter '1', '2', or '3'.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input! Please enter a valid number.");
                    scanner.next();
                }
                break;
        }
        return validBet;
    }

    @Override
    protected boolean determineWin(int betType) {
        int winningNumber = wheel.spinWheel();
        String winningColor = colorDeterminer.getColor(winningNumber);
        System.out.println("\nThe roulette wheel landed on: " + winningNumber + " (" + winningColor + ")");
        boolean playerWins = false;
        switch (betType) {
            case 1: // Straight-Up Bet
                playerWins = (playerNumberBet == winningNumber);
                break;
            case 2: // Even/Odd Bet
                playerWins = (winningNumber != 0) && ((winningNumber & 1) == 0) == isEvenBet;
                break;
            case 3: // Red/Black Bet
                playerWins = winningColor.equalsIgnoreCase(playerColorBet);
                break;
            case 4: // Low/High Bet
                playerWins = (winningNumber != 0) && ((winningNumber <= 18) == isLowBet);
                break;
            case 5: // Dozen Bet
                if (dozenBet == 1) {
                    playerWins = (winningNumber >= 1 && winningNumber <= 12);
                } else if (dozenBet == 2) {
                    playerWins = (winningNumber >= 13 && winningNumber <= 24);
                } else if (dozenBet == 3) {
                    playerWins = (winningNumber >= 25 && winningNumber <= 36);
                }
                break;
            case 6: // Column Bet
                if (columnBet == 1) {
                    playerWins = (winningNumber - 1) % 3 == 0;
                } else if (columnBet == 2) {
                    playerWins = (winningNumber - 2) % 3 == 0;
                } else if (columnBet == 3) {
                    playerWins = (winningNumber - 3) % 3 == 0;
                }
                break;
        }
        return playerWins;
    }

    @Override
    protected void updateChips(int betAmount, boolean playerWins) {
        if (playerWins) {
            chips += betAmount;
            System.out.println("You win! You now have " + chips + " chips.");
        } else {
            chips -= betAmount;
            System.out.println("You lose! You now have " + chips + " chips.");
        }
    }
    @Override
    protected boolean askPlayAgain(){
        String playAgain;
        while (true) {
            System.out.print("Would you like to play again? (yes/no): ");
            playAgain = scanner.next();
            if (playAgain.equalsIgnoreCase("yes")) {
                return true;
            } else if (playAgain.equalsIgnoreCase("no")) {
                return false;
            } else {
                System.out.println("Invalid input! Please enter 'yes' or 'no'.");
            }
        }


    }

    public static void main(String[] args) {
        RouletteGame rouletteGame = RouletteGame.getInstance();
        rouletteGame.playGame();
    }
}
