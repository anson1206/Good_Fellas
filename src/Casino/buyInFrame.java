package Casino;

import javax.swing.*;
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
        creditCardField = new JTextField(20);
        mainPanel.add(creditCardLabel);
        mainPanel.add(creditCardField);

        // Amount input
        JLabel amountLabel = new JLabel("Enter Amount to Input:");
        amountField = new JTextField(10);
        mainPanel.add(amountLabel);
        mainPanel.add(amountField);

        // Confirm button
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String creditCardInfo = creditCardField.getText();
                int amount = Integer.parseInt(amountField.getText());
                playerChips.addAmount(amount);
                mainWindow.updateBalance(mainWindow.getTotalCash() + amount);
                mainWindow.refreshChips();
                dispose(); // Close the buyInFrame
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

