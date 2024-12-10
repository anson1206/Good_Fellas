package Slots;

import Casino.BettingChipsMain;
import Casino.MainWindow;

import java.util.Random;

/*
This class handles the core logic of the game. It is responsible for spinning the reels or randomly selecting
 symbols, validating the player's bet, determining if the player wins or loses, and updating the player's
 chip balance based on the game outcome. It interacts with the UI to update the display with the latest
 results.

John McIntosh
 */

public class GameLogic {
    private SlotMachinesTemplate machine; // Current slot machine
    private MessageManager messageManager; // Handles sending messages to UI
    private BettingChipsMain playerChips; // Tracks the player's chips
    private MainWindow main; // Reference to the main menu window

    // Constructor links the game logic to the current slot machine, message manager, Main Casino, and chips
    public GameLogic(SlotMachinesTemplate machine, MessageManager messageManager, BettingChipsMain playerChips, MainWindow main) {
        this.machine = machine;
        this.messageManager = messageManager;
        this.playerChips = playerChips;
        this.main = main;
    }

    // Spins the slot machine reels and generates random numbers
    public String[] spinReels() {
        Random random = new Random(); // Random number generator
        String[] reels = new String[3]; // Array to store reel symbols
        for (int i = 0; i < 3; i++) {
            // Randomly pick a symbol index and retrieve the corresponding symbol
            int randomIndex = random.nextInt(3); // Generate a number between 0 and 2
            reels[i] = machine.getSymbol(randomIndex); // Get symbol from slot machine
        }
        return reels; // Return the array of reel symbols
    }

    // Validates the player's bet to ensure it meets the slot machine's rules
    public boolean validateBet(int bet) {
        // Check if the bet is less than the minimum allowed
        if (bet < machine.getBetMinimum()) {
            messageManager.setMessage("Bet below minimum! Bet must be at least $" + machine.getBetMinimum());
            return false;
        }

        // Check if the bet exceeds the maximum allowed
        if (bet > machine.getMaxBet()) {
            messageManager.setMessage("Bet exceeds maximum! Bet must be below $" + machine.getMaxBet());
            return false;
        }

        // Check if the player has enough chips for the bet
        if (bet > main.playerChips.getAmount()) {
            messageManager.setMessage("Bet exceeds your current chip balance of $" + playerChips.getAmount() + "\nGo Visit Lenny the Loan Shark");
            return false;
        }

        return true; // Bet is valid
    }

    // Runs the game logic subtract the bet, check for a win, and updates chips
    public String playGame(int bet, String[] reels) {
        // Deduct the bet amount from the player's chips
        main.playerChips.removeAmount(bet);

        // Check if all three reel symbols match (winning condition)
        if (reels[0].equals(reels[1]) && reels[1].equals(reels[2])) {
            int winnings = bet * machine.getWinMultiplier(); // Calculate winnings
            main.playerChips.addAmount(winnings); // Add winnings to the player's chips
            return "You won $" + winnings + "! Current chips: $" + main.playerChips.getAmount();
        } else {
            // Player loses, return a message with the remaining chip amount
            return "You lost. Current chips: $" + main.playerChips.getAmount();
        }
    }

    // Updates the UI with the new reel images
    public void updateReels(String[] reels) {
        messageManager.setReelImages(reels); // Notify observers (UI) of the new symbols
    }
}
