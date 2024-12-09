//Chase Wink
package Roulette;

/* This is where I implement the template pattern like how we did for HW2. In here I created protected
* abstract methods that the class implementing this class must use. Then I created a method playGame that
* defines the skeleton of how the Roulette Game should be played. It goes through and calls different methods
* to complete the functionality of the Roulette game*/
public abstract class RouletteGameTemplate {
    //This is the method that defines the skeleton of how the Roulette game should be played
    public final void playGame() {
        //Getting the bet amount from the player
        int betAmount = getBetAmount();
        //If the bet amount is greater than 0 then we can continue with the game
        if (betAmount > 0) {
            //Getting the bet type from the player
            int betType = getBetType();
            //If placeBet returns true then we can continue with the game
            if (placeBet(betType, betAmount, "")) {
                //Determining if the player wins
                boolean playerWins = determineWin(betType);
                //Updating the chips of the player
                updateChips(betAmount, playerWins, betType);

            }
        }
    }

    protected abstract int getBetAmount();
    protected abstract int getBetType();
    protected abstract boolean placeBet(int betType, int betAmount, String betDetails);
    protected abstract boolean determineWin(int betType);
    protected abstract void updateChips(int betAmount, boolean playerWins, int betType);

}