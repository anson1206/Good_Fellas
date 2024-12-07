package Roulette;
//This interface defines methods that a subject like notifier must
//implement to add, remove, and notify observers
public interface WinNotifier {
    void addObserver(WinObserver observer);
    void removeObserver(WinObserver observer);
    void notifyObservers();

}
