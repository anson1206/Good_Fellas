package Slots;

import Casino.BettingChipsMain;
import Casino.MainWindow;

import javax.swing.*;

public class SlotMachinesMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create dummy instances for the required arguments
            BettingChipsMain chips = new BettingChipsMain(50); // Start with 50 chips
            MainWindow mainMenu = new MainWindow(); // Create the main menu window

            // Pass the arguments to the SlotMachineUI constructor
            new SlotMachineUI(chips, mainMenu);
        });
    }
}
