package Roulette1;


import java.util.Random;

public class wheelSpin {
    public static int spinWheel() {
        Random random = new Random();
        return random.nextInt(37);
    }
    public static void main(String[] args) {
        System.out.println(spinWheel());
    }
}