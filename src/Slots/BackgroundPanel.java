package Slots;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    // Constructor Loads the background image from the file path
    public BackgroundPanel(String imagePath) {
        try {
            // Load the image and store it in backgroundImage
            backgroundImage = new ImageIcon(imagePath).getImage();
        } catch (Exception e) {
            // Print an error message if the image cannot be loaded
            System.err.println("Error loading background image: " + e.getMessage());
        }
    }

    // Custom paint method to draw the background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Ensure other components are painted first
        if (backgroundImage != null) {
            // Draw the background image to fill the entire panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
