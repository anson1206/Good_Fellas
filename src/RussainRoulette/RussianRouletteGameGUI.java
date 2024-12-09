
package RussainRoulette;

import Casino.BettingChipsMain;
import Casino.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
    private int playerNumberBet = -1;
    private String playerColorBet = "";
    private boolean isEvenBet = false;
    private boolean isLowBet = false;
    private int dozenBet = -1;
    private int columnBet = -1;
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

    @Override
    protected int getBetAmount() {
        try {
            int betAmount = Integer.parseInt(betAmountField.getText());
            if(betAmount < minBet){
                outputArea.append("Bet amount is too low! Minimum bet is " + minBet + ".\n");
                return 0;
            }
            else if(betAmount > maxBet){
                outputArea.append("Bet amount is too high! Maximum bet is " + maxBet + ".\n");
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

    @Override
    protected boolean placeBet(int betType, int betAmount, String betDetails) {
        outputArea.setText("");

        switch (betType) {
            case 1: // Straight-Up Bet
                String number = JOptionPane.showInputDialog("Enter the number you want to bet on (0-36): ");
                try {
                    playerNumberBet = Integer.parseInt(number);
                    if (playerNumberBet >= 0 && playerNumberBet <= 36) {
                        validBet = true;
                    } else {
                        outputArea.append("Invalid number! Must be between 0 and 36.\n");
                    }
                } catch (Exception e) {
                    outputArea.append("Invalid input! Please enter a valid number.\n");
                }
                break;
            case 2: // Even/Odd Bet
                String evenOddBet = JOptionPane.showInputDialog("Bet on Even or Odd (Enter 'Even' or 'Odd'): ");
                if ("Even".equalsIgnoreCase(evenOddBet) || "Odd".equalsIgnoreCase(evenOddBet)) {
                    isEvenBet = "Even".equalsIgnoreCase(evenOddBet);
                    validBet = true;
                } else {
                    outputArea.append("Invalid input! Enter 'Even' or 'Odd'.\n");
                }
                break;
            case 3: // Red/Black Bet
                String color = JOptionPane.showInputDialog("Bet on Red or Black (Enter 'Red' or 'Black'): ");
                if ("Red".equalsIgnoreCase(color) || "Black".equalsIgnoreCase(color)) {
                    playerColorBet = color;
                    validBet = true;
                } else {
                    outputArea.append("Invalid color! Enter 'Red' or 'Black'.\n");
                }
                break;
            case 4: // Low/High Bet
                String lowHighBet = JOptionPane.showInputDialog("Bet on Low (1-18) or High (19-36) (Enter 'Low' or 'Red'): ");
                if ("Low".equalsIgnoreCase(lowHighBet) || "High".equalsIgnoreCase(lowHighBet)) {
                    isLowBet = "Low".equalsIgnoreCase(lowHighBet);
                    validBet = true;
                } else {
                    outputArea.append("Invalid input! Enter 'Low' or 'High'.\n");
                }
                break;
            case 5: // Dozen Bet
                String dozenBetStr = JOptionPane.showInputDialog("Bet on Dozen (1 for 1-12, 2 for 13-24, 3 for 25-36): ");
                try {
                    dozenBet = Integer.parseInt(dozenBetStr);
                    if (dozenBet >= 1 && dozenBet <= 3) {
                        validBet = true;
                    } else {
                        outputArea.append("Invalid bet! Enter '1', '2', or '3'.\n");
                    }
                } catch (Exception e) {
                    outputArea.append("Invalid input! Please enter a valid number.\n");
                }
                break;
            case 6: // Column Bet
                String columnBetStr = JOptionPane.showInputDialog("Bet on Column (1 for first, 2 for second, 3 for third): ");
                try {
                    columnBet = Integer.parseInt(columnBetStr);
                    if (columnBet >= 1 && columnBet <= 3) {
                        validBet = true;
                    } else {
                        outputArea.append("Invalid bet! Enter '1', '2', or '3'.\n");
                    }
                } catch (Exception e) {
                    outputArea.append("Invalid input! Please enter a valid number.\n");
                }
                break;
        }
        return validBet;
    }

    @Override
    protected boolean determineWin(int betType) {
        winningNumber = wheel.spinWheel();

        winningColor = colorDeterminer.getColor(winningNumber);
        outputArea.append("\nThe roulette wheel landed on: " + winningNumber + " (" + winningColor + ")\n");
        switch (betType) {
            case 1: // Straight-Up Bet
                playerWins = (playerNumberBet == winningNumber);
                break;
            case 2: // Even/Odd Bet
                playerWins = (winningNumber != 0) && ((winningNumber & 1) == 0) == isEvenBet;
                break;
            case 3: // Red/Black Bet
                playerWins = winningColor.equalsIgnoreCase(playerColorBet);
                break;
            case 4: // Low/High Bet
                playerWins = (winningNumber != 0) && ((winningNumber <= 18) == isLowBet);
                break;
            case 5: // Dozen Bet
                if (dozenBet == 1) {
                    playerWins = (winningNumber >= 1 && winningNumber <= 12);
                } else if (dozenBet == 2) {
                    playerWins = (winningNumber >= 13 && winningNumber <= 24);
                } else if (dozenBet == 3) {
                    playerWins = (winningNumber >= 25 && winningNumber <= 36);
                }
                break;
            case 6: // Column Bet
                if (columnBet == 1) {
                    playerWins = (winningNumber - 1) % 3 == 0;
                } else if (columnBet == 2) {
                    playerWins = (winningNumber - 2) % 3 == 0;
                } else if (columnBet == 3) {
                    playerWins = (winningNumber - 3) % 3 == 0;
                }
                break;
        }
        return playerWins;
    }

    private int getMultiplier(int betType) {
        switch (betType) {
            case 1: // Straight-Up Bet
                return 35;
            case 2: // Even/Odd Bet
                return 2;
            case 3: // Red/Black Bet
                return 2;
            case 4: // Low/High Bet
                return 2;
            case 5: // Dozen Bet
                return 3;
            case 6: // Column Bet
                return 3;
            default:
                return 1;
        }
    }

    @Override
    protected void updateChips(int betAmount, boolean playerWins, int betType) {
        int multiplier = getMultiplier(betType);
        if (playerWins) {
            playerChips.setAmount(playerChips.getAmount() + (betAmount * multiplier));
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

    /*public void returnToMainMenu() {
        frame.setVisible(false);
        mainMenu.setVisible(true);
    } */
//MainWindowTest mainMenu
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

        String[] betTypes = { "Straight-Up Bet", "Even/Odd Bet", "Red/Black Bet", "Low/High Bet", "Dozen Bet", "Column Bet" };
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
                playSoundInstance.playSound("src/Roulette/wheel-spin-click-slow-down-101152.wav");
                gifLabel.setVisible(true); // Make the GIF visible when the bet is placed


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

