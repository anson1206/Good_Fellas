package BlackJack;

/***
 * Betting Chips Interface
 * This class is the interface class for the builder pattern
 */
public interface BettingChipsBuilder {
    void setAmount(int amount);
    void removeAmount(int chips);
    int getChips();
    BettingChips build();
}
