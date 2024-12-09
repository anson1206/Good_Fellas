package Casino;

/***
 * Betting Chips Interface
 * This class is the interface class for the builder pattern
 * Anson Graumann
 */
public interface BettingChipsBuilderMain {
    void setAmount(int amount);
    int getChips();
    BettingChipsMain build();
}
