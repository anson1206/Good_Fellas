package Slots;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void setReelImages(String[] reels) {
        for (Observer observer : observers) {
            if (observer instanceof SlotMachineUI) {
                ((SlotMachineUI) observer).updateReels(reels); // Update reels in the UI
            }
        }
    }

    public void setMessage(String message) {
        for (Observer observer : observers) {
            observer.update(message); // Notify observers with the result message
        }
    }

    public void updateBalance(double balance) {
        for (Observer observer : observers) {
            if (observer instanceof SlotMachineUI) {
                ((SlotMachineUI) observer).updateBalance(balance); // Notify SlotMachineUI of updated balance
            }
        }
    }

}
