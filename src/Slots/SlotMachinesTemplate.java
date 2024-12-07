package Slots;
import java.util.Random;
import java.util.Scanner;

abstract class SlotMachinesTemplate {
    protected double balance;

    public SlotMachinesTemplate(double initialBalance) {
        this.balance = initialBalance;
    }

    public void play() {
        getGameName();
        getMaxBet();
        getBetMinimum();
        getWinMultiplier();
    }


    protected abstract String getGameName();

    protected abstract String getSymbol(int index);

    protected abstract int getWinMultiplier();

    protected abstract int getBetMinimum();

    protected abstract int getMaxBet();
}
