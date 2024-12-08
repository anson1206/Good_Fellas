package Roulette;

public class ColorDeterminer {
    public static String getColor(int number) {
        if (number == 0) {
            return "Green";
        } else {
            int[] evenNumbers = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36};
            for (int even : evenNumbers) {
                if (number == even) {
                    return "Red";
                }
            }
            return "Black";
        }
    }
}
