package Roulette;
import Casino.BettingChipsMain;
import Casino.MainWindowTest;
import javax.swing.*;



public class WinPopup implements WinObserver {

    @Override
    public void onPlayerWin(BettingChipsMain playerchips, boolean playerWins, boolean validBet, int winningNumber, String winningColor) {
            if(!validBet){
                JOptionPane.showMessageDialog(null, "You must place a correct bet to play.", "Incorrect Bet", JOptionPane.INFORMATION_MESSAGE);
            }


            else if  (playerchips.getAmount() == 0 && validBet) {
                JOptionPane.showMessageDialog(null, "You have no more chips to play with. Go visit Lenny", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
            else if  (!playerWins && validBet) {
                JOptionPane.showMessageDialog(null, "You lost! You now have " + playerchips.getAmount() + " chips.", "Wheel landed on" + " " + winningNumber + " " + "(" + winningColor + ")", JOptionPane.INFORMATION_MESSAGE);
            }
            else if  (playerWins && validBet) {
                JOptionPane.showMessageDialog(null, "You won! You now have " + playerchips.getAmount() + " chips.", "Wheel landed on" + " " + winningNumber + " " + "(" + winningColor + ")", JOptionPane.INFORMATION_MESSAGE);
            }


    }

}
