package Slots;

import Casino.BettingChipsMain;

import java.util.Random;

public class GameLogic {
    private SlotMachinesTemplate machine;
    private MessageManager messageManager;
    private BettingChipsMain playerChips;

    public GameLogic(SlotMachinesTemplate machine, MessageManager messageManager, BettingChipsMain playerChips) {
        this.machine = machine;
        this.messageManager = messageManager;
        this.playerChips = playerChips;
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

        if (bet > playerChips.getAmount()) { // Use chips only
            messageManager.setMessage("Bet exceeds your current chip balance of $" + playerChips.getAmount());
            return false;
        }

        return true; // Bet is valid
    }

    public String playGame(int bet, String[] reels) {
        // Deduct bet from chips only
        playerChips.removeAmount(bet);

        // Determine game outcome
        if (reels[0].equals(reels[1]) && reels[1].equals(reels[2])) {
            int winnings = bet * machine.getWinMultiplier();
            playerChips.addAmount(winnings); // Add winnings to chips only
            return "You won $" + winnings + "! Current chips: $" + playerChips.getAmount();
        } else {
            return "You lost. Current chips: $" + playerChips.getAmount();
        }
    }
    public void updateReels(String[] reels) {
        messageManager.setReelImages(reels); // Notify UI with updated images
    }
}
