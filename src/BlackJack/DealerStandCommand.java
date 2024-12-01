package BlackJack;
/***
 * Dealer Stand Command
 * This class is used to represent the stand command
 * This class implements the Command interface
 * This class is the concrete command class in the command pattern
 */
public class DealerStandCommand implements Command {
    private Dealer dealer;

    //constructor
    public DealerStandCommand(Dealer dealer) {
        this.dealer = dealer;
    }

    //execute method
    @Override
    public void execute() {
        dealer.stand();
    }
}
