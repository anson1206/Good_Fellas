package Roulette;
//Trying to incorporate the Template Method Pattern
public abstract class RouletteGameTemplate {
    //Might consider adding the play() method here??
    //Going to change the name of the methods as well for better understanding
    protected abstract int getBetAmount();

    protected abstract int getBetType();

    protected abstract boolean placeBet(int betType, int betAmount);

    protected abstract void updateChips(boolean playerWins, int betAmount, int betType);

    protected abstract boolean askPlayAgain();
}
