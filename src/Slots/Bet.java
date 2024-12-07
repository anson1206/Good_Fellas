package Slots;

public class Bet {
    private double amount;
    private double minBet;
    private double maxBet;

    public Bet(double amount, double minBet, double maxBet) {
        this.amount = amount;
        this.minBet = minBet;
        this.maxBet = maxBet;
    }

    public double getAmount() {
        return amount;
    }

    public double getMinBet() {
        return minBet;
    }

    public double getMaxBet() {
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
