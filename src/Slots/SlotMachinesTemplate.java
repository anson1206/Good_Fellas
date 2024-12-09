package Slots;

abstract class SlotMachinesTemplate {
    protected int balance;

    public SlotMachinesTemplate(int initialBalance) {
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

    //protected abstract String getImagePath(int index);
}