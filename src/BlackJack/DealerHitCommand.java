package BlackJack;
/***
 * Dealer Hit Command
 * This class is used to represent the hit command
 * This class implements the Command interface
 * This class is the concrete command class in the command pattern
 * Anson Graumann
 */
public class DealerHitCommand implements Command{
    private Dealer dealer;

    //constructor
    public DealerHitCommand(Dealer dealer) {
        this.dealer = dealer;
    }

    //execute method
    @Override
    public void execute() {
        dealer.hit();
    }
}
