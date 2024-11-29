/***
 * Stand Command Class
 * This class is used to represent the stand command
 * This class implements the Command interface
 * This class is the concrete command class in the command pattern
 */
public class StandCommand implements Command {
    private Player player;

    public StandCommand(Player player) {
        this.player = player;
    }

    @Override
    public void execute() {
        player.stand();
    }
}
