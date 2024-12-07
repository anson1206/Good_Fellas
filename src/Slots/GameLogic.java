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
        int reel1 = random.nextInt(3);
        int reel2 = random.nextInt(3);
        int reel3 = random.nextInt(3);

        return new String[]{
                machine.getSymbol(reel1),
                machine.getSymbol(reel2),
                machine.getSymbol(reel3)
        };
    }

    public void playGame(double bet, String[] reels) {
        if (machine.balance == 0) {
            messageManager.setMessage("You are broke! Go visit Lenny the Loan Shark!");
            return;
        }

        if (bet < machine.getBetMinimum()) {
            messageManager.setMessage("Bet must be at least $" + machine.getBetMinimum());
            return;
        }

        if (bet > machine.getMaxBet()) {
            messageManager.setMessage("Bet exceeds the maximum allowed bet of $" + machine.getMaxBet());
            return;
        }

        if (bet > machine.balance) {
            messageManager.setMessage("You don't have enough balance! Lower your bet.");
            return;
        }

        // Deduct bet
        machine.balance -= bet;

        // Check for win
        if (reels[0].equals(reels[1]) && reels[1].equals(reels[2])) {
            double winnings = bet * machine.getWinMultiplier();
            machine.balance += winnings;
            messageManager.setMessage("Reels: [" + reels[0] + "] [" + reels[1] + "] [" + reels[2] + "]");
            messageManager.setMessage("You won $" + winnings + "!");
        } else {
            messageManager.setMessage("Reels: [" + reels[0] + "] [" + reels[1] + "] [" + reels[2] + "]");
            messageManager.setMessage("You lost this round.");
        }

        // Update balance
        messageManager.setMessage("Balance: $" + machine.balance);
    }

    public double getBalance() {
        return machine.balance;
    }
}
