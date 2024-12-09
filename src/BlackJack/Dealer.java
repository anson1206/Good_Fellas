package BlackJack;
/***
 * Dealer class
 * This class is used to represent the dealer in the game
 * This class is  a reciever class in the command pattern
 * This class also implements the observer pattern
 * Anson Graumann
 */
import java.util.*;
public class Dealer implements Observer {
    private Command hitCommand;
    private Deck deck = Deck.getInstance();
    private Player player;
    private List<Card> hand;
    private Subject subject;
    int score = 0;

    //constructor
    public Dealer(Deck deck, Player player, Subject subject) {
        this.deck = deck;
        this.subject = subject;
        this.hand = new ArrayList<>();
        this.score = 0;
        this.player = player;
        this.subject.add(this);
    }


    //dealer hit method to add a card to the dealer's hand
    public void hit() {
        Card card = deck.draw();
        hand.add(card);
        subject.updateObserver("Dealer drew " + card.getRank() + " value: " + card.getValue() + ". Score: " + getScore());
        System.out.println("Dealer drew " + card.getRank() + " value: " + card.getValue());
    }


    //checks if the dealer is busted
    public boolean isBusted() {
        if (score > 21) {
            return true;
        } else {
            return false;
        }
    }

    //checks if the dealer has blackjack
    public boolean isBlackjack() {
        if (score == 21) {
            return true;
        } else {
            return false;
        }
    }

    //returns the score of the dealer
    //handles aces as 1 or 11
    public int getScore() {
        score = 0;
        int aces = 0;
        for (Card card : hand) {
            score += card.getValue(card.getRank());
            if (card.getRank().equals("Ace")) {
                aces++;
            }
        }
        while (score > 21 && aces > 0) {
            score -= 10;
            aces--;
        }
        return score;
    }

    //gets the hand of the dealer
    public List<Card> getHand() {
        return hand;
    }

    //Helps initialize the hit command
    public void setHitCommand(Command hitCommand) {
        this.hitCommand = hitCommand;
    }


    //updates the dealer
    @Override
    public void update(String message) {
        System.out.println("Dealer received message: " + message);
    }

}