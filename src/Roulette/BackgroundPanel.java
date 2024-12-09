//Chase Wink

package Roulette;
import javax.swing.*;
import java.awt.*;
/**
 * Custom JPanel class to display a background image. I had to use this so that I can update the jTextArea.
 * I used this so that I can paint the background on that panel, because when I was trying .setBackground() it was not working
 * for images. This led me down a rabbit hole that I eventually solved with a YouTube video. The video that
 * I used was this link: https://youtu.be/ToW9kVvnvo4?si=juL8AEmEP9x0GMUp
 * Made this class so that I can call it in my GUI class
 */
public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    //Taking in the file path as a string to set the background image
    public BackgroundPanel(String filePath) {
        //Setting the background image to the image that is passed in
        backgroundImage = new ImageIcon(filePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Using the background image to paint the background of the panel
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
