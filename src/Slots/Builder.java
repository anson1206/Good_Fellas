package Slots;

public class Builder {
    private double amount;
    private double minBet;
    private double maxBet;

    public Builder setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public Builder setMinBet(double minBet) {
        this.minBet = minBet;
        return this;
    }

    public Builder setMaxBet(double maxBet) {
        this.maxBet = maxBet;
        return this;
    }

    public Bet build() {
        return new Bet(amount, minBet, maxBet);
    }
}
