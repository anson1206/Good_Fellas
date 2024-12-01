package BlackJack;
/***
 * Card class
 * This class is used to represent a card
 * This class is used to represent a card in a deck of cards
 */
public class Card {
    private String suit;
    private String rank;
    private int value;
    public Card( String suit, String rank) {
        //System.out.println("Rank: " + rank + "Suit" + suit);
        this.suit = suit;
        this.rank = rank;
        this.value = getValue(rank);
    }

    //Returns the value of the card
    public int getValue(String rank) {
        rank = rank.trim();
        //System.out.println("Processing rank " + rank);
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
            case"10":
                return 10;
            case "Jack":
                return 10;
            case "Queen":
                return 10;
            case "King":
                return 10;
            case "Ace":
                return 11;

            default:
                throw new IllegalArgumentException("Invalid card rank"+ rank);

        }
    }

    //Getters
    public String getSuit() {
        return suit;
    }
    public String getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }

}
