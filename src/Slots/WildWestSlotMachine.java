package Slots;

// Cowboy Slot Machine
public class WildWestSlotMachine extends SlotMachinesTemplate {
    private static final String[] WildWest = {"Horse", "Sheriff", "Bandit"};

    public WildWestSlotMachine(double initialBalance) {
        super(initialBalance);
    }

    @Override
    protected String getGameName() {
        return
                "Wild West Slot Machine";
    }

    @Override
    protected String getSymbol(int index) {

        return WildWest[index];
    }

    @Override
    protected int getWinMultiplier() {

        return 5; // Win 5x the bet
    }

    @Override
    protected int getBetMinimum(){

        return 5; // $5 minimum bet on spins
    }

    @Override
    protected int getMaxBet() {
        return 25; // $25 maximum bet on spins
    }


}