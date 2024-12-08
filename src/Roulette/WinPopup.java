package Roulette;
import Casino.BettingChipsMain;
import Casino.MainWindowTest;
import javax.swing.*;



public class WinPopup implements WinObserver {

    @Override
    public void onPlayerWin(BettingChipsMain playerchips, boolean playerWins) {
        if (playerchips.getAmount() == 0) {
            JOptionPane.showMessageDialog(null, "You have no more chips to play with. Game over.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        if (!playerWins){
            JOptionPane.showMessageDialog(null, "You lost! You now have " + playerchips.getAmount() + " chips.", "Better luck next time", JOptionPane.INFORMATION_MESSAGE);
        }
        if (playerWins){
        JOptionPane.showMessageDialog(null, "You won! You now have " + playerchips.getAmount() + " chips.", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
    }


    }
}
