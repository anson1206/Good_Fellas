package Slots;

/*
This abstract class provides the foundation for slot machines, defining the methods each specific slot
machine must implement. These are getGameName, getSymbol, getWinMultiplier, getBetMinimum, and getMaxBet. It
also includes a play method that ensures each slot machine adheres to a consistent structure.

John McIntosh
 */

// Abstract class to define a template for slot machine games
abstract class SlotMachinesTemplate {
    // Holds the current balance of the player
    protected int balance;

    // Constructor to initialize the balance
    public SlotMachinesTemplate(int initialBalance) {
        this.balance = initialBalance;
    }

    // Template method defining the sequence of game actions
    public void play() {
        getGameName();       // Get the name of the slot machine
        getMaxBet();         // Get the maximum allowable bet
        getBetMinimum();     // Get the minimum allowable bet
        getWinMultiplier();  // Get the multiplier for wins
    }

    // Abstract method to get the name of the slot machine
    protected abstract String getGameName();

    // Abstract method to get the symbol at a specific index
    protected abstract String getSymbol(int index);

    // Abstract method to get the multiplier for winning
    protected abstract int getWinMultiplier();

    // Abstract method to get the minimum allowable bet
    protected abstract int getBetMinimum();

    // Abstract method to get the maximum allowable bet
    protected abstract int getMaxBet();
}
