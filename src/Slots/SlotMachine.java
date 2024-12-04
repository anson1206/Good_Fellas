package Slots;

import java.util.Random;
import java.util.Scanner;

public class SlotMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        double balance = 100;

        System.out.println("Welcome to Goodfellas Slots!");
        while (balance > 0) {
            System.out.println("Balance: $" + balance);
            System.out.print("Enter your bet (0 to quit): ");
            double bet = scanner.nextDouble();

            if (bet == 0 | bet > balance) {
                break;
            }

            balance -= bet;

            int reel1 = random.nextInt(3);
            int reel2 = random.nextInt(3);
            int reel3 = random.nextInt(3);

            System.out.println("Reels: [" + reel1 + "][" + reel2 + "][" + reel3 + "]");

            if (reel1 == reel2 & reel2 == reel3) {
                double winnings = bet * 5;
                balance += winnings;
                System.out.println("You won $" + winnings);
            } else {
                System.out.println("You lost this round, place your bets");
            }
        }

        System.out.println("Game over! Final balance: $" + balance);
        scanner.close();
    }
}
