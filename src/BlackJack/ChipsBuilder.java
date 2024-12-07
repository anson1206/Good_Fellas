package BlackJack;
/***
 * Chips Builder
 * This class is used to represent the betting chips builder
 * This class is the concrete builder class in the builder pattern
 */
public class ChipsBuilder implements BettingChipsBuilder {
    private int amount;

    @Override
    public void setAmount(int amount) {
        this.amount = amount;
    }
    @Override
    public void removeAmount(int chips) {
        this.amount -= chips;
    }
    @Override
    public int getChips() {
        return amount;
    }
    @Override
    public BettingChips build() {
        return new BettingChips(amount);
    }
}
