package BlackJack;
/***
 * BlackJackGameUI class
 * This class is used to represent the BlackJackGameUI
 * This class handles the game functionality
 * THis class handles how the game is displayed and the chips being updated for other games to use
 * Anson Graumann
 */

import javax.swing.*;
import java.awt.*;
import Casino.BettingChipsMain;
import Casino.MainWindow;

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
    private JLabel balanceLabel;
    private BettingChipsMain playerChips;
    private int currentBet;
    private MainWindow mainWindowTest;

    public BlackJackGameUI(Player player, Dealer dealer, Invoker invoker, Command phitCommand,
                           Command pstandCommand, Command dhitCommand, BettingChipsMain playerChips,
                           MainWindow mainWindowTest) {
        this.player = player;
        this.dealer = dealer;
        this.invoker = invoker;
        this.phitCommand = phitCommand;
        this.pstandCommand = pstandCommand;
        this.dhitCommand = dhitCommand;
        this.playerChips = playerChips;
        this.mainWindowTest = mainWindowTest;

        playerChips = mainWindowTest.playerChips;


        // Set up the frame
        setTitle("BlackJack Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameLog = new JTextArea();
        gameLog.setEditable(false);
        add(new JScrollPane(gameLog), BorderLayout.NORTH);

        // Buttons and panels
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        add(buttonPanel, BorderLayout.SOUTH);
        hitButton.setEnabled(false);
        standButton.setEnabled(false);

        //playerPanel and dealerPanel
        playerPanel = new JPanel();
        dealerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.X_AXIS));
        dealerPanel.setLayout(new BoxLayout(dealerPanel, BoxLayout.X_AXIS));
        playerPanel.setBackground(new Color(0, 100, 0)); // Dark green
        dealerPanel.setBackground(new Color(0, 100, 0)); // Dark green

        //main panel
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.add(dealerPanel);
        mainPanel.add(playerPanel);
        add(mainPanel, BorderLayout.CENTER);

        //bet panel
        betField = new JTextField(5);
        betButton = new JButton("Bet. Min bet:20 chips Max bet: 100.");
        JPanel betPanel = new JPanel();
        betPanel.add(betField);
        betPanel.add(betButton);
        buttonPanel.add(betPanel);

        //main screen button
        mainScreenButton = new JButton("Main Screen");
        buttonPanel.add(mainScreenButton);

        //balance label
        balanceLabel = new JLabel("Balance: " + playerChips.getAmount() + " chips");
        buttonPanel.add(balanceLabel);

        // Hit button action listener
        hitButton.addActionListener(e -> {
            invoker.addCommand(phitCommand);
            invoker.executeCommands();
            invoker.removeCommand(phitCommand);
            updateCardImages();
            gameStatus();
        });

        // Stand button action listener
        standButton.addActionListener(e -> {
            invoker.addCommand(pstandCommand);
            invoker.executeCommands();
            invoker.removeCommand(pstandCommand);
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
            // Dealer's turn
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

    });

        // Bet button action listener
        betButton.addActionListener(e -> {
            try {
                //gets the amount the player wants to bet and checks if it is valid bet
                int betAmount = Integer.parseInt(betField.getText());
                if (betAmount > mainWindowTest.playerChips.getAmount()) {
                    update("You do not have enough chips to bet that amount.");
                } else if (betAmount < 20) {
                    update("A minimum of a 20 chip bet is required.");
                } else if(betAmount > 100){
                    update("A maximum of a 100 chip bet is allowed.");
                }
                else {
                    // Deduct bet and start the game
                    currentBet = betAmount;
                    mainWindowTest.playerChips.removeAmount(betAmount);
                    update("You bet " + betAmount + " chips. Chips remaining: " + mainWindowTest.playerChips.getAmount());
                    refreshChips();
                    resetGame();
                    hitButton.setEnabled(true);
                    standButton.setEnabled(true);
                    betButton.setEnabled(true);
                    betField.setEnabled(true);
                    startDeal();
                }
            } catch (NumberFormatException ex) {
                update("Please enter a valid bet amount.");
            }
        });

        // Main screen button action listener
        mainScreenButton.addActionListener(e -> {
            resetGame();
            refreshChips();
            mainWindowTest.refreshChips();
            setVisible(false);
            mainWindowTest.setVisible(true);
        });
    }

    //refreshes the chips
    public void refreshChips() {
        playerChips = mainWindowTest.playerChips; // Synchronize with main window
        balanceLabel.setText("Balance: " + playerChips.getAmount() + " chips");
        update("You now have " + playerChips.getAmount() + " chips available to bet.");
    }


    //updates the the players chips
    public void updateBetAmount(Boolean playerWins) {
        if (playerWins) {
            int winnings = currentBet * 2;
            playerChips.addAmount(winnings);
            update("You won " + winnings + " chips! Total chips: " + playerChips.getAmount());
        } else {
            update("You lost the bet. Total chips: " + playerChips.getAmount());
        }
        mainWindowTest.refreshChips();
        currentBet = 0;
        refreshChips();
        betButton.setEnabled(true);
        betField.setEnabled(true);
    }

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


    //updates the card images
    private void updateCardImages() {
        playerPanel.removeAll();
        dealerPanel.removeAll();
        // Add card images to the panels
        for (Card card : player.getHand()) {
            playerPanel.add(new JLabel(card.getCardImage()));
        }
        for (Card card : dealer.getHand()) {
            dealerPanel.add(new JLabel(card.getCardImage()));
        }
        playerPanel.revalidate();
        playerPanel.repaint();
        dealerPanel.revalidate();
        dealerPanel.repaint();
    }

    //resets the game
    private void resetGame() {
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

    //updates the game log
    @Override
    public void update(String message) {
        gameLog.append(message + "\n");
    }

    //starts intial deal
    public void startDeal() {
        player.hit();
        player.hit();
        dealer.hit();
        dealer.hit();
        updateCardImages();
        gameStatus();
    }
}