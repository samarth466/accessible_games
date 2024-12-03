package uno;

// Import java modules

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

// Class header
public class Audio {

    // Initialize instance variables
    private Clip clip;
    private FloatControl volumeControl;

    // Constructor
    public Audio(String filePath) {
        try {
            // Load the audio file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Get the volume control from the clip
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Play the audio
    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);   // Rewind to the beginning
            clip.start();
        }
    }

    // Play the audio for a set number of seconds
    public void playForDuration(int seconds) {
        if (clip != null) {
            clip.setFramePosition(0);   // Rewind to the beginning
            clip.start();

            // Schedule a task to stop the clip after a specified duration
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    clip.stop();
                }
            }, seconds);
        }
    }

    // Loop the clip
    public void loop(int count) {
        if (clip != null) {
            clip.loop(count);  // Loop the clip "count" times
        }
    }

    // Loop the clip undefinitely
    public void loopContinuously() {
        if (clip != null) {
            loop(clip.LOOP_CONTINUOUSLY);  // Loop the clip continuously
        }
    }

    // Adjusts the volume
    public void setVolume(float volume) {
        if (volumeControl != null) {
            // volume is a value between 0.0 (silent) and 1.0 (maximum)
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float newValue = min+(max-min)*volume;
            volumeControl.setValue(newValue);
        }
    }

}
