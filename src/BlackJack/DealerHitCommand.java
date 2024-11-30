package BlackJack;
/***
 * Dealer Hit Command
 * This class is used to represent the hit command
 * This class implements the Command interface
 * This class is the concrete command class in the command pattern
 */
public class DealerHitCommand implements Command{
    //private CardReceiver receiver;
    private Deck deck;
    private Player player;
    private Dealer dealer;

    public DealerHitCommand(Dealer dealer, Deck deck) {
        this.dealer = dealer;
        this.deck = deck;

    }

    @Override
    public void execute() {
        Card card = deck.draw();
        dealer.getHand().add(card);
        System.out.println("Dealer drew " + card.getRank() + " value: " + card.getValue());
    }
}
