package Slots;

import Casino.BettingChipsMain;
import Casino.MainWindow;

import java.util.Random;

public class GameLogic {
    private SlotMachinesTemplate machine;
    private MessageManager messageManager;
    private BettingChipsMain playerChips;
    private MainWindow main;

    public GameLogic(SlotMachinesTemplate machine, MessageManager messageManager, BettingChipsMain playerChips, MainWindow main) {
        this.machine = machine;
        this.messageManager = messageManager;
        this.playerChips = playerChips;
        this.main = main;
    }

    public String[] spinReels() {
        Random random = new Random();
        String[] reels = new String[3];
        for (int i = 0; i < 3; i++) {
            int randomIndex = random.nextInt(3); // Randomize the index for the symbols
            reels[i] = machine.getSymbol(randomIndex);
        }
        return reels;
    }

    public boolean validateBet(int bet) {
        if (bet < machine.getBetMinimum()) {
            messageManager.setMessage("Bet below minimum! Bet must be at least $" + machine.getBetMinimum());
            return false;
        }

        if (bet > machine.getMaxBet()) {
            messageManager.setMessage("Bet exceeds maximum! Bet must be below $" + machine.getMaxBet());
            return false;
        }

        if (bet > main.playerChips.getAmount()) { // Use chips only
            messageManager.setMessage("Bet exceeds your current chip balance of $" + playerChips.getAmount() + "\nGo Visit Lenny the Loan Shark");
            return false;
        }

        return true; // Bet is valid
    }

    public String playGame(int bet, String[] reels) {
        // Deduct bet from chips only
        main.playerChips.removeAmount(bet);

        // Determine game outcome
        if (reels[0].equals(reels[1]) && reels[1].equals(reels[2])) {
            int winnings = bet * machine.getWinMultiplier();
            main.playerChips.addAmount(winnings); // Add winnings to chips only
            return "You won $" + winnings + "! Current chips: $" + main.playerChips.getAmount();
        } else {
            return "You lost. Current chips: $" + main.playerChips.getAmount();
        }
    }
    public void updateReels(String[] reels) {
        messageManager.setReelImages(reels); // Notify UI with updated images
    }
}