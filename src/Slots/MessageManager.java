package Slots;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;

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

    public ImageIcon getImageIcon(String symbol) {
        switch (symbol) {
            case "Batman":
                return new ImageIcon("src/Slots/Images/batman.png");
                case "Superman":
                    return new ImageIcon("src/Slots/Images/superman.png");
                case "Iron Man":
                    return new ImageIcon("src/Slots/Images/ironman.png");
                case "Horse":
                    return new ImageIcon("src/Slots/Images/horse.png");
                case "Sheriff":
                    return new ImageIcon("src/Slots/Images/sheriff.png");
                case "Bandit":
                    return new ImageIcon("src/Slots/Images/bandit.png");
                default:
                    return new ImageIcon(new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB)); // Placeholder
            }
        }
    }
