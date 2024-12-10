//Chase Wink
package Casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Displays the CashOutWindow where players can manage chips, cash, and balance by performing swaps, and
 * doing some calculations to update the labels in the MainWindow like the chip counter or the balance  */

public class CashOutWindow extends JFrame {
    private BettingChipsMain playerChips; // Reference to player's chips
    private int cash; // Stores total cash available
    private JLabel cashLabel; // Displays the cash amount
    private JLabel chipLabel; // Displays the total chips
    private MainWindow mainWindow; // Reference to the main window
    private JLabel balanceLabel; // Displays the player's balance

    public CashOutWindow(BettingChipsMain playerChips, MainWindow mainWindow) {
        this.playerChips = playerChips;
        this.mainWindow = mainWindow;

        setTitle("Convert Chips to Cash");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel setup with background image
        BackgroundPanel mainPanel = new BackgroundPanel("src/Casino/Images/Bank.png");
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(mainPanel);

        // Displays the current cash amount
        cashLabel = new JLabel("Cash: $" + cash);
        cashLabel.setForeground(Color.WHITE);
        mainPanel.add(cashLabel);

        // Displays the player's total chips
        chipLabel = new JLabel("Total Chips: " + playerChips.getAmount());
        chipLabel.setForeground(Color.WHITE);
        mainPanel.add(chipLabel);

        // Displays the player's current balance
        balanceLabel = new JLabel("Balance: $" + mainWindow.getBalance());
        balanceLabel.setForeground(Color.WHITE);
        mainPanel.add(balanceLabel);

        // Button to convert chips into cash
        JButton convertButton = new JButton("Convert Chips to Cash");
        mainPanel.add(convertButton);

        // Button to convert cash into balance
        JButton convert2Cash = new JButton("Convert Cash to Balance");
        mainPanel.add(convert2Cash);

        // Button to convert balance into cash
        JButton Balance2Cash = new JButton("Convert Balance to Cash");
        mainPanel.add(Balance2Cash);

        // Handles converting chips to cash
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Updates the total cash and chip count
                int chips = playerChips.getAmount();
                mainWindow.updateTotalCash(chips);
                cash += chips;
                playerChips.setAmount(0); // Resets chips to 0
                cashLabel.setText("Cash: $" + cash);
                chipLabel.setText("Total Chips: " + playerChips.getAmount());
                mainWindow.refreshChips(); // Updates the chip count in the main window
                JOptionPane.showMessageDialog(null, "Converted " + chips + " Chips to Cash");
            }
        });

        // Handles converting cash to balance
        convert2Cash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cash = mainWindow.getTotalCash();
                // Checks if there is cash to convert
                if (cash > 0) {
                    int newBalance = mainWindow.getBalance() + cash; // Updates balance
                    mainWindow.updateBalance(newBalance);// Updates balance in main window
                    mainWindow.setCash(0); // Resets cash to 0
                    mainWindow.updateTotalCash(0);// Updates total cash in main window
                    cashLabel.setText("Cash: $0");
                    balanceLabel.setText("Balance: $" + newBalance);
                    JOptionPane.showMessageDialog(null, "Converted $" + cash + " to Balance. Current Balance: $" + newBalance);
                } else {
                    JOptionPane.showMessageDialog(null, "No cash to convert to balance.");
                }
            }
        });

        // Handles converting balance to cash
        Balance2Cash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int balance = mainWindow.getBalance();
                // Checks if there is a balance to convert
                if (balance > 0) {
                    // Updates the total cash and balance
                    int newCash = mainWindow.getTotalCash() + balance;
                    mainWindow.updateTotalCash(newCash);
                    mainWindow.updateBalance(0); // Resets balance to 0
                    balanceLabel.setText("Balance: $0");
                    cashLabel.setText("Cash: $" + newCash);
                    JOptionPane.showMessageDialog(null, "Converted $" + balance + " to Cash. Current Cash: $" + newCash);
                } else {
                    JOptionPane.showMessageDialog(null, "No balance to convert to cash.");
                }
            }
        });

        // Button to return to the main menu
        JButton mainMenuButton = new JButton("Main Menu");
        mainPanel.add(mainMenuButton);
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // Hides this window
                mainWindow.setVisible(true); // Returns to main menu
            }
        });

        // Button to leave the casino
        JButton leaveCasino = new JButton("Leave Casino");
        mainPanel.add(leaveCasino);
        leaveCasino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Checks if the player has a balance remaining
                if (mainWindow.balance == 0) {
                    JOptionPane.showMessageDialog(null, "Total Cash Left Casino with: $" + mainWindow.getTotalCash() +
                            "\nMessage from Goodfellas: Come back soon" +
                            "\nMessage from Lenny: If you owe me I'll see you soon!!!");
                    System.exit(0); // Exits the application
                } else {
                    JOptionPane.showMessageDialog(null, "You still have a balance remaining cash out before leaving");
                }
            }
        });
    }
}
