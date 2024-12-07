package BlackJack;

/***
 * Betting Chips
 * This class is used to represent the betting chips
 * This class is the product class in the builder pattern
 */
public class BettingChips {
   // private int chips;
    private int amount;
    public BettingChips(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int removeAmount(int chips) {
        this.amount -= chips;
        return this.amount;
    }

}
