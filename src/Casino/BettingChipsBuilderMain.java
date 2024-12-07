package Casino;

/***
 * Betting Chips Interface
 * This class is the interface class for the builder pattern
 */
public interface BettingChipsBuilderMain {
    void setAmount(int amount);
    void removeAmount(int chips);
    int getChips();
    BettingChipsMain build();
}
