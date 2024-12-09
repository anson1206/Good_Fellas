package BlackJack;
/***
 * Game UI Class
 * This class handles the UI portion for the game
 * This class also handles the game functionality
 * Anson Graumann
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Casino.BettingChipsMain;
import Casino.*;
public class BlackJackGameUI extends JFrame implements Observer {
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
    private JButton mainScreenButton;
    private JButton betButton;
    private JTextField betField;
    private static BettingChipsMain playerChips;
    private int currentBet;
    private MainWindow mainWindowTest;

    public BlackJackGameUI(Player player, Dealer dealer, Invoker invoker, Command phitCommand, Command pstandCommand, Command dhitCommand
            , BettingChipsMain playerChips, MainWindow mainWindowTest) {
        this.player = player;
        this.dealer = dealer;
        this.invoker = invoker;
        this.phitCommand = phitCommand;
        this.pstandCommand = pstandCommand;
        this.dhitCommand = dhitCommand;
        this.playerChips = playerChips;
        this.mainWindowTest = mainWindowTest;


        //creates the game UI
        setTitle("BlackJack Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameLog = new JTextArea();
        gameLog.setEditable(false);
        add(new JScrollPane(gameLog), BorderLayout.NORTH);

        //creates the hit and stand buttons
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        add(buttonPanel, BorderLayout.SOUTH);
        hitButton.setEnabled(false);
        standButton.setEnabled(false);

        //creates the player and dealer panels
        playerPanel = new JPanel();
        dealerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.X_AXIS));
        dealerPanel.setLayout(new BoxLayout(dealerPanel, BoxLayout.X_AXIS));
        playerPanel.setBackground(Color.GREEN);
        dealerPanel.setBackground(Color.GREEN);

        //creates the player and dealer name panels
        JPanel playerNamePanel = new JPanel();
        JPanel dealerNamePanel = new JPanel();
        playerNamePanel.add(new JLabel("Player"));
        dealerNamePanel.add(new JLabel("Dealer"));

        //shows the dealer and player cards like a traditional blackjack game
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.add(dealerPanel);
        mainPanel.add(playerPanel);
        add(mainPanel, BorderLayout.CENTER);


        //creates the bet area and button
        betField = new JTextField(5);
        betButton = new JButton("Bet. Min bet is 20 chips.");
        JPanel betPanel = new JPanel();
        betPanel.add(betField);
        betPanel.add(betButton);
        buttonPanel.add(betPanel);

        //main screen button
        mainScreenButton = new JButton("Main Screen");
        buttonPanel.add(mainScreenButton);

        //hit button action listener
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

        //stand button action listener
        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invoker.addCommand(pstandCommand);
                invoker.executeCommands();
                invoker.removeCommand(pstandCommand);
                hitButton.setEnabled(false);
                standButton.setEnabled(false);
                if (dealer.getScore() > player.getScore()) {
                    update("Dealer wins!");
                    updateBetAmount(false);

                } else {
                    //dealer hits until score is 17 or higher
                    while (dealer.getScore() < 17) {
                        invoker.addCommand(dhitCommand);
                        invoker.executeCommands();
                        invoker.removeCommand(dhitCommand);

                    }
                    if (dealer.getScore() > player.getScore() && (!dealer.isBusted())) {
                        update("Dealer wins!");
                        updateBetAmount(false);

                    }
                }
                if (dealer.getScore() == player.getScore()) {
                    update("It's a tie!");
                    updateBetAmount(false);
                } else if (dealer.getScore() < player.getScore() && (!dealer.isBusted())) {
                    update("Player wins!");
                    updateBetAmount(true);

                }
                updateCardImages();
                gameStatus();
            }
        });

        //bet button action listener
        betButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int betAmount = Integer.parseInt(betField.getText());
                if (playerChips != null) {
                    if (betAmount > playerChips.getAmount() || betAmount < 20) {
                        update("total chips" + playerChips.getAmount());
                        update("Bet amount: " + betAmount);
                        update("You do not have enough chips to bet that amount.");
                    } else {
                        //resets the game and updates the player's chips
                        resetGame();
                        update("total chips" + playerChips.getAmount());
                        update("Bet amount: " + betAmount);
                        currentBet = betAmount;
                        playerChips.removeAmount(betAmount);
                        update("Chips remaining: " + playerChips.getAmount());
                        betButton.setEnabled(true);
                        betField.setEnabled(true);
                        hitButton.setEnabled(true);
                        standButton.setEnabled(true);
                        startDeal();
                    }
                } else {
                    update("You do not have any chips to bet. Go see Lenny for some spare change.");
                }

            }
        });

        //main screen button action listener
        mainScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
                setVisible(false);
                mainWindowTest.setVisible(true);
            }
        });


    }
    public void updatePlayerChips(BettingChipsMain playerChips){
        this.playerChips = playerChips;
        update("You have " + playerChips.getAmount() + " chips.");    }


    //checks for black jack and bust
    private void gameStatus() {
        if (player.isBusted()) {
            update("Player is busted. Dealer wins!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            updateBetAmount(false);
        } else if (dealer.isBusted()) {
            update("Dealer is busted. Player wins!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            updateBetAmount(true);
        } else if (player.isBlackjack() && dealer.isBlackjack()) {
            update("Both have blackjack! It's a tie!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            updateBetAmount(false);
        } else if (player.isBlackjack()) {
            update("Player has blackjack!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            updateBetAmount(true);
        } else if (dealer.isBlackjack()) {
            update("Dealer has blackjack!");
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            updateBetAmount(false);
        }
    }

    //updates the game log
    @Override
    public void update(String message) {
        gameLog.append(message + "\n");
    }

    //starts the game with an initial deal
    public void startDeal() {
       betButton.setEnabled(false);
        player.hit();
        player.hit();
        dealer.hit();
        dealer.hit();
        updateCardImages();
        gameStatus();
    }

    //resets the game
    public void resetGame(){
        player.getHand().clear();
        dealer.getHand().clear();
        gameLog.setText("");
        playerPanel.removeAll();
        dealerPanel.removeAll();
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        betButton.setEnabled(true);
        betField.setEnabled(true);
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

    public void updateBetAmount(Boolean playerWins) {
        if (playerWins) {
            playerChips.setAmount(playerChips.getAmount() + (currentBet * 2));
            mainWindowTest.updateBalance(playerChips.getAmount());

        }
        update("You have " + playerChips.getAmount() + " chips.");
        currentBet = 0;
        betButton.setEnabled(true);
        betField.setEnabled(true);
    }
}