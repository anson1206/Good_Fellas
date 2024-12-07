package Roulette;
//Trying to incorporate the Template Method Pattern
public abstract class RouletteGameTemplate {
    //Might consider adding the play() method here??
    //Going to change the name of the methods as well for better understanding
    public final void playGame() {
        int betAmount = getBetAmount();
        int betType = getBetType();
        boolean playerWins = placeBet(betType, betAmount);
        updateChips(playerWins, betAmount, betType);
        askPlayAgain();
    }

    protected abstract int getBetAmount();

    protected abstract int getBetType();

    protected abstract boolean placeBet(int betType, int betAmount);

    protected abstract void updateChips(boolean playerWins, int betAmount, int betType);

    protected abstract boolean askPlayAgain();
}