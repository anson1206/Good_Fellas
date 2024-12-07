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

    public void updateReels(String[] reels) {
        messageManager.setReelImages(reels); // Notify UI with updated images
    }

    public String playGame(String[] reels) {
        // Example logic: Check if all reels match
        if (reels[0].equals(reels[1]) && reels[1].equals(reels[2])) {
            double winnings = machine.getWinMultiplier();
            machine.balance += winnings;
            return "You won! Balance: $" + machine.balance;
        } else {
            return "You lost. Balance: $" + machine.balance;
        }
    }
}
