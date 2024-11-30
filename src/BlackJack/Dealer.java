package BlackJack;
/***
 * Dealer class
 * THis class acts like the invoker in the command pattern
 *
 */
import java.util.*;
public class Dealer implements CardReceiver {
    private Command hitCommand;
    private Command standCommand;
    private Deck deck;
    private Player player;
    private List<Card> hand;

    int score = 0;


    public Dealer(Deck deck, Player player) {
        this.deck = deck;
        this.hand = new ArrayList<>();
        this.score = 0;
        this.player = player;
    }

    //checks to see if player either stands, hasnt busted, and hasnt gotten blackjack, then the dealer deals
    // a card
    @Override
    public void hit() {
        hitCommand.execute();
        getScore();
    }


    //returns the score of the dealer
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
        System.out.println( "Dealer has a score of " + score);

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
     @Override
    public void stand() {
        standCommand.execute();
    }

     public boolean isBusted() {
         if(score > 21) {
             System.out.println("Dealer is busted");
             return true;
         }else{
             return false;
         }
     }
}