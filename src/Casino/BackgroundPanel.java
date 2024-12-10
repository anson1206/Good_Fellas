//Chase Wink and John
package Casino;

import javax.swing.*;
import java.awt.*;

/* Using the same format and code as I used on BackgroundPanel in Roulette Package. Look at that file to see
* the resource I used to implement this*/
public class BackgroundPanel extends JPanel {
    //Setting the background image to a private variable
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = new ImageIcon(imagePath).getImage();
        } catch (Exception e) {
            System.out.println("Background image not found: " + imagePath);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
