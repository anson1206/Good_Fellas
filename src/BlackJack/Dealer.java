package BlackJack;
/***
 * Dealer class
 * This class is used to represent the dealer in the game
 * This class is  a reciever class in the command pattern
 */
import java.util.*;
public class Dealer implements Observer {
    private Command hitCommand;
    private Command standCommand;
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


    //dealer hits
    public void hit() {
        Card card = deck.draw();
        hand.add(card);
        subject.updateObserver("Dealer drew " + card.getRank() + " value: " + card.getValue() + ". Score: " + getScore());
        //System.out.println("Dealer drew " + card.getRank() + " value: " + card.getValue());
        //subject.updateObserver("Dealer "+ " scored " + getScore());
    }


    //checks if the dealer is busted
    public boolean isBusted() {
        if (score > 21) {
           // System.out.println("Dealer is busted");
            //subject.updateObserver("Dealer is busted");
            return true;
        } else {
            return false;
        }
    }

    //checks if the dealer has blackjack
    public boolean isBlackjack() {
        if (score == 21) {
           // System.out.println("Dealer has blackjack");
            //subject.updateObserver("Dealer has blackjack");
            return true;
        } else {
            return false;
        }
    }

    //returns the score of the dealer
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
       // System.out.println("Dealer has a score of " + score);
        return score;
    }


    public List<Card> getHand() {

        return hand;
    }

    //Helps initialize the hit command
    public void setHitCommand(Command hitCommand) {
        this.hitCommand = hitCommand;
    }

    //Helps initialize the stand command
    public void setStandCommand(Command standCommand) {
        this.standCommand = standCommand;
    }

    //updates the dealer
    @Override
    public void update(String message) {
        //System.out.println("Dealer received message: " + message);
    }

}