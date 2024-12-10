package Slots;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class PlaySound {
    // Plays a sound from a given file path
    public void playSound(String filePath) {
        try {
            File audioFile = new File(filePath); // Locate the sound file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile); // Open an audio stream
            Clip clip = AudioSystem.getClip(); // Create a clip to play the audio
            clip.open(audioStream); // Open the audio stream in the clip
            clip.start(); // Start playing the audio
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace(); // Print any errors that occur
        }
    }
}
