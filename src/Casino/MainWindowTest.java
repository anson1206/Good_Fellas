package Casino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import BlackJack.*;

public class MainWindowTest extends JFrame {
    private JTextField chipField;
    private JButton buyChipsButton;
    private JButton slotsButton;
    private JButton rouletteButton;
    private JButton blackJackButton;
    private int amount;
    private ChipsDirectorMain chipsDirector;
    private BettingChipsMain playerChips;
    private GameUI window;

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

        buyChipsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                amount = Integer.parseInt(chipField.getText());
                playerChips = chipsDirector.construct(amount);
                JOptionPane.showMessageDialog(null, "You have bought " + playerChips.getAmount() + " chips.");
            }
        });

        slotsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Slots game window
                // new SlotsGameWindow(chips).setVisible(true);
            }
        });

        rouletteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Roulette game window
                // new RouletteGameWindow(chips).setVisible(true);
            }
        });

        blackJackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(window == null) {
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

                    window = new GameUI(player, dealer, invoker, phitCommand, pstandCommand, dhitCommand, playerChips,
                            MainWindowTest.this);
                    subject.add(window);
                }
                window.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindowTest().setVisible(true));
    }
}