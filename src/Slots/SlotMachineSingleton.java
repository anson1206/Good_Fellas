package Slots;

import Casino.BettingChipsMain;
import Casino.MainWindow;

public class SlotMachineSingleton {
    private static SlotMachineSingleton instance;
    private SlotMachineUI slotMachineUI;

    private SlotMachineSingleton() {
        // Private constructor to prevent external instantiation
    }

    public static SlotMachineSingleton getInstance() {
        if (instance == null) {
            instance = new SlotMachineSingleton();
        }
        return instance;
    }

    public void createSlotMachineUI(BettingChipsMain chips, MainWindow mainMenu) {
        if (slotMachineUI == null) {
            slotMachineUI = new SlotMachineUI(chips, mainMenu);
        } else {
            slotMachineUI.refresh1Chips(); // Ensure chip display is up-to-date
            slotMachineUI.setVisible(true);
        }
    }
}
