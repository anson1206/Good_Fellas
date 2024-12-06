package BlackJack;

import javax.swing.*;
import java.util.Scanner;



/***
 * Main Class
 * This class is used to represent the client class
 */
public class Main {
    public static void main(String[] args) {

        Deck deck = Deck.getInstance();
        Subject subject = new Subject();
        Player player = new Player("Player", deck, subject);
        Dealer dealer = new Dealer(deck, player, subject);
        Invoker invoker = new Invoker();

        Command phitCommand = new PlayerHitCommand(player);
        Command pstandCommand = new PlayerStandCommand(player);
        Command dhitCommand = new DealerHitCommand(dealer);

        player.setHitCommand(phitCommand);
        player.setStandCommand(pstandCommand);
        dealer.setHitCommand(new DealerHitCommand(dealer));

        GameUI window = new GameUI(player, dealer, invoker, phitCommand, pstandCommand, dhitCommand);
        subject.add(window);
        window.setVisible(true);
        //SwingUtilities.invokeLater(() -> window.startDeal());
      //  window.startDeal();

    }
}


