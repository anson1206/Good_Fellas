package Casino;

/***
 * Chips Builder
 * This class is used to represent the betting chips builder
 * This class is the concrete builder class in the builder pattern
 * Anson Graumann
 */
public class ChipsBuilderMain implements BettingChipsBuilderMain {
    private int amount;

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }
    @Override
    public int getChips() {
        return amount;
    }
    @Override
    public BettingChipsMain build() {
        return new BettingChipsMain(amount);
    }
}
