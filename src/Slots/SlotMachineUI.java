package Slots;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SlotMachineUI {
    private JFrame frame;
    private JPanel panel;
    private JLabel balanceLabel, resultLabel, reelsLabel;
    private JButton cowboyButton, superheroButton, spinButton;
    private JTextField betField;
    private SlotMachinesTemplate currentMachine;

    public SlotMachineUI() {
        // Initialize the frame
        frame = new JFrame("Slot Machine Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);

        // Create the main panel
        panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1));

        // Title Label
        JLabel titleLabel = new JLabel("Welcome to the Slot Machine Game!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        panel.add(titleLabel);

        // Balance Label
        balanceLabel = new JLabel("Balance: $100.0", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        panel.add(balanceLabel);

        // Bet Input
        JPanel betPanel = new JPanel(new FlowLayout());
        betPanel.add(new JLabel("Enter your bet: "));
        betField = new JTextField(10);
        betField.setEnabled(false); // Disabled until a game is selected
        betPanel.add(betField);
        panel.add(betPanel);

        // Reels Display
        reelsLabel = new JLabel("[ ] [ ] [ ]", SwingConstants.CENTER);
        reelsLabel.setFont(new Font("Serif", Font.BOLD, 24));
        panel.add(reelsLabel);

        // Result Label
        resultLabel = new JLabel("Choose a game to start", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Serif", Font.PLAIN, 16));
        panel.add(resultLabel);

        // Slot Machine Selection Buttons
        cowboyButton = new JButton("Wild West Slot Machine");
        superheroButton = new JButton("Superhero Slot Machine");
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(cowboyButton);
        buttonPanel.add(superheroButton);
        panel.add(buttonPanel);

        // Spin Button
        spinButton = new JButton("Spin");
        spinButton.setEnabled(false); // Disabled until a machine is selected
        panel.add(spinButton);

        // Add listeners
        cowboyButton.addActionListener(e -> selectMachine(new WildWestSlotMachine(100.0)));
        superheroButton.addActionListener(e -> selectMachine(new SuperheroSlotMachine(100.0)));
        spinButton.addActionListener(e -> spinReels());

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);
    }

    private void selectMachine(SlotMachinesTemplate machine) {
        currentMachine = machine;
        balanceLabel.setText("Balance: $" + currentMachine.balance);
        resultLabel.setText("You selected: " + machine.getGameName() +
                " | Bet Minimum: $" + currentMachine.getBetMinimum() +
                " | Bet Maximum: $" + currentMachine.getMaxBet());
        betField.setEnabled(true); // Enable bet input
        spinButton.setEnabled(true); // Enable spin button
    }

    private void spinReels() {
        if (currentMachine == null) {
            resultLabel.setText("No slot machine selected!");
            return;
        }

        try {
            double bet = Double.parseDouble(betField.getText());

            // Validate bet
            if (bet < currentMachine.getBetMinimum() || bet > currentMachine.getMaxBet()) {
                resultLabel.setText("Invalid bet! Must be between $" + currentMachine.getBetMinimum() + " and $" + currentMachine.getMaxBet());
                return;
            }

            if (bet > currentMachine.balance) {
                resultLabel.setText("Easy there high roller you swing for the fences huh, boy do we got the game for you, go back to the lobby and play \\u001B[1m Russian Roulette \\u001B[0m");
                return;
            }

            // Deduct bet
            currentMachine.balance -= bet;

            // Spin reels
            Random random = new Random();
            int reel1 = random.nextInt(3);
            int reel2 = random.nextInt(3);
            int reel3 = random.nextInt(3);

            // Update reel text
            reelsLabel.setText("[" + currentMachine.getSymbol(reel1) + "] [" + currentMachine.getSymbol(reel2) + "] [" + currentMachine.getSymbol(reel3) + "]");

            // Check for win
            if (reel1 == reel2 && reel2 == reel3) {
                double winnings = bet * currentMachine.getWinMultiplier();
                currentMachine.balance += winnings;
                resultLabel.setText("You won $" + winnings + "!");
            } else {
                resultLabel.setText("You lost this round.");
            }

            // Update balance
            balanceLabel.setText("Balance: $" + currentMachine.balance);

            // Game over condition
            if (currentMachine.balance <= 0) {
                resultLabel.setText("You are broke, but I know a guy who can fix that... go visit Lenny the Loan Shark");
                spinButton.setEnabled(false);
                betField.setEnabled(false);
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Invalid input! Enter a valid number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SlotMachineUI::new);
    }
}
