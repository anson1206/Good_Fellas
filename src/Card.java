public class Card {
    private String suit;
    private String rank;

    public Card( String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public int getValue() {
        switch (rank) {
            case"2":
                return 2;
            case"3":
                return 3;
            case"4":
                return 4;
            case"5":
                return 5;
            case"6":
                return 6;
            case"7":
                return 7;
            case"8":
                return 8;
            case"9":
                return 9;
            case "Jack":
                return 10;
            case "Queen":
                return 10;
            case "King":
                return 10;
            case "Ace":
                return 11;
            default:
                throw new IllegalArgumentException("Invalid card rank");

        }
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

}
