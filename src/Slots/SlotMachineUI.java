package Slots;

import javax.swing.*;
import java.awt.*;
import java.awt.Image;

public class SlotMachineUI implements Observer {
    private JFrame frame;
    private JPanel panel;
    private JLabel balanceLabel, resultLabel;
    private JLabel reel1Label, reel2Label, reel3Label;
    private JButton cowboyButton, superheroButton, spinButton, cashOutButton;
    private JTextField betField;
    private Invoker invoker;
    private SlotMachinesTemplate currentMachine;
    private GameLogic gameLogic;
    private MessageManager messageManager;

    public SlotMachineUI() {
        frame = new JFrame("Slot Machine Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);

        // Initialize the MessageManager
        messageManager = new MessageManager();
        messageManager.addObserver(this); // Register UI as an observer

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Welcome to the Slot Machine Game!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        balanceLabel = new JLabel("Balance: $100.0", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(balanceLabel);

        panel.add(Box.createVerticalStrut(10)); // Lower balance label

        JPanel betPanel = new JPanel(new FlowLayout());
        betPanel.add(new JLabel("Enter your bet: "));
        betField = new JTextField(10);
        betField.setEnabled(true);
        betPanel.add(betField);
        panel.add(betPanel);

        panel.add(Box.createVerticalStrut(40)); // Add vertical space between bet and game start

        // Reel display panel
        JPanel reelsPanel = new JPanel(new FlowLayout());
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
            setReelImages("WildWest");
            Command cowboyCommand = new GameSelectionCommand(new WildWestSlotMachine(100.0), this, messageManager);
            invoker.setCommand(cowboyCommand);
            invoker.executeCommand();
        });

        superheroButton.addActionListener(e -> {
            setReelImages("Superhero");
            Command superheroCommand = new GameSelectionCommand(new SuperheroSlotMachine(100.0), this, messageManager);
            invoker.setCommand(superheroCommand);
            invoker.executeCommand();
        });

        // Add listener for the spin button
        spinButton.addActionListener(e -> {
            double bet = getBet();
            Command spinCommand = new SpinCommand(gameLogic, messageManager, bet);
            invoker.setCommand(spinCommand);
            invoker.executeCommand();
        });

        // Add listener for the cash-out button
        cashOutButton.addActionListener(e -> {
            if (currentMachine != null) {
                Command cashOutCommand = new CashOutCommand(currentMachine, messageManager);
                invoker.setCommand(cashOutCommand);
                invoker.executeCommand();
            } else {
                messageManager.setMessage("No machine selected to cash out.");
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void setReelImages(String theme) {
        try {
            ImageIcon icon1, icon2, icon3;
            if (theme.equals("WildWest")) {
                icon1 = new ImageIcon("src/Slots/Images/horse3.png");
                icon2 = new ImageIcon("src/Slots/Images/sheriff.png");
                icon3 = new ImageIcon("src/Slots/Images/bandit.png");
            } else { // Superhero theme
                icon1 = new ImageIcon("src/Slots/Images/batman.png");
                icon2 = new ImageIcon("src/Slots/Images/superman.png");
                icon3 = new ImageIcon("src/Slots/Images/ironman.png");
            }

            reel1Label.setIcon(resizeImageIcon(icon1, 200, 200));
            reel2Label.setIcon(resizeImageIcon(icon2, 200, 200));
            reel3Label.setIcon(resizeImageIcon(icon3, 200, 200));

        } catch (Exception e) {
            System.out.println("Error setting reel images: " + e.getMessage());
        }
    }

    private ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public void setMachine(SlotMachinesTemplate machine) {
        this.currentMachine = machine;
        this.gameLogic = new GameLogic(machine, messageManager);

        balanceLabel.setText("Balance: $" + machine.balance);
        messageManager.setMessage("Selected: " + machine.getGameName() +
                " | Bet Min: $" + machine.getBetMinimum() +
                " | Bet Max: $" + machine.getMaxBet());

        cashOutButton.setEnabled(true); // Enable the cash-out button
        betField.setEnabled(true);
        spinButton.setEnabled(true);
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
        if (message.startsWith("Reels:")) {
            String[] parts = message.replace("Reels: [", "").replace("]", "").split("\\] \\[", -1);
            if (parts.length == 3 && currentMachine != null) {
                resultLabel.setText("<html>Reels: [" + parts[0] + "] [" + parts[1] + "] [" + parts[2] + "]</html>");
            } else {
                resultLabel.setText("Error: Reels data is invalid.");
            }
        } else if (message.startsWith("Balance:")) {
            balanceLabel.setText(message); // Update balance label
        } else {
            resultLabel.setText("<html>" + message.replace("\n", "<br>") + "</html>"); // Update result label
        }
    }

    public static void main(String[] args) {
        new SlotMachineUI();
    }
}
