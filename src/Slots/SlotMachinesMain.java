package Slots;

import java.util.Scanner;

// Main Class to Run the Games
public class SlotMachinesMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SlotMachinesTemplate slotMachine;

        System.out.println("Welcome to the Slot Machine Game!");
        System.out.println("\nChoose a game to play:");
        System.out.println("\n1: Cowboy Slot Machine \nPayout: 5x Multiplier \nBet Minimum: $5 per spin \nBet Maximum: $25 per spin");
        System.out.println("\n2: Superhero Slot Machine \nPayout: 10x Multiplier \nBet Minimum: $10 per spin \nBet Maximum: $50 per spin");
        System.out.println("\nAny Other Number: Go back to main menu");
        System.out.print("\nEnter your choice: ");
        int choice = scanner.nextInt();


        if (choice == 1) {
            slotMachine = new WildWestSlotMachine(100.0);
        } else if (choice == 2) {
            slotMachine = new SuperheroSlotMachine(100.0);
        } else {
            System.out.println("Exiting game");
            scanner.close();
            return;
        }

        slotMachine.play();
        scanner.close();
    }
}
