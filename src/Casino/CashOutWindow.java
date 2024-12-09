//Chase Wink
package Casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CashOutWindow extends JFrame {
    private BettingChipsMain playerChips;
    private int cash;
    private JLabel cashLabel;
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



        cash = 0;

        // Balance Label
        cashLabel = new JLabel("Cash paid out: $" + cash);
        cashLabel.setForeground(Color.WHITE);
        cashLabel.setBounds(50, 30, 200, 30);
        mainPanel.add(cashLabel);

        // Chip Label
        chipLabel = new JLabel("Total Chips: " + playerChips.getAmount());
        chipLabel.setForeground(Color.WHITE);
        chipLabel.setBounds(50, 70, 200, 30);
        mainPanel.add(chipLabel);

        // Convert Button
        JButton convertButton = new JButton("Convert Chips to Cash");
        convertButton.setBounds(50, 110, 200, 30);
        mainPanel.add(convertButton);

        //Cash to Balance

        JButton convert2Cash = new JButton("Convert Cash back to Balance");
        convert2Cash.setBounds(50,130,200,30);
        mainPanel.add(convert2Cash);

        // Action Listener for Convert Button
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int chips = playerChips.getAmount();
                mainWindow.updateTotalCash(chips);
                cash += chips;
                playerChips.setAmount(0); // Reset chips to 0 after conversion
                cashLabel.setText("Cash In Hand: $" + cash);
                chipLabel.setText("Total Chips: " + playerChips.getAmount());
                mainWindow.refreshChips();
                JOptionPane.showMessageDialog(null, "Converted " + chips + " Chips to Cash");
            }
        });

        convert2Cash.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cash = mainWindow.getTotalCash();
                if (cash > 0) {
                    mainWindow.updateBalance(mainWindow.balance + cash); // Add cash to balance
                    mainWindow.setCash(0); // Reset cash
                    cashLabel.setText("Cash In Hand: $0"); // Update local cash label
                    mainWindow.updateTotalCash(0); // Ensure the main window's cash is set to 0
                    JOptionPane.showMessageDialog(null, "Converted $" + cash + " to Balance. Current Balance: $" + mainWindow.balance);
                    mainWindow.refreshChips();
                } else {
                    JOptionPane.showMessageDialog(null, "No cash to convert to balance.");
                }
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
                if (mainWindow.balance == 0) {
                    JOptionPane.showMessageDialog(null, "Total Cash Left Casino with: $" + mainWindow.getTotalCash() + "\nMessage from Goodfellas: Come back soon" + "\nMessage from Lenny: If you owe me I'll see you soon!!!");
                    System.exit(0); //Close application
                }
                else{
                    JOptionPane.showMessageDialog(null, "You still have a balance remaining cash out before leaving");
                }
            }
        });
    }
}



