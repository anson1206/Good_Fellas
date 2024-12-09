//Chase Wink
package RussainRoulette;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
//Same use as the Roulette version look at that for more information
public class PlaySound {
    public void playSound(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
