package Casino;

/***
 * Chips Director
 * This class manages the construction of the betting chips
 * This class is the director class in the builder pattern
 */
public class ChipsDirectorMain {
    private BettingChipsBuilderMain bettingChipsBuilder;

    public ChipsDirectorMain(BettingChipsBuilderMain bettingChipsBuilder) {
        this.bettingChipsBuilder = bettingChipsBuilder;
    }

    public BettingChipsMain construct (int amount) {
        bettingChipsBuilder.setAmount(amount);
        return bettingChipsBuilder.build();
    }
}
