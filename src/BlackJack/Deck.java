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

    //creates a deck of cards
    private Deck() {
        cards = new ArrayList<>();
        //makes it easier to create the deck of cards when the deck runs out of cards
        createDeck();
        }

    //returns the instance of the deck
    public static Deck getInstance() {
        if (instance == null) {
            instance = new Deck();
        }
        return instance;
    }

    //creates the deck of cards
    public void createDeck(){
    String [] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
    String [] values = {"2", "3", "4", "5", "6", "7", "8", "9","10", "Jack", "Queen", "King", "Ace"};
    //reads each suit and value and creates a card
        for (String suit : suits) {
        for (String rank: values){
            //trims the spaces and adds the card to the deck
            cards.add(new Card(suit.trim(), rank.trim()));
        }
    }
    //shuffles the deck
        Collections.shuffle(cards);

    }

    //draws a card from the deck
    public Card draw() {
       //allows the deck of cards to never be empty
        if(cards.isEmpty()) {
            createDeck();
            //throw new IllegalStateException("Deck is empty");
        }
        System.out.println("Cards left in the deck: " + (cards.size()-1));
        return cards.remove(cards.size() - 1);
    }

}
