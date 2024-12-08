package Casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoanSharkWindow extends JFrame {
    private MainWindowTest mainMenu;
    private JTextField loanAmountField;
    private JButton borrowButton;
    private JLabel messageLabel;

    private double maxLoanLimit = 1000.0; // Maximum loan limit
    private double currentDebt = 0.0;    // Current debt

    public LoanSharkWindow(MainWindowTest mainMenu) {
        this.mainMenu = mainMenu;

        setTitle("Lenny the Loan Shark");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Lenny the Loan Shark");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        JLabel instructionsLabel = new JLabel("<html><center>Need more chips? Borrow from Lenny!<br>"
                + "Max loan limit: $" + maxLoanLimit + "<br>"
                + "Current debt: $" + currentDebt + "</center></html>");
        instructionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(instructionsLabel);

        loanAmountField = new JTextField();
        loanAmountField.setMaximumSize(new Dimension(200, 30));
        loanAmountField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(loanAmountField);

        borrowButton = new JButton("Borrow");
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

    private void handleBorrow() {
        try {
            double requestedLoan = Double.parseDouble(loanAmountField.getText());

            if (requestedLoan <= 0) {
                messageLabel.setText("Please enter a valid amount greater than 0.");
            } else if (currentDebt + requestedLoan > maxLoanLimit) {
                messageLabel.setText("Loan exceeds maximum limit! Max: $" + maxLoanLimit);
            } else {
                currentDebt += requestedLoan; // Update debt
                mainMenu.updateBalance(mainMenu.balance + requestedLoan); // Add loan to balance
                messageLabel.setText("Loan approved! You borrowed $" + requestedLoan);
                JOptionPane.showMessageDialog(this, "You borrowed $" + requestedLoan + ". Pay it back, or else...");
                dispose(); // Close the loan shark window
            }
        } catch (NumberFormatException ex) {
            messageLabel.setText("Please enter a valid loan amount.");
        }
    }
}
