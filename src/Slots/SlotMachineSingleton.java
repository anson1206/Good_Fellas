package Slots;

import Casino.BettingChipsMain;
import Casino.MainWindow;

/*
This class ensures there is only one instance of the SlotMachineUI. It uses the Singleton pattern to
manage the slot machine's lifecycle and ensures that the UI is consistent and updated whenever it is
accessed.

John McIntosh
 */

public class SlotMachineSingleton {
    private static SlotMachineSingleton instance; // Single instance of this class
    private SlotMachineUI slotMachineUI; // UI for the slot machine game

    // Private constructor to prevent external instantiation
    private SlotMachineSingleton() {}

    // Returns the single instance of this class
    public static SlotMachineSingleton getInstance() {
        if (instance == null) {
            instance = new SlotMachineSingleton(); // Create the instance if it doesn't exist
        }
        return instance;
    }

    // Creates or displays the slot machine UI
    public void createSlotMachineUI(BettingChipsMain chips, MainWindow mainMenu) {
        if (slotMachineUI == null) {
            // If the UI doesn't exist, create it
            slotMachineUI = new SlotMachineUI(chips, mainMenu);
        } else {
            // Refresh the UI to ensure it's updated with the correct amount of chips
            slotMachineUI.refresh1Chips();
            slotMachineUI.setVisible(true); // Make the UI visible
        }
    }
}
