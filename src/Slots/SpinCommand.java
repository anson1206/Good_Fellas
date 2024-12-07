package Slots;

public class SpinCommand implements Command {
    private GameLogic gameLogic;
    private MessageManager messageManager;
    private double bet;

    public SpinCommand(GameLogic gameLogic, MessageManager messageManager, double bet) {
        this.gameLogic = gameLogic;
        this.messageManager = messageManager;
        this.bet = bet;
    }

    @Override
    public void execute() {
        // Spin the reels
        String[] reels = gameLogic.spinReels();

        // Update the reels display via MessageManager
        messageManager.setMessage("Reels: [" + reels[0] + "] [" + reels[1] + "] [" + reels[2] + "]");

        // Play the game logic
        gameLogic.playGame(bet, reels);
    }
}
