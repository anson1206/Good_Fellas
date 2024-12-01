package BlackJack;

/***
 * Player Hit Command
 * This class is used to represent the hit command
 * This class implements the Command interface
 * This class is the concrete command class in the command pattern
 */
public class PlayerHitCommand implements Command {
    private Player player;

    //constructor
    public PlayerHitCommand(Player player) {
        this.player = player;
    }

    //execute method
    @Override
    public void execute() {
        player.hit();

    }
}