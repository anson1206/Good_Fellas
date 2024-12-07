package Slots;

public class SpinCommand implements Command {
    private GameLogic gameLogic;
    private MessageManager messageManager;

    public SpinCommand(GameLogic gameLogic, MessageManager messageManager) {
        this.gameLogic = gameLogic;
        this.messageManager = messageManager;
    }

    @Override
    public void execute() {
        String[] reels = gameLogic.spinReels(); // Get random reels
        gameLogic.updateReels(reels); // Notify MessageManager to update reels
        String result = gameLogic.playGame(reels); // Get the result of the spin
        messageManager.setMessage(result); // Notify MessageManager to update the result
    }
}
