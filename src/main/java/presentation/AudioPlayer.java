package presentation;

import datasource.I18n;
import datasource.ResourceHelper;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AudioPlayer {
    private final boolean enableSound;

    public AudioPlayer(boolean inputEnableSound) {
        this.enableSound = inputEnableSound;
    }

    private void playMusic(String pathname) {
        if (!enableSound) {
            return;
        }
        Runnable runnablePlay = () -> {
            try {
                File explosion = ResourceHelper.getAsFile(pathname);
                FileInputStream fileInputStream =
                        new FileInputStream(explosion);
                BufferedInputStream bufferedInputStream =
                        new BufferedInputStream(fileInputStream);
                Player player = new Player(bufferedInputStream);
                player.play();
            } catch (JavaLayerException | IOException e) {
                System.err.println(I18n.getMessage("CouldntPlayMusic"));
            }
        };
        Thread playThread = new Thread(runnablePlay);
        playThread.start();
    }

    public void playMusicOnStartup() {
        playMusic("/audio/start.mp3");
    }

    public void playExplosion() {
        playMusic("/audio/explodingKitten.mp3");
    }

    public void playDefused() {
        playMusic("/audio/defused.mp3");
    }
}
