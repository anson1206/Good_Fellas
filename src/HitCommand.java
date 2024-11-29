/***
 * Hit Command
 * This class is used to represent the hit command
 * This class implements the Command interface
 * This class is the concrete command class in the command pattern
 */
public class HitCommand implements Command {
    private Player player;
    private Deck deck;

    public HitCommand(Player player, Deck deck) {
        this.player = player;
        this.deck = deck;
    }

    @Override
    public void execute() {
        player.hit(deck.draw());
    }


}
