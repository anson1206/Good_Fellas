//Chase Wink
package RussainRoulette;

import Casino.BettingChipsMain;
import Casino.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
//Same as the Roulette version however, I changed some variables and GUI for the Russian Roulette game
public class RussianRouletteGameGUI extends RussianRouletteGameTemplate implements WinNotifier {
    private static RussianRouletteGameGUI instance;
    private wheelSpin wheel;
    private ColorDeterminer colorDeterminer;
    private final List<WinsObserver> observers = new ArrayList<>();
    private BettingChipsMain playerChips;
    private MainWindow mainWindowTest;
    private boolean playerWins;
    private boolean validBet;
    private int winningNumber;
    private String winningColor;
    private int minBet = 10;
    private int maxBet = 500;

    // GUI components
    private JTextArea outputArea;
    private JTextField betAmountField;
    private JButton placeBetButton;
    private JComboBox<String> betTypeComboBox;

    private JButton mainMenuButton;
    private JLabel chipAmountLabel;
    private JLabel betAmountLabel;

    // Instance variables for bet details

    private String playerColorBet = "";
    private boolean isEvenBet = false;
    private boolean isLowBet = false;
    private JLabel gifLabel;
    private PlaySound playSoundInstance = new PlaySound();


    private RussianRouletteGameGUI() {
        this.wheel = new wheelSpin();
        this.colorDeterminer = new ColorDeterminer();
    }

    public static synchronized RussianRouletteGameGUI getInstance() {
        if (instance == null) {
            instance = new RussianRouletteGameGUI();
        }
        return instance;
    }

    @Override
    public void addsObserver(WinsObserver observer) {

        observers.add(observer);
    }

    @Override
    public void removesObserver(WinsObserver observer) {

        observers.remove(observer);
    }

    @Override
    public void notifyObservers(boolean playerWins, boolean validBet, int winningNumber, String winningColor) {
        for (WinsObserver observer : observers) {
            observer.onPlayerWin(playerChips, playerWins, validBet, winningNumber, winningColor);
        }
    }
    public boolean isObserversEmpty() {
        return observers.isEmpty();
    }

    @Override
    protected int getBetAmount() {
        try {
            int betAmount = Integer.parseInt(betAmountField.getText());
            if(betAmount < minBet){
                outputArea.append("Bet amount is too low! Minimum bet is " + minBet + ".\n");
                return 0;
            }

            if (betAmount <= 0 || betAmount > playerChips.getAmount()) {
                outputArea.append("Invalid amount! You have " + playerChips.getAmount() + " chips.\n");
                return 0;
            }
            return betAmount;

        } catch (NumberFormatException e) {
            outputArea.append("Invalid input! Please enter a valid number.\n");
            return 0;
        }
    }

    @Override
    protected int getBetType() {
        return betTypeComboBox.getSelectedIndex() + 1;
    }

    //Changed this because we want to only check for 3 cases not all the cases like roulette game since
    //This game only has 3 cases to win
    @Override
    protected boolean placeBet(int betType, int betAmount, String betDetails) {
        outputArea.setText("");

        // Handle Even/Odd Bet
        String evenOddBet = JOptionPane.showInputDialog("Bet on Even or Odd (Enter 'Even' or 'Odd'): ");
        if ("Even".equalsIgnoreCase(evenOddBet) || "Odd".equalsIgnoreCase(evenOddBet)) {
            isEvenBet = "Even".equalsIgnoreCase(evenOddBet);
            validBet = true;
        } else {
            outputArea.append("Invalid input! Enter 'Even' or 'Odd'.\n");
            validBet = false;
        }

        // Handle Red/Black Bet
        String color = JOptionPane.showInputDialog("Bet on Red or Black (Enter 'Red' or 'Black'): ");
        if ("Red".equalsIgnoreCase(color) || "Black".equalsIgnoreCase(color)) {
            playerColorBet = color;
            validBet = true;
        } else {
            outputArea.append("Invalid color! Enter 'Red' or 'Black'.\n");
            validBet = false;
        }

        // Handle Low/High Bet
        String lowHighBet = JOptionPane.showInputDialog("Bet on Low (1-18) or High (19-36) (Enter 'Low' or 'High'): ");
        if ("Low".equalsIgnoreCase(lowHighBet) || "High".equalsIgnoreCase(lowHighBet)) {
            isLowBet = "Low".equalsIgnoreCase(lowHighBet);
            validBet = true;
        } else {
            outputArea.append("Invalid input! Enter 'Low' or 'High'.\n");
            validBet = false;
        }

        return validBet;
    }

    //This code is different as well because to determine if the user won or not we are only checking for
    //if the 3 cases are true to determine if there is a Win
    @Override
    protected boolean determineWin(int betType) {

        winningNumber = wheel.spinWheel();
        winningColor = colorDeterminer.getColor(winningNumber);
        outputArea.append("\nThe roulette wheel landed on: " + winningNumber + " (" + winningColor + ")\n");

        // Check Even/Odd Bet
        boolean evenOddWin = (winningNumber != 0) && ((winningNumber & 1) == 0) == isEvenBet;

        // Check Red/Black Bet
        boolean colorWin = winningColor.equalsIgnoreCase(playerColorBet);

        // Check Low/High Bet
        boolean lowHighWin = (winningNumber != 0) && ((winningNumber <= 18) == isLowBet);
        // Player wins if all 3 conditions are true
        playerWins = evenOddWin && colorWin && lowHighWin;
        return playerWins;

    }

    //This code is different as well because we are updating by a multipler of 6 for the Russian Roulette game
    @Override
    protected void updateChips(int betAmount, boolean playerWins, int betType) {

        if (playerWins) {
            playerChips.setAmount(playerChips.getAmount() + (betAmount * 6));
            outputArea.append("You win! You now have " + playerChips.getAmount() + " chips.\n");

        } else {
            playerChips.setAmount(playerChips.getAmount() - betAmount);
            outputArea.append("You lose! You now have " + playerChips.getAmount() + " chips.\n");

        }
        chipAmountLabel.setText("Chips: " + playerChips.getAmount());  // Update the chip label
        if (playerChips.getAmount() <= 0) {
            outputArea.append("\nYou have run out of chips! Game over.\n");
        }

    }
    //Changed a little GUI here for the Roulette
    public void createAndShowGUI(BettingChipsMain playerChips) {
        this.playerChips = playerChips;
        JFrame frame = new JFrame("Russian Roulette Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        // Create a panel for the GIF and place it at the top
        JPanel gifPanel = new JPanel();
        gifLabel = new JLabel(new ImageIcon("src/Roulette/RouletteGIF.gif"));
        gifLabel.setVisible(false); // Initially set the GIF to be invisible
        gifPanel.add(gifLabel);
        frame.add(gifPanel, BorderLayout.NORTH); // Put the GIF in the NORTH region


        // Custom panel with background image
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/Roulette/RouletteTablePhoto.jpg");
        backgroundPanel.setLayout(new BorderLayout());

        // Output area
        outputArea = new JTextArea(15, 40);
        outputArea.setOpaque(false); // Make the text area transparent
        outputArea.setEditable(false);
        outputArea.getCaret().setVisible(false);  // Hide the caret
        outputArea.setForeground(Color.WHITE); // Set the text color to white
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);  // Output area will fill the center

        frame.add(backgroundPanel, BorderLayout.CENTER);

        // Control panel for the bet and buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(5, 2));

        betAmountLabel = new JLabel("Bet Amount: ");
        controlPanel.add(betAmountLabel);

        betAmountField = new JTextField();
        controlPanel.add(betAmountField);

        String[] betTypes = {"Russian Roulette Bet"};
        betTypeComboBox = new JComboBox<>(betTypes);
        controlPanel.add(new JLabel("Bet Type: "));
        controlPanel.add(betTypeComboBox);

        placeBetButton = new JButton("Place Bet");
        placeBetButton.setPreferredSize(new Dimension(150, 40)); // Button size adjustment
        placeBetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validBet = false; // Reset the validBet flag
                playerWins = false; // Reset the playerWins flag
                // Handle the bet logic
                //This is where I implement the template pattern!!!
                playGame();
                //notifyObservers();
                if (validBet){
                playSoundInstance.playSound("src/Roulette/wheel-spin-click-slow-down-101152.wav");
                gifLabel.setVisible(true); // Make the GIF visible when the bet is placed
                     }


                // Create a timer to hide the GIF after 2 seconds
                Timer timer = new Timer(2000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        gifLabel.setVisible(false);
                        notifyObservers(playerWins, validBet, winningNumber, winningColor);
                            }
                });
                timer.setRepeats(false); // Only execute once
                timer.start();


            }
        });
        controlPanel.add(placeBetButton);


        chipAmountLabel = new JLabel("Chips: " + playerChips.getAmount());
        controlPanel.add(chipAmountLabel);
        // Add the Main Menu button
        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setPreferredSize(new Dimension(150, 40)); // Button size adjustment
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                if (mainWindowTest != null) {
                    mainWindowTest.setVisible(true);
                }

            }
        });
        controlPanel.add(mainMenuButton);

        frame.add(controlPanel, BorderLayout.SOUTH);  // Add the control panel at the bottom

        frame.setVisible(true);
        JLabel betLimitsLabel = new JLabel("Min Bet: 10, Max Bet: Infinity");
        controlPanel.add(betLimitsLabel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RussianRouletteGameGUI gameGUI = RussianRouletteGameGUI.getInstance();
                BettingChipsMain playerChips = new BettingChipsMain(100);
                gameGUI.createAndShowGUI(playerChips);
                gameGUI.addsObserver(new WinsPopup());
            }
        });
    }
}

