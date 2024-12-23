package BlackJack;

import java.util.*;

/***
 * Player class
 * This class is used to represent the player
 * This class implements the Observer interface
 * This class also works with the Command pattern
 * This class is a receiver in the command pattern
 * Anson Graumann
 */
public class Player implements Observer {
    private int score;
    private List<Card> hand;
    private Command hitCommand;
    private Command standCommand;
    Deck deck = Deck.getInstance();
    private Subject subject;

    //constructor
    public Player(Deck deck, Subject subject) {
        this.subject = subject;
        this.score = 0;
        this.hand = new ArrayList<>();
        this.deck = deck;
        this.subject.add(this);
    }

    //player hits and gets the score
    public void hit() {
        Card card = deck.draw();
        hand.add(card);
        subject.updateObserver("Player drew " + card.getRank() + " value: " + card.getValue() + ". Score: " + getScore());
        getScore();
    }

    //player stands and gets the score
    public void stand() {
        System.out.println("Player stands");
         getScore();
    }

    //checks if the player is busted
    public boolean isBusted() {
        if(score > 21) {
            return true;
        }else{
            return false;
        }
    }

    //checks if the player has blackjack
    public boolean isBlackjack() {
        if(score == 21) {
            return true;
        }else{
            return false;
        }
    }

    //getters and setters

    public List<Card> getHand() {
        return hand;
    }
    //returns the score of the player
    //handles aces as 1 or 11
    public int getScore() {
        score = 0;
        int aces = 0;
        for(Card card : hand){
            score += card.getValue(card.getRank());
            if(card.getRank().equals("Ace")){
                aces++;
            }
        }
        while (score > 21 && aces > 0){
            score -= 10;
            aces--;
        }
        return score;
    }

    //sets the commands
    public void setHitCommand(Command hitCommand) {
        this.hitCommand = hitCommand;
    }
    public void setStandCommand(Command standCommand) {
        this.standCommand = standCommand;
    }


    //updates the observer
    @Override
    public void update(String message) {
        System.out.println("Player received message: " + message);
    }

}



