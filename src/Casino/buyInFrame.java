//Chase Wink, John
package Casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class serves as the buyIn frame that we created and will show to allow the user to convert their
 * money from their credit cards to the chips. The user can input the credit card numbers, the cvc number
 * and the amount money that they want to withdraw from their account to use to bet. We do some checks, and
 * in this class we provide the code to display the frame to the user and the GUI components */

// Frame for buying in chips by converting cash into chips
public class buyInFrame extends JFrame {
    private BettingChipsMain playerChips; // Holds player's chip data
    private MainWindow mainWindow;       // Reference to the main window
    private JTextField creditCardField; // Input field for credit card
    private JTextField amountField;     // Input field for amount
    private JTextField CVCField;        // Input field for CVC code

    // Constructor to initialize the frame
    public buyInFrame(BettingChipsMain playerChips, MainWindow mainWindow) {
        // Initialize fields with passed in values
        this.playerChips = playerChips;
        this.mainWindow = mainWindow;

        // Set the title, size, and default close operation for the frame
        setTitle("Convert Chips to Cash");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with background image
        BackgroundPanel mainPanel = new BackgroundPanel("src/Casino/Images/Bank.png");
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Vertical layout
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Create and configure the credit card input section
        JLabel creditCardLabel = new JLabel("Enter Credit Card Number\n(no dashes or spaces):");
        creditCardLabel.setForeground(Color.WHITE); // Set label text color to white
        creditCardField = new JTextField(20);   // Text field for credit card number
        creditCardField.setMaximumSize(new Dimension(200, 20)); // Restrict max size

        // Add the credit card input section to the main panel
        mainPanel.add(creditCardLabel);
        mainPanel.add(creditCardField);

        // Create and configure the CVC input section
        JLabel CVCLabel = new JLabel("Enter CVC:");
        CVCLabel.setForeground(Color.WHITE);
        CVCField = new JTextField(20);  // Text field for CVC
        CVCField.setMaximumSize(new Dimension(200, 20));

        // Add the CVC input section to the main panel
        mainPanel.add(CVCLabel);
        mainPanel.add(CVCField);

        // Create and configure the amount input section
        JLabel amountLabel = new JLabel("Enter Amount to Input:");
        amountLabel.setForeground(Color.WHITE);
        amountField = new JTextField(10);  // Text field for amount
        amountField.setMaximumSize(new Dimension(200, 20));

        // Add the amount input section to the main panel
        mainPanel.add(amountLabel);
        mainPanel.add(amountField);

        // Add confirm button with validation and action handling
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validate credit card and CVC input
                String creditCardInfo = creditCardField.getText();
                String CVCInfo = CVCField.getText();
                //This is where we check to see if the credit card number is 16 digits and the CVC is 3 digits
                //The \\d checks for digits
                if (creditCardInfo.length() == 16 && creditCardInfo.matches("\\d+")
                        && CVCInfo.length() == 3 && CVCInfo.matches("\\d+")) {
                    // Convert amount input and update balance
                    int amount = Integer.parseInt(amountField.getText());
                    // Update the balance in the main window
                    mainWindow.updateBalance(mainWindow.balance + amount);
                    dispose(); // Close the frame after processing
                } else {
                    // Show error message if validation fails
                    JOptionPane.showMessageDialog(null, "Error reenter credit card number with no dashes or spaces or reenter 3 digit CVC");
                }
            }
        });
        // Add the confirm button to the main panel
        mainPanel.add(confirmButton);

        // Add main menu button to return to main window
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the buyInFrame
                mainWindow.setVisible(true); // Show the main menu
            }
        });
        // Add the main menu button to the main panel
        mainPanel.add(mainMenuButton);

        // Add the main panel to the frame
        add(mainPanel);
    }
}
