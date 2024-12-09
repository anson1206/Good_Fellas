package RussainRoulette;

import Roulette.WinObserver;

//This interface defines methods that a subject like notifier must
//implement to add, remove, and notify observers
public interface WinNotifier {
    void addsObserver(WinsObserver observer);
    void removesObserver(WinsObserver observer);
    void notifyObservers(boolean playerWins, boolean validBet, int winningNumber, String winningColor);

}
