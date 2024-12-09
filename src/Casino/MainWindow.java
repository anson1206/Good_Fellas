package Casino;

import javax.swing.*;
import java.awt.*;

import Roulette.WinPopup;
import RussainRoulette.RussianRouletteGameGUI;
import Slots.SlotMachineUI;
import BlackJack.*;
import Roulette.RouletteGameGUI;
import RussainRoulette.WinsPopup;

public class MainWindow extends JFrame {
    private JTextField chipField;
    private JButton buyChipsButton;
    private JButton slotsButton;
    private JButton rouletteButton;
    private JButton blackJackButton;
    private JButton loanSharkButton;
    private JButton russianRouletteButton;
    private JLabel balanceLabel;
    private int amount;
    public int balance = 500; // Initial balance
    private ChipsDirectorMain chipsDirector;
    public BettingChipsMain playerChips;
    private SlotMachineUI slotMachineUI;
    private BlackJackGameUI blackJackWindow;
    public int currentDebt;
    private Label debtLabel;

    public MainWindow() {
        setTitle("GoodFellas Casino");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use a custom background panel
        BackgroundPanel mainPanel = new BackgroundPanel("src/Casino/Images/goodfellas_casino.png");
        mainPanel.setLayout(null); // Use absolute layout for precise positioning

        // Balance Label
        balanceLabel = new JLabel("Balance: $" + balance);
        balanceLabel.setBounds(300, 320, 200, 30); // Position above "Enter Amount"
        balanceLabel.setForeground(Color.WHITE);
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(balanceLabel);

        // Create a panel for the "Enter amount" label and text field
        JPanel chipInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chipInputPanel.setBackground(new Color(0, 0, 0, 150));
        chipInputPanel.setForeground(Color.BLACK);
        chipInputPanel.setOpaque(true);
        chipInputPanel.setBounds(300, 350, 300, 30); // Position above the "Roulette" button

        // "Enter Amount" Label
        JLabel chipLabel = new JLabel("Enter amount to buy chips:");
        chipLabel.setForeground(Color.WHITE); // White font for visibility

        // Chip Input Field
        chipField = new JTextField(10);

        // Add components to the panel
        chipInputPanel.add(chipLabel);
        chipInputPanel.add(chipField);

        // Add the chip input panel to the main panel
        mainPanel.add(chipInputPanel);

        // "Buy Chips" Button
        buyChipsButton = new JButton("Buy Chips");
        buyChipsButton.setBounds(650, 355, 100, 20); // Position near "BlackJack"
        mainPanel.add(buyChipsButton);

        // "Lenny the Loan Shark" Button
        loanSharkButton = new JButton("Lenny the Loan Shark");
        loanSharkButton.setBounds(650, 385, 200, 20); // Position below "Buy Chips"
        mainPanel.add(loanSharkButton);

        // Game Buttons
        slotsButton = new JButton("Slots");
        slotsButton.setBounds(850, 420, 100, 30); // Position below "Enter Amount"
        mainPanel.add(slotsButton);

        rouletteButton = new JButton("Roulette");
        rouletteButton.setBounds(450, 420, 100, 30); // Center below the curtain
        mainPanel.add(rouletteButton);

        russianRouletteButton = new JButton("Russian Roulette");
        russianRouletteButton.setBounds(200, 420, 150, 30); // Center below "Buy Chips"
        mainPanel.add(russianRouletteButton);

        blackJackButton = new JButton("BlackJack");
        blackJackButton.setBounds(650, 420, 100, 30); // Center below "Buy Chips"
        mainPanel.add(blackJackButton);



        // Action Listeners
        chipsDirector = new ChipsDirectorMain(new ChipsBuilderMain());
        buyChipsButton.addActionListener(e -> {
            try {
                amount = Integer.parseInt(chipField.getText());
                if (amount > balance) {
                    JOptionPane.showMessageDialog(null, "Insufficient balance! Your current balance is $" + balance);
                } else if (amount <= 0) {
                    JOptionPane.showMessageDialog(null, "Enter a valid amount greater than 0.");
                } else {
                    balance -= amount; // Deduct from balance
                    playerChips = chipsDirector.construct(amount);
                    balanceLabel.setText("Balance: $" + balance); // Update balance label
                    JOptionPane.showMessageDialog(null, "You have bought " + playerChips.getAmount() + " chips.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid amount.");
            }
        });


        slotsButton.addActionListener(e -> {
            if (playerChips == null) {
                JOptionPane.showMessageDialog(null, "Please buy chips first!");
            } else {
                setVisible(false); // Hide the main menu
                slotMachineUI = new SlotMachineUI(playerChips, this);
                slotMachineUI.setVisible(true); // Open Slots game window
            }
        });

        rouletteButton.addActionListener(e -> {
            if (playerChips == null) {
                JOptionPane.showMessageDialog(null, "Please buy chips first!");
            } else {
                // getting instance of the RouletteGameGUI for singleton pattern
                RouletteGameGUI gameGUI = RouletteGameGUI.getInstance();
                gameGUI.createAndShowGUI(playerChips); // Open Roulette game
                // adding observer to the gameGUI
                gameGUI.addObserver(new WinPopup());
            }
        });

        russianRouletteButton.addActionListener(e -> {
            if (playerChips == null) {
                JOptionPane.showMessageDialog(null, "Please buy chips first!");
            } else {
                // getting instance of the RussianRouletteGameGUI for singleton pattern
                RussianRouletteGameGUI gameGUI = RussianRouletteGameGUI.getInstance();
                gameGUI.createAndShowGUI(playerChips); // Open Roulette game
                // adding observer to the gameGUI
                gameGUI.addsObserver(new WinsPopup());
            }
        });

        blackJackButton.addActionListener(e -> {
            if (playerChips == null || playerChips.getAmount() <= 0) {
                JOptionPane.showMessageDialog(null, "Please buy chips first!");
            } else {
                if (blackJackWindow == null) {
                    Deck deck = Deck.getInstance();
                    Subject subject = new Subject();
                    Player player = new Player("Player", deck, subject);
                    Dealer dealer = new Dealer(deck, player, subject);
                    Invoker invoker = new Invoker();

                    Command phitCommand = new PlayerHitCommand(player);
                    Command pstandCommand = new PlayerStandCommand(player);
                    Command dhitCommand = new DealerHitCommand(dealer);

                    player.setHitCommand(phitCommand);
                    player.setStandCommand(pstandCommand);
                    dealer.setHitCommand(dhitCommand);

                    blackJackWindow = new BlackJackGameUI(player, dealer, invoker, phitCommand, pstandCommand, dhitCommand, playerChips, this);
                    subject.add(blackJackWindow);
                }
                blackJackWindow.refreshChips(); // Ensure the chip amount is refreshed
                blackJackWindow.setVisible(true);
            }
        });

        loanSharkButton.addActionListener(e -> {
            new LoanSharkWindow(this).setVisible(true); // Open Loan Shark page
        });

        add(mainPanel);
    }

    public void updateBalance(int newBalance) {
        this.balance = newBalance;
        balanceLabel.setText("Balance: $" + balance); // Update the balance label
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}