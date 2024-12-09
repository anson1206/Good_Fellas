package Casino;

/***
 * Betting Chips
 * This class is used to represent the betting chips
 * This class is the product class in the builder pattern
 * Anson Graumann, John McIntosh
 */
public class BettingChipsMain {
    private int amount;

    //constructor
    public BettingChipsMain(int amount) {
        this.amount = amount;
    }

    //getter and setter
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    //removes the amount of chips from the player
    public int removeAmount(int chips) {
        this.amount -= chips;
        return this.amount;
    }

    //adds the amount of chips to the player
    public int addAmount(int winnings){
        this.amount += winnings;
        return this.amount;
    }

}
