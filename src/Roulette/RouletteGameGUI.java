//Chase Wink
package Roulette;

import Casino.BettingChipsMain;
import Casino.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

/***
 * RouletteGameGUI
 * This class is the GUI for the Roulette game.
 * This class is where the player can place bets on the roulette wheel and see the results of the wheel spin.
 * The player can interact with the game and place bets on the wheel.
 * The player can see the results of the wheel spin and see if they won or lost through text and a text box popup
 * The player can see the amount of chips they have and the amount of chips they have won or lost.
 * The player can see min and max bets and then can place a bet by clicking the button, and select the bet they want
 * This whole class is based off of the Template pattern, Singleton pattern, and Observer pattern. I use
 * observer for the notifications that appear in the UI when the player wins/loses and if they made an invalid
 * bet. I use the template pattern for the game logic structuring how the game should operate. Finally, I
 * use the Singleton pattern for only one instance of the class.
 */

//extends RouletteGameTemplate for template and WinNotifier for observer
public class RouletteGameGUI extends RouletteGameTemplate implements WinNotifier {
    //Setting up my variables so that I can use them in my class
    private static RouletteGameGUI instance;
    //for sound
    private PlaySound playSoundInstance = new PlaySound();
    //for observer setting up the list that we can add too
    private final List<WinObserver> observers = new ArrayList<>();
    //instance of the wheelSpin class
    private wheelSpin wheel;
    //instance of the colorDeterminer class
    private ColorDeterminer colorDeterminer;
    //instance of the BettingChipsMain class
    private BettingChipsMain playerChips;
    //boolean for player wins and if its a validBet
    private boolean playerWins;
    private boolean validBet;
    //int for the winning number and String for the color
    private int winningNumber;
    private String winningColor;
    //variables for the min and max bets
    private int minBet = 10;
    private int maxBet = 500;

    // GUI components
    private JTextArea outputArea;
    private JTextField betAmountField;
    private JButton placeBetButton;
    private JComboBox<String> betTypeComboBox;
    private MainWindow mainWindowTest;
    private JButton mainMenuButton;
    private JLabel chipAmountLabel;
    private JLabel betAmountLabel;
    private JLabel gifLabel;

    // Instance variables for bet details
    private int playerNumberBet = -1;
    private String playerColorBet = "";
    private boolean isEvenBet = false;
    private boolean isLowBet = false;
    private int dozenBet = -1;
    private int columnBet = -1;



    //Initializing the instance of the class in my constructor
    private RouletteGameGUI() {
        this.wheel = new wheelSpin();
        this.colorDeterminer = new ColorDeterminer();
    }
    //This is where I am creating the instance of the class for the Signleton pattern
    public static synchronized RouletteGameGUI getInstance() {
        if (instance == null) {
            instance = new RouletteGameGUI();
        }
        return instance;
    }

    //These methods add, remove, and notify are for the observer pattern. I can add an observer to the
    //list, remove an observer from the list, and notify the observers in the list
    @Override
    public void addObserver(WinObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(WinObserver observer) {
        observers.remove(observer);
    }

    //This is where I am notifying the observers in the list passing these parameters because when I call
    //WinObserver I need to pass these parameters for the checks that I am doing for the popups to show up
    @Override
    public void notifyObservers(boolean playerWins, boolean validBet, int winningNumber, String winningColor) {
        for (WinObserver observer : observers) {
            //this is where I am calling the onPlayerWin method in the WinObserver class which is then
            //implemented in the WinPopup class
            observer.onPlayerWin(playerChips, playerWins, validBet, winningNumber, winningColor);
        }
    }

    /*This class getBetAmount is where I am checking if the bet amount is valid. We are going through
    a series of checks to make sure the bet amount is valid. If the bet amount is not valid then we return
    zero and the player cannot place a bet. If the bet amount is valid then we return the bet amount so
    our other methods can use it. But if its not then we return zero so then the game cannot continue.
    For example, we do the check in playGame to see if the bet amount is greater than zero, if it is not
    then we do not proceed with the game
    */
    @Override
    protected int getBetAmount() {
        try {
            //This is where I am getting the bet amount from the text field from the swing component
            int betAmount = Integer.parseInt(betAmountField.getText());
            //check for the min and max bets
            if(betAmount < minBet){
                outputArea.append("Bet amount is too low! Minimum bet is " + minBet + ".\n");
                return 0;
            }
            else if(betAmount > maxBet){
                outputArea.append("Bet amount is too high! Maximum bet is " + maxBet + ".\n");
                return 0;
            }
            //check if the bet is less than or equal to zero or if its more than the amount of chips the player has
            if (betAmount <= 0 || betAmount > playerChips.getAmount()) {
                outputArea.append("Invalid amount! You have " + playerChips.getAmount() + " chips.\n");
                return 0;
            }
            //if none of these checks then return the amount of the bet since its valid
            return betAmount;
        //if nothing happens above then catch the exception and return zero
        } catch (NumberFormatException e) {
            outputArea.append("Invalid input! Please enter a valid number.\n");
            return 0;
        }
    }
    /*The getBetType method retrieves the selected bet type from the JComboBox component in the
    * GUI. It returns the index of the selected item plus one, which corresponds to the type of bet
    * the player wants to place. This ensures the bet type is correctly identified for the game logic.
    * The reason why we add one is that the index starts at zero, and we want to start at one
    * We return this and then use it in the playGame method to determine the type of bet the player
    * */
    @Override
    protected int getBetType() {
        return betTypeComboBox.getSelectedIndex() + 1;
    }

    /*In this class we are taking in as parameters the betType,amount, and the betdetails which is the
    * string that the user inputs from the java swing text field. We take in these parameters to
    * do a series of different checks for the different types of bets. We use the switch so go between
    * the different cases depending on what that value is. The reason for this class is to append different
    * messages to the user in the outputArea if the input for the bet is wrong or not. We are also
    * showing a pop-up message for the user to read  */
    @Override
    protected boolean placeBet(int betType, int betAmount, String betDetails) {
        //This is where we are setting the text area to be empty everytime this method is called
        outputArea.setText("");
        //Goes to the certain case depending on the betType
        switch (betType) {
            case 1: // Straight-Up Bet
                //This is where we are displaying the message to the user to enter a number between 0 and 36
                String number = JOptionPane.showInputDialog("Enter the number you want to bet on (0-36): ");
                try {
                    //This is where we are parsing the number to an integer if need be
                    playerNumberBet = Integer.parseInt(number);
                    if (playerNumberBet >= 0 && playerNumberBet <= 36) {
                        //setting a flag to keep track
                        validBet = true;
                        //appending in the output area a message error
                    } else {
                        outputArea.append("Invalid number! Must be between 0 and 36.\n");
                    }
                    //if none of those checks are valid then catch the exception and display a message
                } catch (Exception e) {
                    outputArea.append("Invalid input! Please enter a valid number.\n");
                }
                //breaking out of the case
                break;
            case 2: // Even/Odd Bet
                //This is where we are displaying the message to the user to enter even or odd
                String evenOddBet = JOptionPane.showInputDialog("Bet on Even or Odd (Enter 'Even' or 'Odd'): ");
                if ("Even".equalsIgnoreCase(evenOddBet) || "Odd".equalsIgnoreCase(evenOddBet)) {
                    isEvenBet = "Even".equalsIgnoreCase(evenOddBet);
                    //bool to keep track
                    validBet = true;
                } else {
                    //appending in the output area a message error
                    outputArea.append("Invalid input! Enter 'Even' or 'Odd'.\n");
                }
                break;
            case 3: // Red/Black Bet
                //This is where we are displaying the message to the user to enter red or black
                String color = JOptionPane.showInputDialog("Bet on Red or Black (Enter 'Red' or 'Black'): ");
                if ("Red".equalsIgnoreCase(color) || "Black".equalsIgnoreCase(color)) {
                    //setting the color to the color that the user entered
                    playerColorBet = color;
                    //bool to keep track
                    validBet = true;
                    //appending in the output area a message error
                } else {
                    outputArea.append("Invalid color! Enter 'Red' or 'Black'.\n");
                }
                break;
            case 4: // Low/High Bet
                //This is where we are displaying the message to the user to enter low or high
                String lowHighBet = JOptionPane.showInputDialog("Bet on Low (1-18) or High (19-36) (Enter 'Low' or 'Red'): ");
                if ("Low".equalsIgnoreCase(lowHighBet) || "High".equalsIgnoreCase(lowHighBet)) {
                    //setting the low or high bet to the value that the user entered
                    isLowBet = "Low".equalsIgnoreCase(lowHighBet);
                    //bool to keep track
                    validBet = true;
                    //appending in the output area a message error
                } else {
                    outputArea.append("Invalid input! Enter 'Low' or 'High'.\n");
                }
                break;
            case 5: // Dozen Bet
                //This is where we are displaying the message to the user to enter a number between 1 and 3
                String dozenBetStr = JOptionPane.showInputDialog("Bet on Dozen (1 for 1-12, 2 for 13-24, 3 for 25-36): ");
                try {
                    //This is where we are parsing the number to an integer if need be
                    dozenBet = Integer.parseInt(dozenBetStr);
                    //checking if the number is between 1 and 3
                    if (dozenBet >= 1 && dozenBet <= 3) {
                        //bool to keep track
                        validBet = true;
                        //appending in the output area a message error
                    } else {
                        outputArea.append("Invalid bet! Enter '1', '2', or '3'.\n");
                    }
                    //if none of those checks are valid then catch the exception and display a message
                } catch (Exception e) {
                    outputArea.append("Invalid input! Please enter a valid number.\n");
                }
                break;
            case 6: // Column Bet
                //This is where we are displaying the message to the user to enter a number between 1 and 3
                String columnBetStr = JOptionPane.showInputDialog("Bet on Column (1 for first, 2 for second, 3 for third): ");
                try {
                    //This is where we are parsing the number to an integer if need be
                    columnBet = Integer.parseInt(columnBetStr);
                    //checking if the number is between 1 and 3
                    if (columnBet >= 1 && columnBet <= 3) {
                        //bool to keep track
                        validBet = true;
                        //appending in the output area a message error
                    } else {
                        outputArea.append("Invalid bet! Enter '1', '2', or '3'.\n");
                    }
                } catch (Exception e) {
                    outputArea.append("Invalid input! Please enter a valid number.\n");
                }
                break;
        }
        //returning the validBet to see if the bet is valid or not so we can use this in other areas of the code
        return validBet;
    }

    /* Here we are implementing the determineWin method which uses switch cases again and does a series of tests
    * to see if the player won or not. We are taking in the parameter of betType to be able to switch from
    * case to case */
    @Override
    protected boolean determineWin(int betType) {
        //This is where we are spinning the wheel to get the winning number
        winningNumber = wheel.spinWheel();
        //This is where we are determining the color of the number that was spun
        winningColor = colorDeterminer.getColor(winningNumber);
        //appending in the output area the winning number and color
        outputArea.append("\nThe roulette wheel landed on: " + winningNumber + " (" + winningColor + ")\n");
        switch (betType) {
            case 1: // Straight-Up Bet
                playerWins = (playerNumberBet == winningNumber);
                break;
            case 2: // Even/Odd Bet
                //This is where we are checking if the winning number is not zero and if the winning number is even
                playerWins = (winningNumber != 0) && ((winningNumber & 1) == 0) == isEvenBet;
                break;
            case 3: // Red/Black Bet
                //This is where we are checking if the winning color is equal to the color that the player bet on
                playerWins = winningColor.equalsIgnoreCase(playerColorBet);
                break;
            case 4: // Low/High Bet
                //This is where we are checking if the winning number is not zero and if the winning number is less than or equal to 18
                playerWins = (winningNumber != 0) && ((winningNumber <= 18) == isLowBet);
                break;
            case 5: // Dozen Bet
                //This is where we are checking if the winning number is between 1 and 12, 13 and 24, or 25 and 36
                if (dozenBet == 1) {
                    playerWins = (winningNumber >= 1 && winningNumber <= 12);
                } else if (dozenBet == 2) {
                    playerWins = (winningNumber >= 13 && winningNumber <= 24);
                } else if (dozenBet == 3) {
                    playerWins = (winningNumber >= 25 && winningNumber <= 36);
                }
                break;
            case 6: // Column Bet
                //This is where we are checking if the winning number is in the column that the player bet on.
                //Doing this by using modulo to see if the winning number is in the column that the player bet on
                if (columnBet == 1) {
                    playerWins = (winningNumber - 1) % 3 == 0;
                } else if (columnBet == 2) {
                    playerWins = (winningNumber - 2) % 3 == 0;
                } else if (columnBet == 3) {
                    playerWins = (winningNumber - 3) % 3 == 0;
                }
                break;
        }
        //returning if the player wins or not so that we can use this in other parts of code
        return playerWins;
    }

    /* This method is how we customize the different multiplers for each type of bet. Again we take in the
    * parameter of betType which tells us what bet we are using, and then we can switch from case to case and
    * return a number which we can use later in the code to multiply the chips by for the multipler. So we are
    * returning a number here*/
    private int getMultiplier(int betType) {
        //switching from case to case depending on the betType
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

    /*This is where we are updating the chips for the player. We are taking in the parameters of the betAmount,
    * playerWins and betType. By doing this we can print out messages to the output area of the amount of chips
    * we have based off of the multipler that we multiple it by*/
    @Override
    protected void updateChips(int betAmount, boolean playerWins, int betType) {
        //This is where we are intitlaizing the multipler
        int multiplier = getMultiplier(betType);
        //This is where we are checking if the player wins or not and then updating the chips based off of that
        if (playerWins) {
            //This is where we are updating the chips for the player after multipling by the multipler
            playerChips.setAmount(playerChips.getAmount() + (betAmount * multiplier));
            outputArea.append("You win! You now have " + playerChips.getAmount() + " chips.\n");
        //if you lost then subtract that amount and update it
        } else {
            playerChips.setAmount(playerChips.getAmount() - betAmount);
            outputArea.append("You lose! You now have " + playerChips.getAmount() + " chips.\n");

        }
        //This is where we are updating the chip label to show the amount of chips the player has
        chipAmountLabel.setText("Chips: " + playerChips.getAmount());  // Update the chip label
        if (playerChips.getAmount() <= 0) {
            outputArea.append("\nYou have run out of chips! Game over.\n");
        }

    }

    /*This is where we are creating the GUI for the roulette game. We are setting up the frame and the layout
    * here. There is alot going on in this method but the main things that this method does is that it implements
    * the template pattern, makes use of the observer pattern and sets up the UI */
    //Takes in the chips from the BettingChipsMain class which is the amount of chips the player has overall for all games
    public void createAndShowGUI(BettingChipsMain playerChips) {
        //intializing the playerChips
        this.playerChips = playerChips;
        //setting up the frame
        JFrame frame = new JFrame("Roulette Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        // Create a panel for the GIF and place it at the top of output area
        JPanel gifPanel = new JPanel();
        gifLabel = new JLabel(new ImageIcon("src/Roulette/RouletteGIF.gif"));
        gifLabel.setVisible(false); // Initially set the GIF to be invisible
        gifPanel.add(gifLabel);
        frame.add(gifPanel, BorderLayout.NORTH); // Put the GIF in the NORTH region


        // Custom panel with background image using the BackgroundPanel class
        BackgroundPanel backgroundPanel = new BackgroundPanel("src/Roulette/RouletteTablePhoto.jpg");
        backgroundPanel.setLayout(new BorderLayout());

        // Output area
        outputArea = new JTextArea(15, 40);
        outputArea.setOpaque(false); // Make the text area transparent
        outputArea.setEditable(false);
        outputArea.getCaret().setVisible(false);  // Hide the | that says you can type
        outputArea.setForeground(Color.WHITE); // Set the text color to white
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setOpaque(false); // Make the scroll pane transparent
        scrollPane.getViewport().setOpaque(false); //
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);  // Output area will fill the center

        frame.add(backgroundPanel, BorderLayout.CENTER); // Add the background panel to the frame

        // Control panel for the bet and buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(5, 2)); // 5 rows, 2 columns
        // Add the bet amount label and text field
        betAmountLabel = new JLabel("Bet Amount: ");
        controlPanel.add(betAmountLabel);

        // Add the bet amount text field
        betAmountField = new JTextField();
        controlPanel.add(betAmountField);

        // Adding the bet type combo box
        String[] betTypes = { "Straight-Up Bet", "Even/Odd Bet", "Red/Black Bet", "Low/High Bet", "Dozen Bet", "Column Bet" };
        betTypeComboBox = new JComboBox<>(betTypes);
        controlPanel.add(new JLabel("Bet Type: "));
        controlPanel.add(betTypeComboBox);

        // Add the Place Bet button
        placeBetButton = new JButton("Place Bet");
        placeBetButton.setPreferredSize(new Dimension(150, 40)); // Button size adjustment

        //Adding the functionality to the place bet button which calls the playGame method in the template class
        placeBetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validBet = false; // Reset the validBet flag
                playerWins = false; // Reset the playerWins flag
                // Handle the bet logic
                //This is where I implement the template pattern!!!
                playGame();
                //notifyObservers();
                //if the bet is valid then we play the sound and display the gif
                if (validBet){
                playSoundInstance.playSound("src/Roulette/wheel-spin-click-slow-down-101152.wav");
                gifLabel.setVisible(true); // Make the GIF visible when the bet is placed
                     }


                // Create a timer to hide the GIF after 2 seconds
                Timer timer = new Timer(2000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        gifLabel.setVisible(false);
                        // Notify the observers after the GIF is hidden this is where we use observer to notify
                        notifyObservers(playerWins, validBet, winningNumber, winningColor);
                            }
                });
                timer.setRepeats(false); // Only execute once
                timer.start();


            }
        });
        // Add the Place Bet button to the control panel
        controlPanel.add(placeBetButton);

        // Add the chip amount label
        chipAmountLabel = new JLabel("Chips: " + playerChips.getAmount());
        controlPanel.add(chipAmountLabel);

        // Add the Main Menu button
        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setPreferredSize(new Dimension(150, 40)); // Button size adjustment

        //Adding the functionality to the main menu button which takes the player back to the main menu
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hide the current window, update the chips, and show the main menu window
                frame.setVisible(false);
                mainWindowTest.refreshChips();
                if (mainWindowTest != null) {
                    mainWindowTest.setVisible(true);
                }

            }
        });
        // Add the Main Menu button to the control panel
        controlPanel.add(mainMenuButton);

        frame.add(controlPanel, BorderLayout.SOUTH);  // Add the control panel at the bottom

        // Set the frame to be visible
        frame.setVisible(true);

        //Setting the limit label so the user knows
        JLabel betLimitsLabel = new JLabel("Min Bet: 10, Max Bet: 500");
        controlPanel.add(betLimitsLabel);
    }

    //Creating a main here for testing purposes don't technically need this
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RouletteGameGUI gameGUI = RouletteGameGUI.getInstance();
                BettingChipsMain playerChips = new BettingChipsMain(100);
                gameGUI.createAndShowGUI(playerChips);
                gameGUI.addObserver(new WinPopup());
            }
        });
    }
}

