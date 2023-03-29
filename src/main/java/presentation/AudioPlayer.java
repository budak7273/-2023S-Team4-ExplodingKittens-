package presentation;

import datasource.Messages;
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
                File explosion = new File(pathname);
                FileInputStream fileInputStream =
                        new FileInputStream(explosion);
                BufferedInputStream bufferedInputStream =
                        new BufferedInputStream(fileInputStream);
                Player player = new Player(bufferedInputStream);
                player.play();
            } catch (JavaLayerException | IOException e) {
                System.err.println(Messages.getMessage(Messages.NO_MUSIC));
            }
        };
        Thread playThread = new Thread(runnablePlay);
        playThread.start();
    }

    public void playMusicOnStartup() {
        playMusic("src/main/resources/start.mp3");
    }

    public void playExplosion() {
        playMusic("src/main/resources/explodingKitten.mp3");
    }

    public void playDefused() {
        playMusic("src/main/resources/defused.mp3");
    }
}
