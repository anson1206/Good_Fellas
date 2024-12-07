package Roulette;

public abstract class RouletteGameTemplate {
    public final void playGame() {
        int betAmount = getBetAmount();
        int betType = getBetType();
        if (placeBet(betType, betAmount, "betDetails")) {
            boolean playerWins = determineWin(betType);
            updateChips(betAmount, playerWins);
        }
        if (askPlayAgain()) {
            playGame();
        } else {
            System.out.println("Thank you for playing!");
        }
    }

    protected abstract int getBetAmount();
    protected abstract int getBetType();
    protected abstract boolean placeBet(int betType, int betAmount, String betDetails);
    protected abstract boolean determineWin(int betType);
    protected abstract void updateChips(int betAmount, boolean playerWins);
    protected abstract boolean askPlayAgain();
}