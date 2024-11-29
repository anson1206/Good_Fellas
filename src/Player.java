
import java.util.*;

/***
 * Player class
 * This class is used to represent the player
 * This class implements the Observer interface
 * This class also works with the Command pattern
 * This class acts like a receiver in the command pattern
 */
public class Player implements Observer{
    private String name;
    private int score;
    private List<Card> hand;
    private Command hitCommand;
    private Command standCommand;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void hit(Card card) {
        hitCommand.execute();
    }

    private void updateScore() {
        score = 0;
        for (Card card : hand) {
            score += card.getValue();

        }
    }

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
}
