package BlackJack;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;

/***
 * Card class
 * This class is used to represent a card
 * This class is used to represent a card in a deck of cards
 * This class is apart of the singleton pattern through association with the Deck class
 * Anson Graumann
 */
public class Card {
    private String suit;
    private String rank;
    private int value;

    //Constructor
    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
        this.value = getValue(rank);
    }

    //Returns the value of the card
    public int getValue(String rank) {
        rank = rank.trim();
        switch (rank) {
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
            case "8":
                return 8;
            case "9":
                return 9;
            case "10":
                return 10;
            case "Jack":
                return 10;
            case "Queen":
                return 10;
            case "King":
                return 10;
            case "Ace":
                return 11;

            default:
                throw new IllegalArgumentException("Invalid card rank" + rank);

        }
    }

    //Getters


    public String getRank() {
        return rank;
    }

    public int getValue() {
        return value;
    }

    //card image getter
    //gets the rank and suit of the card and returns the image of the card
    public ImageIcon getCardImage() {
        String cardImage = rank + suit.charAt(0) + ".png";
        File file = new File("src/BlackJack/Cards/" + cardImage);
       //checks if the file exists and if it does it returns the image of the card or throws an exception
        if(file.exists()){
            try {
                BufferedImage image = ImageIO.read(file);
                int width = 100;
                int height = 150;
                Image resizedImage = image.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
                return new ImageIcon(resizedImage);
            }catch(Exception e){
                System.out.println("Card image found: " + file.getAbsolutePath());
               throw new IllegalArgumentException("Invalid card image " + cardImage);
            }

        }else{
            System.err.println("Card image not found: " + file.getAbsolutePath());
            throw new IllegalArgumentException(";Invalid card image " + cardImage);
        }
    }
}