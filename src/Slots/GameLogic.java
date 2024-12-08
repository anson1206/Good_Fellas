package Slots;

import java.util.Random;

public class GameLogic {
    private SlotMachinesTemplate machine;
    private MessageManager messageManager;

    public GameLogic(SlotMachinesTemplate machine, MessageManager messageManager) {
        this.machine = machine;
        this.messageManager = messageManager;
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

    public boolean validateBet(double bet) {
        if (bet < machine.getBetMinimum()) {
            messageManager.setMessage("Bet below minimum! Bet must be at least $" + machine.getBetMinimum() +
                    "\nMessage from the Goodfellas: Put up some money or get out of our casino.");
            return false;
        }

        if (bet > machine.getMaxBet()) {
            messageManager.setMessage("Bet exceeds maximum! Bet must be below $" + machine.getMaxBet() +
                    "\nMessage from the Goodfellas: Slow down there high roller, either bet less here or go check out Russian Roulette.");
            return false;
        }

        if (bet > machine.balance) {
            messageManager.setMessage("Bet exceeds your current balance of $" + machine.balance +
                    "\nMessage from the Goodfellas: Maybe visit Lenny the Loan Shark?");
            return false;
        }

        return true; // Bet is valid
    }

    public String playGame(double bet, String[] reels) {
        // Deduct bet from balance
        machine.balance -= bet;

        // Determine game outcome
        if (reels[0].equals(reels[1]) && reels[1].equals(reels[2])) {
            double winnings = bet * machine.getWinMultiplier();
            machine.balance += winnings;
            messageManager.updateBalance(machine.balance); // Update balance via MessageManager
            return "You won $" + winnings + "! Current balance: $" + machine.balance;
        } else {
            messageManager.updateBalance(machine.balance); // Update balance via MessageManager
            return "You lost. Current balance: $" + machine.balance;
        }
    }

    public void updateReels(String[] reels) {
        messageManager.setReelImages(reels); // Notify UI with updated images
    }
}
