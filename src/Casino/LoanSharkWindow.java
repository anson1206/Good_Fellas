package Casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoanSharkWindow extends JFrame {
    private static double maxLoanLimit = 1000.0; // Maximum loan limit
    private static double currentDebt = 0.0;    // Static variable to persist debt

    private MainWindow mainMenu;
    private JTextField loanAmountField;
    private JLabel instructionsLabel;
    private JLabel messageLabel;



    public LoanSharkWindow(MainWindow mainMenu) {
        this.mainMenu = mainMenu;

        // Inside the constructor
        BackgroundPanel mainPanel = new BackgroundPanel("src/Casino/Images/lennyloan_shark.png");
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(mainPanel);

        setTitle("Lenny the Loan Shark");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());



        JLabel titleLabel = new JLabel("Lenny the Loan Shark");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        instructionsLabel = new JLabel(getInstructionsText());
        instructionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(instructionsLabel);

        loanAmountField = new JTextField();
        loanAmountField.setMaximumSize(new Dimension(200, 30));
        loanAmountField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(loanAmountField);

        JButton borrowButton = new JButton("Borrow");
        borrowButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(borrowButton);

        messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(messageLabel);

        add(mainPanel, BorderLayout.CENTER);

        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBorrow();
            }
        });
    }

    private String getInstructionsText() {
        return String.format("<html><center>Need more chips? Borrow from Lenny!<br>"
                + "Max loan limit: $%.2f<br>"
                + "Current debt: $%.2f</center></html>", maxLoanLimit, currentDebt);
    }

    private void handleBorrow() {
        try {
            double requestedLoan = Double.parseDouble(loanAmountField.getText());

            if (requestedLoan <= 0) {
                messageLabel.setText("Please enter a valid amount greater than 0.");
            } else if (currentDebt + requestedLoan > maxLoanLimit) {
                messageLabel.setText("Loan exceeds maximum limit! Max: $" + maxLoanLimit);
            } else {
                currentDebt += requestedLoan; // Update static debt
                mainMenu.updateBalance(mainMenu.balance + requestedLoan); // Add loan to balance
                messageLabel.setText("Loan approved! You borrowed $" + requestedLoan);
                JOptionPane.showMessageDialog(this, "You borrowed $" + requestedLoan + ". Pay it back, or else...");
                instructionsLabel.setText(getInstructionsText()); // Update displayed debt
                loanAmountField.setText(""); // Clear input field
            }
        } catch (NumberFormatException ex) {
            messageLabel.setText("Please enter a valid loan amount.");
        }
    }
}
