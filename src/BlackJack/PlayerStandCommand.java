package BlackJack;

/***
 * Player Stand Command Class
 * This class is used to represent the stand command
 * This class implements the Command interface
 * This class is the concrete command class in the command pattern
 * Anson Graumann
 */
public class PlayerStandCommand implements Command {
    private Deck deck;
    private Player player;
    private Dealer dealer;

    //constructor
    public PlayerStandCommand(Player player) {
        this.player = player;
    }

    //execute method
    @Override
    public void execute() {
        player.stand();
    }
}
