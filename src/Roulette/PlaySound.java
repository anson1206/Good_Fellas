//Chase Wink
package Roulette;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/***
 * This class is used to play a sound file that is passed in as a parameter
 * It takes in the file path of the sound file and plays it. I used this class in my GUI when I call this
 * class to make the wheelspin sound effect when the wheel spins for the GUI. I had to look up this method
 * as well on YouTube since I didn't know how to play a sound file in Java. The video that I used was this link:
 * https://youtu.be/wJO_cq5XeSA?si=hDgAtWwd2Q42DJVm
 */
public class PlaySound {
    //Taking in the file path of the sound file to play the sound
    public void playSound(String filePath) {
        try {
            //Setting the audio file to the file path that is passed in
            File audioFile = new File(filePath);
            //Getting the audio input stream from the audio file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            //Opening the audio clip and starting the audio clip
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            //Catching the exceptions that are thrown for handling audio files
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
