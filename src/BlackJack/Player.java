package BlackJack;

import java.util.*;

/***
 * Player class
 * This class is used to represent the player
 * This class implements the Observer interface
 * This class also works with the Command pattern
 * This class acts like a receiver in the command pattern
 */
public class Player implements Observer, CardReceiver {
    private String name;
    private int score;
    private List<Card> hand;
    private Command hitCommand;
    private Command standCommand;
    Deck deck = Deck.getInstance();
    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.hand = new ArrayList<>();
        this.hitCommand = new PlayerHitCommand(this,deck );
        this.standCommand = new PlayerStandCommand(this);
    }

    public String getName() {
        return name;
    }

    //returns the score of the player
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
        System.out.println(name + " has a score of " + score);
        return score;
    }


    public List<Card> getHand() {

        return hand;
    }
    @Override
    public void hit() {
        hitCommand.execute();
       // System.out.println(name + " drew " + card.getRank() + " value: " + card.getValue());
        getScore();
    }

    @Override
    public void stand() {
        standCommand.execute();
    }

    public boolean isBusted() {
        if(score > 21) {
            System.out.println(name + " is busted");
            return true;
        }else{
            return false;
        }
    }

    public boolean isBlackjack() {
        if(score == 21) {
            System.out.println(name + " has blackjack");
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void update(String message) {
        System.out.println(name + " received message: " + message);
    }

    public void setHitCommand(Command hitCommand) {
        this.hitCommand = hitCommand;
    }
    public void setStandCommand(Command standCommand) {
        this.standCommand = standCommand;
    }
}
