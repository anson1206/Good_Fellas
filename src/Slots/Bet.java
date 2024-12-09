package Slots;

public class Bet {
    private int amount;
    private int minBet;
    private int maxBet;

    public Bet(int amount, int minBet, int maxBet) {
        this.amount = amount;
        this.minBet = minBet;
        this.maxBet = maxBet;
    }

    public int getAmount() {
        return amount;
    }

    public int getMinBet() {
        return minBet;
    }

    public int getMaxBet() {
        return maxBet;
    }

    public boolean isValid() {
        return amount >= minBet && amount <= maxBet;
    }

    public String getValidationMessage() {
        if (amount < minBet) {
            return "Bet is below the minimum allowed bet of $" + minBet;
        }
        if (amount > maxBet) {
            return "Bet exceeds the maximum allowed bet of $" + maxBet;
        }
        return "Bet is valid.";
    }
}
