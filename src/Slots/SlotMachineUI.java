package Slots;

import javax.swing.*;
import java.awt.*;

public class SlotMachineUI implements Observer {
    private JFrame frame;
    private JPanel panel;
    private JLabel balanceLabel, resultLabel, reelsLabel;
    private JButton cowboyButton, superheroButton, spinButton, cashOutButton;
    private JTextField betField;
    private Invoker invoker;
    private SlotMachinesTemplate currentMachine;
    private GameLogic gameLogic;
    private MessageManager messageManager;

    public SlotMachineUI() {
        frame = new JFrame("Slot Machine Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);

        // Initialize the MessageManager
        messageManager = new MessageManager();
        messageManager.addObserver(this); // Register UI as an observer

        panel = new JPanel();
        panel.setLayout(new GridLayout(9, 1));

        JLabel titleLabel = new JLabel("Welcome to the Slot Machine Game!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        panel.add(titleLabel);

        balanceLabel = new JLabel("Balance: $0.0", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        panel.add(balanceLabel);

        JPanel betPanel = new JPanel(new FlowLayout());
        betPanel.add(new JLabel("Enter your bet: "));
        betField = new JTextField(10);
        betField.setEnabled(false);
        betPanel.add(betField);
        panel.add(betPanel);

        reelsLabel = new JLabel("[ ] [ ] [ ]", SwingConstants.CENTER);
        reelsLabel.setFont(new Font("Serif", Font.BOLD, 24));
        panel.add(reelsLabel);

        resultLabel = new JLabel("Choose a game to start", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        panel.add(resultLabel);

        cowboyButton = new JButton("Wild West Slot Machine");
        superheroButton = new JButton("Superhero Slot Machine");
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(cowboyButton);
        buttonPanel.add(superheroButton);
        panel.add(buttonPanel);

        spinButton = new JButton("Spin");
        spinButton.setEnabled(false);
        panel.add(spinButton);

        cashOutButton = new JButton("Cash Out");
        cashOutButton.setEnabled(false);
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
        if (message.startsWith("Balance:")) {
            balanceLabel.setText(message); // Update balance label
        } else if (message.startsWith("Reels:")) {
            reelsLabel.setText(message); // Update reels label
        } else {
            resultLabel.setText("<html>" + message.replace("\n", "<br>") + "</html>"); // Update result label
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SlotMachineUI::new);
    }
}
