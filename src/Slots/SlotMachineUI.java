package Slots;

import Casino.BettingChipsMain;
import Casino.MainWindow;
import Roulette.PlaySound;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

// The main UI class for the slot machine game
public class SlotMachineUI implements Observer {
    // GUI components
    private JFrame frame;                      // Main window frame
    private JPanel reelsPanel;                 // Panel to display slot machine reels
    private JLabel balanceLabel, resultLabel;  // Labels for showing balance and results
    private JLabel reel1Label, reel2Label, reel3Label; // Labels for each slot reel
    private JButton cowboyButton, superheroButton, spinButton, mainMenuButton; // Buttons for actions
    private JTextField betField;               // Field to enter the bet amount

    // Core game objects
    private SlotMachinesTemplate currentMachine;  // The currently selected slot machine
    private GameLogic gameLogic;                  // Handles game logic
    private MessageManager messageManager;        // Manages UI messages
    private Map<String, ImageIcon> symbolImages;  // Maps symbols to images
    private BettingChipsMain playerChips;         // Tracks player's chips
    private MainWindow mainMenu;                  // Reference to the main menu window

    // Sound player for win/loss sounds
    private PlaySound playSoundInstance = new PlaySound();

    // Constructor to initialize the UI
    public SlotMachineUI(BettingChipsMain chips, MainWindow mainMenu) {
        // Assign player chips and main menu
        this.playerChips = mainMenu.playerChips;
        this.mainMenu = mainMenu;

        // Create the main window
        frame = new JFrame("Slot Machine Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit app on close
        frame.setSize(800, 600);                             // Set window size

        // Initialize the message manager and add this UI as an observer
        messageManager = new MessageManager();
        messageManager.addObserver(this);

        // Use a custom background panel
        BackgroundPanel panel = new BackgroundPanel("src/Slots/Images/casinobackground.png");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Vertical layout

        // Add the title label
        JLabel titleLabel = new JLabel("Welcome to the Slot Machine Game!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24)); // Font style
        titleLabel.setForeground(Color.WHITE);               // Text color
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        // Add the balance label
        balanceLabel = new JLabel("Chips Available: " + mainMenu.playerChips.getAmount(), SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Serif", Font.PLAIN, 18)); // Font style
        balanceLabel.setForeground(Color.WHITE);                // Text color
        balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(balanceLabel);

        // Add a spacer for better layout
        panel.add(Box.createVerticalStrut(10)); // Add vertical space

        // Create a panel for the bet input
        JPanel betPanel = new JPanel(new FlowLayout()); // Flow layout for bet panel
        betPanel.setOpaque(false); // Make background transparent
        JLabel betLabel = new JLabel("Enter your bet: ");
        betLabel.setForeground(Color.WHITE);
        betPanel.add(betLabel);
        betField = new JTextField(10); // Input field for bets
        betField.setEnabled(false);    // Initially disabled
        betPanel.add(betField);
        panel.add(betPanel);

        // Add another spacer
        panel.add(Box.createVerticalStrut(20)); // Add vertical space

        // Panel for slot machine reels
        reelsPanel = new JPanel(new FlowLayout());
        reelsPanel.setOpaque(false); // Transparent background
        reel1Label = new JLabel();   // First reel
        reel2Label = new JLabel();   // Second reel
        reel3Label = new JLabel();   // Third reel
        reelsPanel.add(reel1Label);  // Add to panel
        reelsPanel.add(reel2Label);  // Add to panel
        reelsPanel.add(reel3Label);  // Add to panel
        panel.add(reelsPanel);

        // Label to display results
        resultLabel = new JLabel("Choose a game to start", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(resultLabel);

        // Panel for selecting slot machines
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        cowboyButton = new JButton("Wild West Slot Machine");    // Wild West game
        superheroButton = new JButton("Superhero Slot Machine"); // Superhero game
        buttonPanel.add(cowboyButton);
        buttonPanel.add(superheroButton);
        panel.add(buttonPanel);

        // Spin button for spinning the reels
        spinButton = new JButton("Spin");
        spinButton.setEnabled(false); // Initially disabled until a game is selected
        spinButton.setFont(new Font("Arial", Font.BOLD, 18));
        spinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinButton.addActionListener(e -> spinReels());          // Spin reels on click
        spinButton.addActionListener(e -> playSlotMachine());    // Play the game
        panel.add(spinButton);

        // Main menu button to return to the main menu
        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setFont(new Font("Arial", Font.BOLD, 18));
        mainMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuButton.addActionListener(e -> returnToMainMenu());
        panel.add(mainMenuButton);

        // Initialize symbol images for the reels
        initializeSymbolImages();

        // Add action listeners for selecting slot machine games
        cowboyButton.addActionListener(e -> {
            setMachine(new WildWestSlotMachine(chips.getAmount())); // Select Wild West game
        });

        superheroButton.addActionListener(e -> {
            setMachine(new SuperheroSlotMachine(chips.getAmount())); // Select Superhero game
        });

        // Add the completed panel to the frame
        frame.add(panel);
        frame.setVisible(true); // Make the frame visible
    }

    // Sets the selected slot machine and initializes game logic
    public void setMachine(SlotMachinesTemplate machine) {
        this.currentMachine = machine;
        this.gameLogic = new GameLogic(machine, messageManager, playerChips, mainMenu);

        // Update the UI with the selected game's details
        balanceLabel.setText("Chips Available: " + mainMenu.playerChips.getAmount());
        messageManager.setMessage("Selected: " + machine.getGameName() +
                " | Bet Min: $" + machine.getBetMinimum() +
                " | Bet Max: $" + machine.getMaxBet() +
                " | Win Multiplier: " + machine.getWinMultiplier() + "x");

        // Enable bet input and spin button
        betField.setEnabled(true);
        spinButton.setEnabled(true);
    }

    // Initializes the symbol images for the reels
    private void initializeSymbolImages() {
        symbolImages = new HashMap<>();
        symbolImages.put("Horse", new ImageIcon("src/Slots/Images/horse3.png"));
        symbolImages.put("Sheriff", new ImageIcon("src/Slots/Images/sheriff.png"));
        symbolImages.put("Bandit", new ImageIcon("src/Slots/Images/bandit.png"));
        symbolImages.put("Batman", new ImageIcon("src/Slots/Images/batman.png"));
        symbolImages.put("Superman", new ImageIcon("src/Slots/Images/superman.png"));
        symbolImages.put("Ironman", new ImageIcon("src/Slots/Images/ironman3.png"));
    }

    // Handles spinning the reels and updating the UI
    private void spinReels() {
        try {
            int bet = Integer.parseInt(betField.getText()); // Parse the entered bet amount
            if (!gameLogic.validateBet(bet)) {
                return; // If the bet is invalid, stop execution
            }

            // Play spin sound
            playSoundInstance.playSound("src/Slots/SpinSoundEffect.wav");

            // Spin the reels and update the UI
            String[] reels = gameLogic.spinReels();
            gameLogic.updateReels(reels);

            // Determine the result and update messages
            String result = gameLogic.playGame(bet, reels);
            messageManager.setMessage(result);

            // Update the balance label
            balanceLabel.setText("Chips Available: " + mainMenu.playerChips.getAmount());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid bet.");
        }
    }

    // Handles playing the slot machine (abstracted logic)
    private void playSlotMachine() {
        if (currentMachine != null) {
            currentMachine.play(); // Execute the game logic of the selected slot machine
            balanceLabel.setText("Chips Available: " + mainMenu.playerChips.getAmount());
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a slot machine first.");
        }
    }

    // Returns to the main menu
    public void returnToMainMenu() {
        frame.setVisible(false);       // Hide the slot machine UI
        mainMenu.refreshChips();      // Refresh chips in the main menu
        mainMenu.setVisible(true);    // Show the main menu
    }

    // Refreshes the chip display
    public void refresh1Chips() {
        balanceLabel.setText("Chips Available: " + mainMenu.playerChips.getAmount());
        messageManager.setMessage("Chips refreshed. Current chips: " + mainMenu.playerChips.getAmount());
    }

    // Sets the visibility of the UI
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    // Updates the UI based on messages from the MessageManager
    @Override
    public void update(String message) {
        if (message.startsWith("Chips Available:")) {
            balanceLabel.setText(message); // Update balance
        } else if (message.startsWith("Reels:")) {
            String[] reels = message.replace("Reels: ", "").split(", ");
            if (reels.length == 3) {
                reel1Label.setIcon(resizeImageIcon(symbolImages.get(reels[0]), 150, 150)); // Update reel 1 + set its size
                reel2Label.setIcon(resizeImageIcon(symbolImages.get(reels[1]), 150, 150)); // Update reel 2 + set its size
                reel3Label.setIcon(resizeImageIcon(symbolImages.get(reels[2]), 150, 150)); // Update reel 3 + set its size
            }
        } else {
            resultLabel.setText("<html>" + message.replace("\n", "<br>") + "</html>"); // Display the result
        }
    }

    // Resizes an image icon to fit within specified dimensions
    private ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        if (icon != null) {
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        }
        return null;
    }
}
