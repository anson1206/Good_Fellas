package BlackJack;
/***
 * Chips Director
 * This class manages the construction of the betting chips
 * This class is the director class in the builder pattern
 */
public class ChipsDirector {
    private BettingChipsBuilder bettingChipsBuilder;

    public ChipsDirector(BettingChipsBuilder bettingChipsBuilder) {
        this.bettingChipsBuilder = bettingChipsBuilder;
    }

    public  BettingChips construct (int amount) {
        bettingChipsBuilder.setAmount(amount);
        return bettingChipsBuilder.build();
    }
}
