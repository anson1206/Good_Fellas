package Roulette;

import javax.swing.*;

public class WinPopup implements WinObserver {
    @Override
    public void onPlayerWin(int chips) {
        JOptionPane.showMessageDialog(null, "You won! You now have " + chips + " chips.", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
    }

}
