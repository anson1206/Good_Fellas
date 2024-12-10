package Casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
The LoanSharkWindow class creates a user interface for borrowing and repaying "Lenny the Loan Shark".
Players can borrow loans up to 1000, with the approved loan then being added to their balance and tracked
as debt. They can also repay the full debt, but only if their entered payment matches the owed amount and
they have enough balance. Lenny updates to show the current debt, loan limits, and dispalys messages about
successful or invalid actions. He also works with the main menu, ensuring any changes to the player's
balance or debt are synced with the game.

John McIntosh
 */

// LoanSharkWindow class for handling borrowing and repayment of loans
public class LoanSharkWindow extends JFrame {

    // Static variable to store the maximum loan limit
    private static int maxLoanLimit = 1000;
    // Static variable to persist the player's current debt
    private static int currentDebt = 0;

    // Reference to the MainWindow for updating balances and returning to the main menu
    private MainWindow mainMenu;
    private JTextField loanAmountField; // Input field for loan or payment amount
    private JLabel instructionsLabel;  // Label to display instructions and current debt
    private JLabel messageLabel;       // Label to display error or success messages

    // Constructor for the LoanSharkWindow
    public LoanSharkWindow(MainWindow mainMenu) {
        this.mainMenu = mainMenu; // Assign reference to the MainWindow

        // Create a background panel with an image of Lenny
        BackgroundPanel mainPanel = new BackgroundPanel("src/Casino/Images/lennyloan_shark.png");
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Vertical layout
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around panel
        add(mainPanel); // Add the panel to the frame

        // Set the window properties
        setTitle("Lenny the Loan Shark");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Label for Lenny's name
        JLabel LennyName = new JLabel();
        LennyName.setText("Lenny the Loan Shark");
        LennyName.setFont(new Font("Arial", Font.BOLD, 20)); // Font styling
        LennyName.setAlignmentX(Component.CENTER_ALIGNMENT);
        LennyName.setBackground(new Color(255, 255, 255, 150)); // Semi-transparent background
        LennyName.setOpaque(true);
        mainPanel.add(LennyName);

        // Instructions label showing loan details
        instructionsLabel = new JLabel(getInstructionsText()); // Dynamic text with current debt
        instructionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionsLabel.setForeground(Color.GREEN); // Green text for visibility
        instructionsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(instructionsLabel);

        // Text field for user to enter loan or payment amount
        loanAmountField = new JTextField();
        loanAmountField.setMaximumSize(new Dimension(200, 30)); // Size of the text field
        loanAmountField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(loanAmountField);

        // Button for borrowing money
        JButton borrowButton = new JButton("Borrow");
        borrowButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(borrowButton);

        // Button for paying back the loan
        JButton paybackButton = new JButton("Payback");
        paybackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(paybackButton);

        // Label to display messages such as errors or success
        messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED); // Red for errors or warnings
        messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(messageLabel);

        // Add the main panel to the frame
        add(mainPanel, BorderLayout.CENTER);

        // Button to return to the main menu
        JButton mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuButton.addActionListener(e -> {
            dispose(); // Close the LoanSharkWindow
            mainMenu.setVisible(true); // Show the main menu
        });
        mainPanel.add(mainMenuButton);

        // Add action listener for the Borrow button
        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBorrow(); // Call the borrowing logic
            }
        });

        // Add action listener for the Payback button
        paybackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePayback(); // Call the repayment logic
            }
        });
    }

    // Method to generate instructions text with dynamic current debt and loan limit
    private String getInstructionsText() {
        return String.format("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Need more chips? Borrow from Lenny!"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Max loan limit: "+ maxLoanLimit
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Current debt: " + currentDebt  + "<html>");
    }

    // Logic to handle borrowing money
    private void handleBorrow() {
        try {
            int requestedLoan = Integer.parseInt(loanAmountField.getText()); // Get loan amount from input

            if (requestedLoan <= 0) { // Ensure loan amount is positive
                messageLabel.setText("Please enter a valid amount greater than 0.");
            } else if (currentDebt + requestedLoan > maxLoanLimit) { // Check if loan exceeds max limit
                messageLabel.setText("Loan exceeds maximum limit! Max: $" + maxLoanLimit);
            } else {
                currentDebt += requestedLoan; // Increase current debt
                mainMenu.updateBalance(mainMenu.balance + requestedLoan); // Add loan amount to player's balance
                JOptionPane.showMessageDialog(this, "You borrowed $" + requestedLoan + ". Pay it back... Or else");
                refreshUI(); // Refresh the UI with updated debt and balance
            }
        } catch (NumberFormatException ex) { // Handle invalid input
            messageLabel.setText("Please enter a valid loan amount.");
        }
    }

    // Logic to handle repaying the loan
    private void handlePayback() {
        try {
            int paymentAmount = Integer.parseInt(loanAmountField.getText()); // Get payment amount from input

            if (paymentAmount <= 0) { // Ensure payment amount is positive
                messageLabel.setText("Please enter a valid amount greater than 0.");
            } else if (paymentAmount != currentDebt) { // Ensure payment matches exact debt
                messageLabel.setText("Incorrect amount! You need to pay exactly $" + currentDebt);
            } else if (mainMenu.balance < paymentAmount) { // Ensure player has enough balance to pay
                messageLabel.setText("Insufficient balance to pay off the loan!");
            } else {
                mainMenu.updateBalance(mainMenu.balance - paymentAmount); // Deduct payment from balance
                currentDebt = 0; // Reset debt to zero
                messageLabel.setText("You have paid off your loan!");
                refreshUI(); // Refresh the UI with updated debt and balance

                // Show success popup
                JOptionPane.showMessageDialog(this, "Loan paid off! You no longer owe Lenny.");
            }
        } catch (NumberFormatException ex) { // Handle invalid input
            messageLabel.setText("Please enter a valid payment amount.");
        }
    }

    // Refreshes the UI components to reflect the updated debt and instructions
    private void refreshUI() {
        instructionsLabel.setText(getInstructionsText()); // Update instructions label
        instructionsLabel.revalidate(); // Refresh the label
        instructionsLabel.repaint();
    }
}
