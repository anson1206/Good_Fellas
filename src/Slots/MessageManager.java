package Slots;

import java.util.ArrayList;
import java.util.List;

/*
This class uses the Observer and manages communication between components. It notifies observers
specifically the UI when updates occur, such as reel images changing, messages being displayed, or the
chip balance updating.

John McIntosh
 */

public class MessageManager {
    private List<Observer> observers = new ArrayList<>(); // List of observers

    // Adds a new observer to receive updates
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    // Notifies observers with updated reel images
    public void setReelImages(String[] reels) {
        for (Observer observer : observers) {
            // Convert the reel array into a single message string
            observer.update("Reels: " + String.join(", ", reels));
        }
    }

    // Sends a message like you Win or Lose to observers
    public void setMessage(String message) {
        for (Observer observer : observers) {
            observer.update(message); // Call the observer's update method
        }
    }
}
