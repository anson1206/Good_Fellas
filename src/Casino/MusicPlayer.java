//Chase Wink
package Casino;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/* This class serves as a way to play the background music for the entire application. I used the same
* method and code from my Roulette game, but instead I made it where the clip runs continuously so when that
* file is over with, it just restarts. This is the source I used https://youtu.be/E1q8dZkiCgo?si=CH0rMi7m56m17IEu */
public class MusicPlayer {

    public void playBackGroundMusic(String filePath) {
        try {
            //Same as roulette file sound effect
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();

            // Setting the volume
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-10.0f); // Reduce volume by 10 decibels
            //throws the 3 exceptions for the audio file
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}