package Casino;

/***
 * Betting Chips
 * This class is used to represent the betting chips
 * This class is the product class in the builder pattern
 */
public class BettingChipsMain {
   // private int chips;
    private int amount;
    public BettingChipsMain(int amount) {
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

    public int addAmount(int winnings){
        this.amount += winnings;
        return this.amount;
    }

}
