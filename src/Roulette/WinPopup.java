//Chase Wink
package Roulette;
import Casino.BettingChipsMain;

import javax.swing.*;


/* This class serves as a way to implement the observer pattern. In this class we implement the observer pattern
* by doing some checks and depending on the check we display a message to the user */
public class WinPopup implements WinObserver {

    @Override
    public void onPlayerWin(BettingChipsMain playerchips, boolean playerWins, boolean validBet, int winningNumber, String winningColor) {
            //If the player did not place a correct bet shows a message
            if(!validBet){
                JOptionPane.showMessageDialog(null, "You must place a correct bet to play.", "Incorrect Bet", JOptionPane.INFORMATION_MESSAGE);
            }
            //If the player has no more chips to play with and is valid bet shows a message
            else if  (playerchips.getAmount() == 0 && validBet) {
                JOptionPane.showMessageDialog(null, "You have no more chips to play with. Go visit Lenny", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            }
            //If the player lost, and it's a valid bet shows a message
            else if  (!playerWins && validBet) {
                JOptionPane.showMessageDialog(null, "You lost! You now have " + playerchips.getAmount() + " chips.", "Wheel landed on" + " " + winningNumber + " " + "(" + winningColor + ")", JOptionPane.INFORMATION_MESSAGE);
            }
            //If the player won, and it's a valid bet shows a message
            else if  (playerWins && validBet) {
                JOptionPane.showMessageDialog(null, "You won! You now have " + playerchips.getAmount() + " chips.", "Wheel landed on" + " " + winningNumber + " " + "(" + winningColor + ")", JOptionPane.INFORMATION_MESSAGE);
            }


    }

}
