package BlackJack;

import Casino.*;

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
        Player player = new Player( deck, subject);
        Dealer dealer = new Dealer(deck, player, subject);
        Invoker invoker = new Invoker();

        ChipsDirectorMain chipsDirector = new ChipsDirectorMain(new ChipsBuilderMain());
        BettingChipsMain playerChips = chipsDirector.construct(1000);

        Command phitCommand = new PlayerHitCommand(player);
        Command pstandCommand = new PlayerStandCommand(player);
        Command dhitCommand = new DealerHitCommand(dealer);

        player.setHitCommand(phitCommand);
        player.setStandCommand(pstandCommand);
        dealer.setHitCommand(new DealerHitCommand(dealer));

        //BlackJackGameUI window = new BlackJackGameUI(player, dealer, invoker, phitCommand, pstandCommand, dhitCommand, playerChips);
        //subject.add(window);
        //window.setVisible(true);


    }
}

