package BlackJack;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

/***
 * Deck class
 * This class is used to represent a deck of cards
 * This class uses the singleton pattern
 */
public class Deck {
    private static Deck instance = null;
    private List<Card> cards;

    private Deck() {
        cards = new ArrayList<>();
        String [] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String [] values = {"2", "3", "4", "5", "6", "7", "8", "9", "Jack", "Queen", "King", "Ace"};
        for (String suit : suits) {
            for (String rank: values){
                cards.add(new Card(suit.trim(), rank.trim()));
            }
        }
        Collections.shuffle(cards);
    }

    public static Deck getInstance() {
        if (instance == null) {
            instance = new Deck();
        }
        return instance;
    }

    public Card draw() {
        if(cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        System.out.println("Cards left in the deck: " + cards.size());
        return cards.remove(cards.size() - 1);
    }

}
