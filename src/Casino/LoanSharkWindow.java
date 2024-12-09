//John, Anson
package Casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoanSharkWindow extends JFrame {
    private static int maxLoanLimit = 1000; // Maximum loan limit
    private static int currentDebt = 0;    // Static variable to persist debt

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

        JLabel LennyName = new JLabel();
        LennyName.setText("Lenny the Loan Shark");
        LennyName.setFont(new Font("Arial",Font.BOLD,20));
        LennyName.setAlignmentX(Component.CENTER_ALIGNMENT);
        LennyName.setBackground(new Color(255,255,255,150));
        LennyName.setOpaque(true);
        mainPanel.add(LennyName);



        instructionsLabel = new JLabel(getInstructionsText());
        instructionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //instructionsLabel.setBackground(new Color(255,255,255,150));
        instructionsLabel.setForeground(Color.GREEN);
        instructionsLabel.setFont(new Font("Arial",Font.BOLD, 20));
        //instructionsLabel.setOpaque(true);
        //instructionsLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(instructionsLabel);

        loanAmountField = new JTextField();
        loanAmountField.setMaximumSize(new Dimension(200, 30));
        loanAmountField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(loanAmountField);

        JButton borrowButton = new JButton("Borrow");
        borrowButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(borrowButton);

        // Payback Button
        JButton paybackButton = new JButton("Payback");
        paybackButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(paybackButton);

        messageLabel = new JLabel(" ");
        messageLabel.setForeground(Color.RED);
        messageLabel.setFont(new Font("Arial",Font.BOLD, 30));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(messageLabel);

        add(mainPanel, BorderLayout.CENTER);

        // Main Menu Button
        JButton mainMenuButton = new JButton("Main Menu");
        //mainMenuButton.setBounds(100, 240, 100, 30);
        mainMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainMenuButton.addActionListener(e -> {
            dispose(); // Close the LoanSharkWindow
            mainMenu.setVisible(true); // Make the main menu visible again
        });
        mainPanel.add(mainMenuButton);


        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBorrow();
            }
        });

        // Payback Button
        paybackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePayback();
            }
        });
    }

    private String getInstructionsText() {
        return String.format("<html>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Need more chips? Borrow from Lenny!"
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Max loan limit: "+ maxLoanLimit
                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Current debt: " + currentDebt  + "<html>");
    }

    private void handleBorrow() {

            try {
                int requestedLoan = Integer.parseInt(loanAmountField.getText());

                if (requestedLoan <= 0) {
                    messageLabel.setText("Please enter a valid amount greater than 0.");
                } else if (currentDebt + requestedLoan > maxLoanLimit) {
                    messageLabel.setText("Loan exceeds maximum limit! Max: $" + maxLoanLimit);
                } else {
                    currentDebt += requestedLoan ; // Update Lenny's debt
                    mainMenu.updateBalance(mainMenu.balance + requestedLoan); // Add loan to main balance
                    JOptionPane.showMessageDialog(this, "You borrowed $" + requestedLoan + ". Use it wisely!");
                    refreshUI();
            }
            } catch (NumberFormatException ex) {
                messageLabel.setText("Please enter a valid loan amount.");
            }

    }

    //payback method
    private void handlePayback() {
        int totalOwed = currentDebt ;
        int moneyOwed = Integer.parseInt(loanAmountField.getText());
        int paymentAmount = Integer.parseInt(loanAmountField.getText());

        if (paymentAmount <= 0) {
            messageLabel.setText("Please enter a valid amount greater than 0.");
        } else if (paymentAmount != currentDebt) {
            messageLabel.setText("Incorrect amount! You need to pay exactly $" + currentDebt);
        } else if (mainMenu.balance < paymentAmount) {
            messageLabel.setText("Insufficient balance to pay off the loan!");
        } else {
            mainMenu.updateBalance(mainMenu.balance - paymentAmount); // Deduct payment from balance
            currentDebt = 0; // Reset debt
            messageLabel.setText("You have paid off your loan!");
            refreshUI();

            // Show popup
            JOptionPane.showMessageDialog(this, "Loan paid off! You no longer owe Lenny.");

        }
    }

    private void refreshUI() {
        instructionsLabel.setText(getInstructionsText());
        instructionsLabel.revalidate();
        instructionsLabel.repaint();
    }
}