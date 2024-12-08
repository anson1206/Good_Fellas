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
        if (!gameLogic.validateBet(bet)) {
            return; // Exit if the bet is invalid
        }

        String[] reels = gameLogic.spinReels(); // Spin the reels
        gameLogic.updateReels(reels); // Update UI with new reels
        String result = gameLogic.playGame(bet, reels); // Process game logic and get result
        messageManager.setMessage(result); // Update UI with the result
    }
}
