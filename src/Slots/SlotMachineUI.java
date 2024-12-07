package Slots;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SlotMachineUI implements Observer {
    private JFrame frame;
    private JPanel panel, reelsPanel;
    private JLabel balanceLabel, resultLabel;
    private JLabel reel1Label, reel2Label, reel3Label;
    private JButton cowboyButton, superheroButton, spinButton, cashOutButton;
    private JTextField betField;
    private Invoker invoker;
    private SlotMachinesTemplate currentMachine;
    private GameLogic gameLogic;
    private MessageManager messageManager;
    private Map<String, ImageIcon> symbolImages;

    public SlotMachineUI() {
        frame = new JFrame("Slot Machine Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Initialize MessageManager and add this UI as an observer
        messageManager = new MessageManager();
        messageManager.addObserver(this);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Welcome to the Slot Machine Game!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        balanceLabel = new JLabel("Balance: $0.0", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(balanceLabel);

        panel.add(Box.createVerticalStrut(10)); // Spacer

        JPanel betPanel = new JPanel(new FlowLayout());
        betPanel.add(new JLabel("Enter your bet: "));
        betField = new JTextField(10);
        betField.setEnabled(false);
        betPanel.add(betField);
        panel.add(betPanel);

        panel.add(Box.createVerticalStrut(20)); // Spacer

        reelsPanel = new JPanel(new FlowLayout());
        reel1Label = new JLabel();
        reel2Label = new JLabel();
        reel3Label = new JLabel();
        reelsPanel.add(reel1Label);
        reelsPanel.add(reel2Label);
        reelsPanel.add(reel3Label);
        panel.add(reelsPanel);

        resultLabel = new JLabel("Choose a game to start", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(resultLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        cowboyButton = new JButton("Wild West Slot Machine");
        superheroButton = new JButton("Superhero Slot Machine");
        buttonPanel.add(cowboyButton);
        buttonPanel.add(superheroButton);
        panel.add(buttonPanel);

        spinButton = new JButton("Spin");
        spinButton.setEnabled(false);
        spinButton.setFont(new Font("Arial", Font.BOLD, 18));
        spinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(spinButton);

        cashOutButton = new JButton("Cash Out");
        cashOutButton.setEnabled(false);
        cashOutButton.setFont(new Font("Arial", Font.BOLD, 18));
        cashOutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(cashOutButton);

        // Initialize the invoker
        invoker = new Invoker();

        // Add listeners for game selection
        cowboyButton.addActionListener(e -> {
            Command cowboyCommand = new GameSelectionCommand(new WildWestSlotMachine(100.0), this, messageManager);
            invoker.setCommand(cowboyCommand);
            invoker.executeCommand();
        });

        superheroButton.addActionListener(e -> {
            Command superheroCommand = new GameSelectionCommand(new SuperheroSlotMachine(100.0), this, messageManager);
            invoker.setCommand(superheroCommand);
            invoker.executeCommand();
        });

        // Add listener for the spin button
        spinButton.addActionListener(e -> {
            double bet = getBet();
            Command spinCommand = new SpinCommand(gameLogic, messageManager);
            invoker.setCommand(spinCommand);
            invoker.executeCommand();
        });

        // Add listener for the cash-out button
        cashOutButton.addActionListener(e -> {
            Command cashOutCommand = new CashOutCommand(currentMachine, messageManager);
            invoker.setCommand(cashOutCommand);
            invoker.executeCommand();
        });

        initializeSymbolImages();

        frame.add(panel);
        frame.setVisible(true);
    }

    public void setMachine(SlotMachinesTemplate machine) {
        this.currentMachine = machine;
        this.gameLogic = new GameLogic(machine, messageManager);

        balanceLabel.setText("Balance: $" + machine.balance);
        messageManager.setMessage("Selected: " + machine.getGameName() +
                " | Bet Min: $" + machine.getBetMinimum() +
                " | Bet Max: $" + machine.getMaxBet());

        cashOutButton.setEnabled(true);
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

    public void updateReels(String[] reels) {
        reel1Label.setIcon(resizeImageIcon(symbolImages.get(reels[0]), 150, 150));
        reel2Label.setIcon(resizeImageIcon(symbolImages.get(reels[1]), 150, 150));
        reel3Label.setIcon(resizeImageIcon(symbolImages.get(reels[2]), 150, 150));
    }

    private ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        if (icon != null) {
            Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        }
        return null;
    }

    public double getBet() {
        try {
            return Double.parseDouble(betField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void disableGame() {
        betField.setEnabled(false);
        spinButton.setEnabled(false);
        cashOutButton.setEnabled(false);
        cowboyButton.setEnabled(false);
        superheroButton.setEnabled(false);
    }

    @Override
    public void update(String message) {
        resultLabel.setText("<html>" + message.replace("\n", "<br>") + "</html>");
    }

    public static void main(String[] args) {
        new SlotMachineUI();
    }
}
