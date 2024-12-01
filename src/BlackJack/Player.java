package BlackJack;

import java.util.*;

/***
 * Player class
 * This class is used to represent the player
 * This class implements the Observer interface
 * This class also works with the Command pattern
 * This class is a receiver in the command pattern
 */
public class Player{
    private String name;
    private int score;
    private List<Card> hand;
    private Command hitCommand;
    private Command standCommand;
    Deck deck = Deck.getInstance();


    //constructor
    public Player(String name, Deck deck) {
        this.name = name;
        this.score = 0;
        this.hand = new ArrayList<>();
        this.deck = deck;
    }
    //player hits and gets the score
    public void hit() {
        Card card = deck.draw();
        hand.add(card);
        System.out.println(name+ " drew " + card.getRank() + " value: " + card.getValue());
        getScore();
    }

    //player stands and gets the score
    public void stand() {
        System.out.println(name + " stands");
        getScore();
    }

    //checks if the player is busted
    public boolean isBusted() {
        if(score > 21) {
            System.out.println(name + " is busted");
            return true;
        }else{
            return false;
        }
    }

    //checks if the player has blackjack
    public boolean isBlackjack() {
        if(score == 21) {
            System.out.println(name + " has blackjack");
            return true;
        }else{
            return false;
        }
    }

    //getters and setters
    public String getName() {
        return name;
    }
    public List<Card> getHand() {
        return hand;
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

    //sets the commands
    public void setHitCommand(Command hitCommand) {
        this.hitCommand = hitCommand;
    }
    public void setStandCommand(Command standCommand) {
        this.standCommand = standCommand;
    }
}

/*
    @Override
    public void update(String message) {
        System.out.println(name + " received message: " + message);
    }
*/

