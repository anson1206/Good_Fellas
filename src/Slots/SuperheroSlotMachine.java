package Slots;

// This class defines the Superhero-themed slot machine
// It extends the abstract class `SlotMachinesTemplate`
public class SuperheroSlotMachine extends SlotMachinesTemplate {
    // Array of symbols specific to the Superhero slot machine
    private static final String[] Superheroes = {"Batman", "Superman", "Ironman"};

    // Constructor to initialize the player's starting balance
    public SuperheroSlotMachine(int initialBalance) {
        super(initialBalance); // Passes the balance to the parent class constructor
    }

    // Returns the name of this slot machine
    @Override
    protected String getGameName() {
        return "Superhero Slot Machine"; // Name displayed in the UI
    }

    // Returns the symbol at the specified index
    // The symbols are specific to the Superhero theme
    @Override
    protected String getSymbol(int index) {
        return Superheroes[index]; // Fetches the symbol from the array based on the index
    }

    // Returns the multiplier for winning
    // This determines how much the player wins based on their bet
    @Override
    protected int getWinMultiplier() {
        return 10; // Winning multiplier is 10x the bet
    }

    // Returns the minimum allowable bet for this slot machine
    @Override
    protected int getBetMinimum() {
        return 10; // Minimum bet is $10
    }

    // Returns the maximum allowable bet for this slot machine
    @Override
    protected int getMaxBet() {
        return 50; // Maximum bet is $50
    }
}
