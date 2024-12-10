package Slots;

/*
This class represents the "Wild West" themed slot machine. It extends SlotMachinesTemplate and provides
details specific to the Wild West game, Horse, Sheriff, Bandit, a win multiplier of 5x,
 a minimum bet of $5, and a maximum bet of $25.

 John McIntosh
 */
public class WildWestSlotMachine extends SlotMachinesTemplate {
    // Array of symbols specific to the Wild West slot machine
    private static final String[] WildWest = {"Horse", "Sheriff", "Bandit"};

    // Constructor to initialize the player's starting balance
    public WildWestSlotMachine(int initialBalance) {
        super(initialBalance); // Passes the balance to the parent class constructor
    }

    // Returns the name of this slot machine
    @Override
    protected String getGameName() {
        return "Wild West Slot Machine"; // Name displayed in the UI
    }

    // Returns the symbol at the specified index
    // The symbols are specific to the Wild West theme
    @Override
    protected String getSymbol(int index) {
        return WildWest[index]; // Fetches the symbol from the array based on the index
    }

    // Returns the multiplier for winning
    // This determines how much the player wins based on their bet
    @Override
    protected int getWinMultiplier() {
        return 5; // Winning multiplier is 5x the bet
    }

    // Returns the minimum allowable bet for this slot machine
    @Override
    protected int getBetMinimum() {
        return 5; // Minimum bet is $5
    }

    // Returns the maximum allowable bet for this slot machine
    @Override
    protected int getMaxBet() {
        return 25; // Maximum bet is $25
    }
}
