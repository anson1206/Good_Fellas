package Roulette;
//Want to implement the observer pattern as well for when player has won
//This interface defines the method that the observer must implement to
//respond to notifications from the subject
public interface WinObserver {
    void onPlayerWin(int chips);
}
