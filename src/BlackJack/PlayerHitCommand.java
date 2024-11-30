package BlackJack;

/***
 * Player Hit Command
 * This class is used to represent the hit command
 * This class implements the Command interface
 * This class is the concrete command class in the command pattern
 */
public class PlayerHitCommand implements Command {
    private CardReceiver receiver;
    private Deck deck;
    private Player player;
    private Dealer dealer;

    public PlayerHitCommand(Player player, Deck deck) {
        this.player = player;
        this.deck = deck;

    }

    @Override
    public void execute() {
        Card card = deck.draw();
        player.getHand().add(card);
        System.out.println(player.getName() + " drew " + card.getRank() + " value: " + card.getValue());
    }
}