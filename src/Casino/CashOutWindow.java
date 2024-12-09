//Chase Wink
package Casino;

import BlackJack.Main;
import Casino.BettingChipsMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CashOutWindow extends JFrame {
    private BettingChipsMain playerChips;
    private int balance;
    private JLabel balanceLabel;
    private JLabel chipLabel;
    private MainWindow mainWindow;

    public CashOutWindow(BettingChipsMain playerChips, MainWindow mainWindow) {
        this.playerChips = playerChips;
        this.mainWindow = mainWindow;
        setTitle("Convert Chips to Balance");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLayout(null);

        BackgroundPanel mainPanel = new BackgroundPanel("src/Casino/Images/Bank.png");
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(mainPanel);



        balance = 0;

        // Balance Label
        balanceLabel = new JLabel("Balance: $" + balance);
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setBounds(50, 30, 200, 30);
        mainPanel.add(balanceLabel);

        // Chip Label
        chipLabel = new JLabel("Total Chips: " + playerChips.getAmount());
        chipLabel.setForeground(Color.WHITE);
        chipLabel.setBounds(50, 70, 200, 30);
        mainPanel.add(chipLabel);

        // Convert Button
        JButton convertButton = new JButton("Convert Chips to Balance");
        convertButton.setBounds(50, 110, 200, 30);
        mainPanel.add(convertButton);

        // Action Listener for Convert Button
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int chips = playerChips.getAmount();
                balance += chips;
                playerChips.setAmount(0); // Reset chips to 0 after conversion
                balanceLabel.setText("Balance: $" + balance);
                chipLabel.setText("Total Chips: " + playerChips.getAmount());
                JOptionPane.showMessageDialog(null, "Converted " + chips + " chips to balance.");
            }
        });

        // Main Menu Button
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setBounds(50, 150, 200, 30);
        mainPanel.add(mainMenuButton);

        // Action Listener for Main Menu Button
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false); // Hide CashOutWindow
                mainWindow.setVisible(true); // Show MainWindow
            }
        });
    }
}



