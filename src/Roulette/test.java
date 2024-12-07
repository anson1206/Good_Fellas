package Roulette;
import javax.swing.*;

public class test {
     public static void main(String[] args) {
        // Create a JFrame
        JFrame frame = new JFrame("GIF Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);

        // Load the GIF image
        ImageIcon gifIcon = new ImageIcon("src/Roulette/RouletteGIF.gif");
        // Add the GIF to a JLabel
        JLabel gifLabel = new JLabel(gifIcon);

        // Add the JLabel to the JFrame
        frame.add(gifLabel);

        // Make the frame visible
        frame.setVisible(true);
    }
}