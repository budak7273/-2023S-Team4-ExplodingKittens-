package presentation;

import datasource.I18n;
import datasource.ResourceHelper;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;

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
                BufferedInputStream bufferedInputStream =
                        new BufferedInputStream(ResourceHelper.getAsStream(pathname));
                Player player = new Player(bufferedInputStream);
                player.play();
            } catch (JavaLayerException e) {
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
