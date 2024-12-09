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
        setTitle("Convert Chips to Cash");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLayout(null);

        BackgroundPanel mainPanel = new BackgroundPanel("src/Casino/Images/Bank.png");
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(mainPanel);



        balance = 0;

        // Balance Label
        balanceLabel = new JLabel("Cash paid out: $" + balance);
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setBounds(50, 30, 200, 30);
        mainPanel.add(balanceLabel);

        // Chip Label
        chipLabel = new JLabel("Total Chips: " + playerChips.getAmount());
        chipLabel.setForeground(Color.WHITE);
        chipLabel.setBounds(50, 70, 200, 30);
        mainPanel.add(chipLabel);

        // Convert Button
        JButton convertButton = new JButton("Convert Chips to Cash");
        convertButton.setBounds(50, 110, 200, 30);
        mainPanel.add(convertButton);

        // Action Listener for Convert Button
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int chips = playerChips.getAmount();
                balance += chips;
                playerChips.setAmount(0); // Reset chips to 0 after conversion
                balanceLabel.setText("Cash In Hand: $" + balance);
                chipLabel.setText("Total Chips: " + playerChips.getAmount());
                JOptionPane.showMessageDialog(null, "Converted " + chips + " Chips to Cash");
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

        JButton leaveCasino = new JButton("Leave Casino");
        leaveCasino.setBounds(100, 150, 200, 30); //Leave Casino button next to Main
        mainPanel.add(leaveCasino);                                  // Menu button

        leaveCasino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Message from Goodfellas: Come back soon" + "\nMessage from Lenny: Have a good one and if you still owe me I'll pay you a visit since you're such a cool person");
                System.exit(0); //Close application
            }
        });
    }
}



