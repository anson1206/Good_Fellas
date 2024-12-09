//Chase Wink
package Roulette;

import java.util.Random;

/*This class sole function is to spin the Wheel and get a number between 0-36 */
public class wheelSpin {
    public static int spinWheel() {
        Random random = new Random();
        return random.nextInt(37);
    }
}
