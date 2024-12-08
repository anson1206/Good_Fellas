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
        setTitle("Welcome to the Casino");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel chipPanel = new JPanel();
        chipPanel.add(new JLabel("Enter amount to buy chips:"));
        chipField = new JTextField(10);
        chipPanel.add(chipField);
        buyChipsButton = new JButton("Buy Chips");
        chipPanel.add(buyChipsButton);
        add(chipPanel, BorderLayout.NORTH);

        JPanel gamePanel = new JPanel();
        slotsButton = new JButton("Slots");
        rouletteButton = new JButton("Roulette");
        blackJackButton = new JButton("BlackJack");
        gamePanel.add(slotsButton);
        gamePanel.add(rouletteButton);
        gamePanel.add(blackJackButton);
        add(gamePanel, BorderLayout.CENTER);

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
                    // Initialize and open BlackJack game window
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindowTest().setVisible(true));
    }
}
