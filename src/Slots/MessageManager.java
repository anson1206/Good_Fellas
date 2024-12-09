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
            // Send the reels update as a message
            observer.update("Reels: " + String.join(", ", reels));
        }
    }

    public void setMessage(String message) {
        for (Observer observer : observers) {
            observer.update(message); // Notify observers with the result message
        }
    }

    public void updateBalance(int balance) {
        // Notify all observers with the balance update as a message
        String balanceMessage = "Chips Available: " + balance;
        for (Observer observer : observers) {
            observer.update(balanceMessage);
        }
    }
}