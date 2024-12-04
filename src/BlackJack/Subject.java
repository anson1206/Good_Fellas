package BlackJack;

import java.util.ArrayList;
import java.util.List;

/***
 * Subject Class
 * This class is part of the observer pattern
 * This class is used to represent the publisher
 * This class handles any messages that need to be sent to the observer
 */
public class Subject {

    //observer
    private List<Observer> observers;

    //constructor
    public Subject() {
        this.observers = new ArrayList<>();
    }

    //updates the observer
    public void updateObserver(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
    //adds the observer
    public void add(Observer observer) {
        observers.add(observer);
    }


}
