package Slots;

// Interface for objects that observe and respond to updates from MessageManager

//John McIntosh

public interface Observer {
    void update(String message); // Called when the observer needs to be notified
}
