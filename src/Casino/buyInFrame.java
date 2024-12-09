package Casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class buyInFrame extends JFrame{
    private BettingChipsMain playerChips;
    private MainWindow mainWindow;
    private JTextField creditCardField;
    private JTextField amountField;


    public buyInFrame(BettingChipsMain playerChips, MainWindow mainWindow) {
        this.playerChips = playerChips;
        this.mainWindow = mainWindow;
        setTitle("Convert Chips to Cash");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackgroundPanel mainPanel = new BackgroundPanel("src/Casino/Images/Bank.png");
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Credit card input
        JLabel creditCardLabel = new JLabel("Enter Credit Card Information:");
        creditCardLabel.setForeground(Color.WHITE); // Set text color to white
        creditCardField = new JTextField(20);
        creditCardField.setMaximumSize(new Dimension(200, 20)); // Set maximum size

        mainPanel.add(creditCardLabel);
        mainPanel.add(creditCardField);

        // Amount input
        JLabel amountLabel = new JLabel("Enter Amount to Input:");
        amountLabel.setForeground(Color.WHITE); // Set text color to white
        amountField = new JTextField(10);
        amountField.setMaximumSize(new Dimension(200, 20)); // Set maximum size


        mainPanel.add(amountLabel);
        mainPanel.add(amountField);

        // Confirm button
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String creditCardInfo = creditCardField.getText();
                if (creditCardInfo.length() == 16 && creditCardInfo.matches("\\d+")) {
                    int amount = Integer.parseInt(amountField.getText());
                    playerChips.addAmount(amount);
                    mainWindow.updateBalance(mainWindow.balance + amount);
                    //mainWindow.refreshChips();
                    dispose(); // Close the buyInFrame
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a 16 digit credit card number with no spaces or -.");
                }
            }
        });
        mainPanel.add(confirmButton);

        // Main menu button
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the buyInFrame
                mainWindow.setVisible(true); // Show the main menu
            }
        });
        mainPanel.add(mainMenuButton);

        add(mainPanel);
    }


}

