package RussainRoulette;

import java.util.Random;

public class wheelSpin {
    public static int spinWheel() {
        Random random = new Random();
        return random.nextInt(37);
    }
}
