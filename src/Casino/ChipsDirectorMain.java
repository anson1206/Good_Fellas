package Casino;

/***
 * Chips Director
 * This class manages the construction of the betting chips
 * This class is the director class in the builder pattern
 * Anson Graumann
 */
public class ChipsDirectorMain {
    private BettingChipsBuilderMain bettingChipsBuilder;

    //constructor
    public ChipsDirectorMain(BettingChipsBuilderMain bettingChipsBuilder) {
        this.bettingChipsBuilder = bettingChipsBuilder;
    }

    //constructs the betting chips
    public BettingChipsMain construct (int amount) {
        bettingChipsBuilder.setAmount(amount);
        return bettingChipsBuilder.build();
    }
}
