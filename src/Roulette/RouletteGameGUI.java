
/*package Roulette;
import javax.swing.*;

public class RouletteGameGUI {
     public static void main(String[] args) {
        // Create a JFrame
        JFrame frame = new JFrame("GIF Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        // Load the GIF image
        ImageIcon gifIcon = new ImageIcon("src/Roulette/RouletteGIF.gif");
        // Add the GIF to a JLabel
        JLabel gifLabel = new JLabel(gifIcon);

        // Add the JLabel to the JFrame
        frame.add(gifLabel);

        // Make the frame visible
        frame.setVisible(true);
    }
}*/
package Roulette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class RouletteGameGUI extends RouletteGameTemplate implements WinNotifier {
    private static RouletteGameGUI instance;
    private wheelSpin wheel;
    private int chips = 100;
    private ColorDeterminer colorDeterminer;
    private final List<WinObserver> observers = new ArrayList<>();

    // GUI components
    private JTextArea outputArea;
    private JTextField betAmountField;
    private JButton placeBetButton;
    private JComboBox<String> betTypeComboBox;
    private JButton playAgainButton;
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


    private RouletteGameGUI() {
        this.wheel = new wheelSpin();
        this.colorDeterminer = new ColorDeterminer();
    }

    public static synchronized RouletteGameGUI getInstance() {
        if (instance == null) {
            instance = new RouletteGameGUI();
        }
        return instance;
    }

    @Override
    public void addObserver(WinObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(WinObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (WinObserver observer : observers) {
            observer.onPlayerWin(chips);
        }
    }

    @Override
    protected int getBetAmount() {
        try {
            int betAmount = Integer.parseInt(betAmountField.getText());
            if (betAmount <= 0 || betAmount > chips) {
                outputArea.append("Invalid amount! You have " + chips + " chips.\n");
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
        boolean validBet = false;
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
                String lowHighBet = JOptionPane.showInputDialog("Bet on Low (1-18) or High (19-36): ");
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
        int winningNumber = wheel.spinWheel();
        String winningColor = colorDeterminer.getColor(winningNumber);
        outputArea.append("\nThe roulette wheel landed on: " + winningNumber + " (" + winningColor + ")\n");
        boolean playerWins = false;
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

    public void resetGame() {
        chips = 100;
        playerNumberBet = -1;
        playerColorBet = "";
        isEvenBet = false;
        isLowBet = false;
        dozenBet = -1;
        columnBet = -1;
    }

    public int getChips() {
        return chips;
    }

    @Override
    protected void updateChips(int betAmount, boolean playerWins) {
        if (playerWins) {
            chips += betAmount;
            outputArea.append("You win! You now have " + chips + " chips.\n");
        } else {
            chips -= betAmount;
            outputArea.append("You lose! You now have " + chips + " chips.\n");
        }
        chipAmountLabel.setText("Chips: " + chips);  // Update the chip label
    }

    @Override
    protected boolean askPlayAgain() {
        int response = JOptionPane.showConfirmDialog(null, "Would you like to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
        return response == JOptionPane.YES_OPTION;
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Roulette Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        // In your createAndShowGUI method:
        gifLabel = new JLabel(new ImageIcon("src/Roulette/RouletteGIF.gif"));
        gifLabel.setVisible(true);  // Ensure the GIF is visible
        frame.add(gifLabel, BorderLayout.CENTER);  // Add the GIF label to the frame

        frame.revalidate(); // Refresh the frame's layout
        frame.repaint();    // Ensure the content is rendered properly


        outputArea = new JTextArea(15, 40);
        outputArea.setEditable(false);
        outputArea.getCaret().setVisible(false);  // Hide the caret
        JScrollPane scrollPane = new JScrollPane(outputArea);
        frame.add(scrollPane, BorderLayout.CENTER);

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
        placeBetButton.setPreferredSize(new Dimension(150, 40)); // Making the button smaller
        placeBetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the GIF for 2 seconds
                gifLabel.setVisible(true);
                Timer timer = new Timer(2000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        gifLabel.setVisible(false);  // Hide the GIF after 2 seconds
                    }
                });
                timer.setRepeats(false);  // The timer should only run once
                timer.start();

                // Continue with the regular bet process
                int betAmount = getBetAmount();
                if (betAmount > 0) {
                    int betType = getBetType();
                    if (placeBet(betType, betAmount, "")) {
                        boolean playerWins = determineWin(betType);
                        updateChips(betAmount, playerWins);
                        notifyObservers();
                    }
                }
            }
        });

        controlPanel.add(placeBetButton);

        playAgainButton = new JButton("Play Again");
        playAgainButton.setPreferredSize(new Dimension(150, 40)); // Making the button smaller
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
                chipAmountLabel.setText("Chips: " + chips);  // Update chip amount
            }
        });
        controlPanel.add(playAgainButton);

        chipAmountLabel = new JLabel("Chips: " + chips);
        controlPanel.add(chipAmountLabel);

        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RouletteGameGUI gameGUI = RouletteGameGUI.getInstance();
                gameGUI.createAndShowGUI();
            }
        });
    }
}

