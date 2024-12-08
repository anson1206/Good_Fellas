package Slots;

import Casino.BettingChipsMain;
import Casino.MainWindowTest;

import javax.swing.*;

public class SlotMachinesMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create dummy instances for the required arguments
            BettingChipsMain chips = new BettingChipsMain(100); // Start with 100 chips
            MainWindowTest mainMenu = new MainWindowTest(); // Create the main menu window

            // Pass the arguments to the SlotMachineUI constructor
            new SlotMachineUI(chips, mainMenu);
        });
    }
}
