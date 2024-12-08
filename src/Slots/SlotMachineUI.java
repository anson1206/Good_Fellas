package Slots;

import Casino.BettingChipsMain;
import Casino.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SlotMachineUI implements Observer {
    private JFrame frame;
    private JPanel reelsPanel;
    private JLabel balanceLabel, resultLabel;
    private JLabel reel1Label, reel2Label, reel3Label;
    private JButton cowboyButton, superheroButton, spinButton, mainMenuButton;
    private JTextField betField;
    private Invoker invoker;
    private SlotMachinesTemplate currentMachine;
    private GameLogic gameLogic;
    private MessageManager messageManager;
    private Map<String, ImageIcon> symbolImages;
    private BettingChipsMain playerChips;
    private MainWindow mainMenu;

    public SlotMachineUI(BettingChipsMain chips, MainWindow mainMenu) {
        this.playerChips = chips;
        this.mainMenu = mainMenu;

        frame = new JFrame("Slot Machine Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Initialize MessageManager and add this UI as an observer
        messageManager = new MessageManager();
        messageManager.addObserver(this);

        // Use BackgroundPanel for the main panel
        BackgroundPanel panel = new BackgroundPanel("src/Slots/Images/casinobackground.png");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Welcome to the Slot Machine Game!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        balanceLabel = new JLabel("Chips Available: " + playerChips.getAmount(), SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(balanceLabel);

        panel.add(Box.createVerticalStrut(10)); // Spacer

        JPanel betPanel = new JPanel(new FlowLayout());
        betPanel.setOpaque(false);
        JLabel betLabel = new JLabel("Enter your bet: ");
        betLabel.setForeground(Color.WHITE);
        betPanel.add(betLabel);
        betField = new JTextField(10);
        betField.setEnabled(false);
        betPanel.add(betField);
        panel.add(betPanel);

        panel.add(Box.createVerticalStrut(20)); // Spacer

        reelsPanel = new JPanel(new FlowLayout());
        reelsPanel.setOpaque(false);
        reel1Label = new JLabel();
        reel2Label = new JLabel();
        reel3Label = new JLabel();
        reelsPanel.add(reel1Label);
        reelsPanel.add(reel2Label);
        reelsPanel.add(reel3Label);
        panel.add(reelsPanel);

        resultLabel = new JLabel("Choose a game to start", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(resultLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        cowboyButton = new JButton("Wild West Slot Machine");
        superheroButton = new JButton("Superhero Slot Machine");
        buttonPanel.add(cowboyButton);
        buttonPanel.add(superheroButton);
        panel.add(buttonPanel);

        spinButton = new JButton("Spin");
        spinButton.setEnabled(false);
        spinButton.setFont(new Font("Arial", Font.BOLD, 18));
        spinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinButton.addActionListener(e -> spinReels());
        panel.add(spinButton);

        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setFont(new Font("Arial", Font.BOLD, 18));
        mainMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuButton.addActionListener(e -> returnToMainMenu());
        panel.add(mainMenuButton);

        initializeSymbolImages();

        // Add button actions
        cowboyButton.addActionListener(e -> {
            setMachine(new WildWestSlotMachine(chips.getAmount()));
        });

        superheroButton.addActionListener(e -> {
            setMachine(new SuperheroSlotMachine(chips.getAmount()));
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    public void setMachine(SlotMachinesTemplate machine) {
        this.currentMachine = machine;
        this.gameLogic = new GameLogic(machine, messageManager);

        balanceLabel.setText("Chips Available: " + playerChips.getAmount());
        messageManager.setMessage("Selected: " + machine.getGameName() +
                " | Bet Min: $" + machine.getBetMinimum() +
                " | Bet Max: $" + machine.getMaxBet() +
                " | Win Multiplier: " + machine.getWinMultiplier() + "x");

        betField.setEnabled(true);
        spinButton.setEnabled(true);
    }

    private void initializeSymbolImages() {
        symbolImages = new HashMap<>();
        symbolImages.put("Horse", new ImageIcon("src/Slots/Images/horse3.png"));
        symbolImages.put("Sheriff", new ImageIcon("src/Slots/Images/sheriff.png"));
        symbolImages.put("Bandit", new ImageIcon("src/Slots/Images/bandit.png"));
        symbolImages.put("Batman", new ImageIcon("src/Slots/Images/batman.png"));
        symbolImages.put("Superman", new ImageIcon("src/Slots/Images/superman.png"));
        symbolImages.put("Ironman", new ImageIcon("src/Slots/Images/ironman3.png"));
    }

    private void spinReels() {
        try {
            int bet = Integer.parseInt(betField.getText());
            if (!gameLogic.validateBet(bet)) {
                return; // Invalid bet, message already shown
            }

            playerChips.removeAmount(bet);
            balanceLabel.setText("Chips Available: " + playerChips.getAmount());
            String[] reels = gameLogic.spinReels();
            gameLogic.updateReels(reels);

            String result = gameLogic.playGame(bet, reels);
            messageManager.setMessage(result);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid bet.");
        }
    }

    public void returnToMainMenu() {
        frame.setVisible(false);
        mainMenu.setVisible(true);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    @Override
    public void update(String message) {
        if (message.startsWith("Chips Available:")) {
            balanceLabel.setText(message);
        } else if (message.startsWith("Reels:")) {
            String[] reels = message.replace("Reels: ", "").split(", ");
            if (reels.length == 3) {
                reel1Label.setIcon(resizeImageIcon(symbolImages.get(reels[0]), 150, 150));
                reel2Label.setIcon(resizeImageIcon(symbolImages.get(reels[1]), 150, 150));
                reel3Label.setIcon(resizeImageIcon(symbolImages.get(reels[2]), 150, 150));
            }
        } else {
            resultLabel.setText("<html>" + message.replace("\n", "<br>") + "</html>");
        }
    }

    private ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        if (icon != null) {
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        }
        return null;
    }
}
