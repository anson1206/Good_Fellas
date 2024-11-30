package BlackJack;

/***
 * Player Stand Command Class
 * This class is used to represent the stand command
 * This class implements the Command interface
 * This class is the concrete command class in the command pattern
 */
public class PlayerStandCommand implements Command {
    private CardReceiver receiver;
    private Deck deck;
    private Player player;
    private Dealer dealer;

    public PlayerStandCommand(Player player) {
        this.player = player;

    }

    @Override
    public void execute() {
        System.out.println(player.getName() + " stands");
        player.getScore();

    }
}
