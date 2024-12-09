//Chase Wink
package RussainRoulette;
//Same use as the Roulette version look at that for more information
public abstract class RussianRouletteGameTemplate {
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

}