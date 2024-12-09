//Chase Wink
package RussainRoulette;

import Casino.BettingChipsMain;
//Same as the Roulette version look at that for more information
//Want to implement the observer pattern as well for when player has won
//This interface defines the method that the observer must implement to
//respond to notifications from the subject
public interface WinsObserver {
    void onPlayerWin(BettingChipsMain playerChips, boolean playerWins, boolean validBet, int winningNumber, String winningColor);
}
