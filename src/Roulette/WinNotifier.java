//Chase Wink
package Roulette;
//This interface defines methods that a subject like notifier must
//implement to add, remove, and notify observers
public interface WinNotifier {

    void addObserver(WinObserver observer);
    void removeObserver(WinObserver observer);
    //This method will notify all observers of the subject must pass these parameters so we can display messages
    void notifyObservers(boolean playerWins, boolean validBet, int winningNumber, String winningColor);

}
