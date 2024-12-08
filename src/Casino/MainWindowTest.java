package Casino;

import javax.swing.*;
import java.awt.*;
import Slots.SlotMachineUI;
import BlackJack.*;
import Roulette.RouletteGameGUI;

public class MainWindowTest extends JFrame {
    private JTextField chipField;
    private JButton buyChipsButton;
    private JButton slotsButton;
    private JButton rouletteButton;
    private JButton blackJackButton;
    private int amount;
    private ChipsDirectorMain chipsDirector;
    private BettingChipsMain playerChips;
    private SlotMachineUI slotMachineUI;
    private GameUI blackJackWindow;

    public MainWindowTest() {
        setTitle("GoodFellas Casino");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use a custom background panel
        BackgroundPanel mainPanel = new BackgroundPanel("src/Casino/Images/goodfellas_casino.png");
        mainPanel.setLayout(null); // Use absolute layout for precise positioning

        // Create a panel for the "Enter amount" label and text field
        JPanel chipInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chipInputPanel.setBackground(new Color(0, 0, 0, 150));
        chipInputPanel.setOpaque(true); // Transparent background for the panel
        chipInputPanel.setBounds(400, 350, 300, 30); // Position above the "Roulette" button

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
        buyChipsButton.setBounds(700, 355, 100, 20); // Position above "BlackJack"
        mainPanel.add(buyChipsButton);

        // Game Buttons
        slotsButton = new JButton("Slots");
        slotsButton.setBounds(800, 420, 100, 30); // Center below "Enter Amount"
        mainPanel.add(slotsButton);

        rouletteButton = new JButton("Roulette");
        rouletteButton.setBounds(400, 420, 100, 30); // Center below the curtain
        mainPanel.add(rouletteButton);

        blackJackButton = new JButton("BlackJack");
        blackJackButton.setBounds(600, 420, 100, 30); // Center below "Buy Chips"
        mainPanel.add(blackJackButton);

        // Action Listeners
        chipsDirector = new ChipsDirectorMain(new ChipsBuilderMain());
        buyChipsButton.addActionListener(e -> {
            try {
                amount = Integer.parseInt(chipField.getText());
                playerChips = chipsDirector.construct(amount);
                JOptionPane.showMessageDialog(null, "You have bought " + playerChips.getAmount() + " chips.");
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
                RouletteGameGUI gameGUI = RouletteGameGUI.getInstance();
                gameGUI.createAndShowGUI(playerChips); // Open Roulette game
            }
        });

        blackJackButton.addActionListener(e -> {
            if (playerChips == null) {
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

                    blackJackWindow = new GameUI(player, dealer, invoker, phitCommand, pstandCommand, dhitCommand, playerChips, this);
                    subject.add(blackJackWindow);
                }
                blackJackWindow.setVisible(true);
            }
        });

        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindowTest().setVisible(true));
    }
}
