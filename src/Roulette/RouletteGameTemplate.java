package Roulette;

public abstract class RouletteGameTemplate {
    public final void playGame() {
        int betAmount = getBetAmount();
        if (betAmount > 0) {
            int betType = getBetType();
            if (placeBet(betType, betAmount, "")) {
                boolean playerWins = determineWin(betType);
                updateChips(betAmount, playerWins, betType);

            }
        }
    }

    protected abstract int getBetAmount();
    protected abstract int getBetType();
    protected abstract boolean placeBet(int betType, int betAmount, String betDetails);
    protected abstract boolean determineWin(int betType);
    protected abstract void updateChips(int betAmount, boolean playerWins, int betType);
    protected abstract boolean askPlayAgain();
}