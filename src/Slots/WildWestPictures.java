package Slots;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class WildWestPictures {

    private static final Map<String, String> imagePaths = new HashMap<>();

    static {
        imagePaths.put("Horse", "src/Slots/Images/horse.png");
        imagePaths.put("Sheriff", "src/Slots/Images/sheriff.png");
        imagePaths.put("Bandit", "src/Slots/Images/bandit.png");
    }

    public static ImageIcon getImageIcon(String symbol) {
        String imagePath = imagePaths.get(symbol);
        if (imagePath != null) {
            try {
                File file = new File(imagePath);
                BufferedImage image = ImageIO.read(file);
                Image resizedImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                return new ImageIcon(resizedImage);
            } catch (Exception e) {
                System.err.println("Error loading image: " + imagePath);
                return null;
            }
        } else {
            System.err.println("No image found for symbol: " + symbol);
            return null;
        }
    }
}
