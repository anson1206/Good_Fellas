package BlackJack;
/***
 * Game UI Class
 * This class handles the UI portion for the game
 * This class also handles the game functionality
 */



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameUI extends JFrame implements Observer {
    private JTextArea gameLog;
    private final Player player;
    private final Dealer dealer;
    private Invoker invoker;
    private Command phitCommand;
    private Command pstandCommand;
    private Command dhitCommand;
    private JPanel playerPanel;
    private JPanel dealerPanel;
    private JButton hitButton;
    private JButton standButton;

    public GameUI(Player player, Dealer dealer, Invoker invoker, Command phitCommand, Command pstandCommand, Command dhitCommand) {
        this.player = player;
        this.dealer = dealer;
        this.invoker = invoker;
        this.phitCommand = phitCommand;
        this.pstandCommand = pstandCommand;
        this.dhitCommand = dhitCommand;

        setTitle("BlackJack Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameLog = new JTextArea();
        gameLog.setEditable(false);
        add(new JScrollPane(gameLog), BorderLayout.CENTER);

        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        add(buttonPanel, BorderLayout.SOUTH);

        playerPanel = new JPanel();
        dealerPanel = new JPanel();
        add(playerPanel, BorderLayout.WEST);
        add(dealerPanel, BorderLayout.EAST);

        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invoker.addCommand(phitCommand);
                invoker.executeCommands();
                invoker.removeCommand(phitCommand);
                updateCardImages();
                gameStatus();
            }
        });

        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invoker.addCommand(pstandCommand);
                invoker.executeCommands();
                invoker.removeCommand(pstandCommand);
                hitButton.setEnabled(false);
                standButton.setEnabled(false);
                if(dealer.getScore() > player.getScore()){
                    update("Dealer wins!");
                } else{
                    //dealer hits until score is 17 or higher
                    while (dealer.getScore() < 17) {
                        invoker.addCommand(dhitCommand);
                        invoker.executeCommands();
                        invoker.removeCommand(dhitCommand);
                        if (dealer.getScore() > player.getScore() && (!dealer.isBusted())) {
                            update("Dealer wins!");
                        } else if (dealer.getScore() == player.getScore()) {
                            update("It's a tie!");
                        } else if (dealer.getScore() < player.getScore() && (!dealer.isBusted())) {
                            update("Player wins!");
                        }
                    }
                }
                updateCardImages();
                gameStatus();
            }
        });
    }
    //checks for black jack and bust
    private void gameStatus() {
        if (player.isBusted()) {
            update("Player is busted. Dealer wins!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
        } else if (dealer.isBusted()) {
            update("Dealer is busted. Player wins!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
        } else if (player.isBlackjack() && dealer.isBlackjack()) {
            update("Both have blackjack! It's a tie!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
        } else if (player.isBlackjack()) {
            update("Player has blackjack!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
        } else if (dealer.isBlackjack()) {
            update("Dealer has blackjack!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
        }
    }

    //updates the game log
    @Override
    public void update(String message) {
        gameLog.append(message + "\n");
    }

    //starts the game
    public void startDeal() {
        player.hit();
        player.hit();
        dealer.hit();
        dealer.hit();
        updateCardImages();
        gameStatus();



    }

    //updates the cards
    private void updateCardImages(){
        playerPanel.removeAll();
        dealerPanel.removeAll();
        for(Card card : player.getHand()){
            try{
            playerPanel.add(new JLabel(card.getCardImage()));
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
        }
        for(Card card : dealer.getHand()){
            try{
            dealerPanel.add(new JLabel(card.getCardImage()));
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
        }
        }
        playerPanel.revalidate();
        playerPanel.repaint();
        dealerPanel.revalidate();
        dealerPanel.repaint();
    }
}