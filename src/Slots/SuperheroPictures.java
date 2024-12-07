package Slots;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SuperheroPictures {

    private static final Map<String, String> imagePaths = new HashMap<>();

    static {
        imagePaths.put("Batman", "src/Slots/Images/batman.png");
        imagePaths.put("Superman", "src/Slots/Images/superman.png");
        imagePaths.put("Iron Man", "src/Slots/Images/ironman3.png");
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