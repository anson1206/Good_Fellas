package Slots;

public class Builder {
    private int amount;
    private int minBet;
    private int maxBet;

    public Builder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public Builder setMinBet(int minBet) {
        this.minBet = minBet;
        return this;
    }

    public Builder setMaxBet(int maxBet) {
        this.maxBet = maxBet;
        return this;
    }

    public Bet build() {
        return new Bet(amount, minBet, maxBet);
    }
}
