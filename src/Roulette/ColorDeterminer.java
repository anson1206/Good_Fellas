//Chase Wink
package Roulette;

/***
 * This class is used to determine the color of the number that is passed in for wheelSpin class
 * Basically what this class does is define the even numbers in an array and then loops over the array to see if the number
 * is in the array. If it is that means the color is red, so we return red if not then its black, and also if the number
 * is zero then its green. Takes in the parameter of number and returns the color of the number
 */
public class ColorDeterminer {
    public static String getColor(int number) {
        //If the number is zero then the color is green
        if (number == 0) {
            return "Green";
            //If the number is even then the color is red checking these numbers for even
        } else {
            int[] evenNumbers = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36};
            //looping over the array to see if the number is in the array
            for (int even : evenNumbers) {
                if (number == even) {
                    //if so return red
                    return "Red";
                }
            }
            //if not in array return black because it is odd
            return "Black";
        }
    }
}
