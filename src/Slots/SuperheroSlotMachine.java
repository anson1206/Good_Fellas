package Slots;

// Superhero Slot Machine
public class SuperheroSlotMachine extends SlotMachinesTemplate {
    private static final String[] Superheroes = {"Batman", "Superman", "Iron Man"};

    public SuperheroSlotMachine(double initialBalance) {

        super(initialBalance);
    }

    @Override
    protected String getGameName() {

        return "Superhero Slot Machine";
    }

    @Override
    protected String getSymbol(int index) {

        return Superheroes[index];
    }

    @Override
    protected int getWinMultiplier() {

        return 10; // Win 10x the bet
    }

    @Override
    protected int getBetMinimum(){

        return 10; // $20 bet minimum
    }

    @Override
    protected int getMaxBet(){

        return 50; //$50 bet max
    }

}