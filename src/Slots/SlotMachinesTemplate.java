package Slots;
import java.util.Random;
import java.util.Scanner;

abstract class SlotMachinesTemplate {
    protected double balance;

    public SlotMachinesTemplate(double initialBalance) {
        this.balance = initialBalance;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Welcome to the " + getGameName() + "!");
        System.out.println("Reminder Bet Minimum is: " + "$" + getBetMinimum() + " PER SPIN");
        System.out.println("Bet Maximum is: " + "$" + getMaxBet() + " PER SPIN");
        while (balance > 0) {
            System.out.println("Balance: $" + balance);
            System.out.print("Enter your bet (0 to quit): ");
            double bet = scanner.nextDouble();


            if(bet == 0){
                System.out.println("Nothing bet, risk some dough or get out of my casino");
                break;
            }

            if(bet > balance){
                System.out.println("Woah slow down there high roller, go on and visit Lenny the Loan shark" +
                        "if you need some more cash. Tell him Big Mac sent ya");
                break;
            }

            if(bet< getBetMinimum()){
                System.out.println("How many times you have to be told what the minimum is?");
                break;
            }

            if(bet > getMaxBet()){
                System.out.println("Easy there high roller you swing for the fences huh, boy do we got the game" +
                        " for you, go back to the lobby and play \u001B[1m Russian Roulette \u001B[0m");

                break;
            }

            balance -= bet;

            // Spin the reels
            int reel1 = random.nextInt(3); // 0, 1, or 2
            int reel2 = random.nextInt(3);
            int reel3 = random.nextInt(3);

            System.out.println("Reels: [" + getSymbol(reel1) + "][" + getSymbol(reel2) + "][" + getSymbol(reel3) + "]");

            // Check for win
            if (reel1 == reel2 & reel2 == reel3) {
                double winnings = bet * getWinMultiplier(); // Win multiplier based on the game
                balance += winnings;
                System.out.println("You won $" + winnings + "!");
            } else {
                System.out.println("You lost this round");
            }
        }
        if (balance == 0){
            System.out.println("\n\u001B[1mYou are broke, but I know a guy who can fix that... go visit Lenny the Loan Shark, " +
                    "tell him Big Mac sent ya,\nhe's not so fond of new people and he don't trust easy\u001B[0m");
        }
        System.out.println("\nGame over, Final balance: $" + balance);
    }

    protected abstract String getGameName();

    protected abstract String getSymbol(int index);

    protected abstract int getWinMultiplier();

    protected abstract int getBetMinimum();

    protected abstract int getMaxBet();
}
