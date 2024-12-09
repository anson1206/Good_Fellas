//Chase Wink
package RussainRoulette;

import java.util.Random;
//Same use as the Roulette version look at that for more information
public class wheelSpin {
    public static int spinWheel() {
        Random random = new Random();
        return random.nextInt(37);
    }
}
