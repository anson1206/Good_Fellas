package Slots;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {
    private List<Observer> observers = new ArrayList<>();
    private String message;
    private double balance;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void setMessage(String message) {
        this.message = message;
        notifyObservers();
    }

    public void updateBalance(double balance) {
        this.balance = balance;
        notifyObservers();
    }

    public double getBalance() {
        return balance;
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
