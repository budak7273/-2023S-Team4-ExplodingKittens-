package presentation;

import datasource.Messages;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AudioPlayer {

    private AudioPlayer() { }

    public static void playMusicOnStartup() {
        Runnable runnablePlay = new Runnable() {
            @Override
            public void run() {
                try {
                    File explosion = new File("src/main/resources/start.mp3");
                    FileInputStream fileInputStream =
                            new FileInputStream(explosion);
                    BufferedInputStream bufferedInputStream =
                            new BufferedInputStream(fileInputStream);
                    Player player = new Player(bufferedInputStream);
                    player.play();
                } catch (JavaLayerException | IOException e) {
                    System.err.println(Messages.getMessage(Messages.NO_MUSIC));
                }
            }
        };
        Thread playThread = new Thread(runnablePlay);
        playThread.start();
    }

    public static void playExplosion() {
        Runnable runnablePlay = new Runnable() {
        @Override
        public void run() {
            try {
                File f = new File("src/main/resources/explodingKitten.mp3");
                FileInputStream fileInputStream =
                        new FileInputStream(f);
                BufferedInputStream bufferedInputStream =
                        new BufferedInputStream(fileInputStream);
                Player player = new Player(bufferedInputStream);
                player.play();
            } catch (JavaLayerException | IOException e) {
                System.err.println(Messages.getMessage(Messages.NO_MUSIC));
            }
        }
        };
        Thread playThread = new Thread(runnablePlay);
        playThread.start();
    }

    public static void playDefused() {
        Runnable runnablePlay = new Runnable() {
            @Override
            public void run() {
                try {
                    File f = new File("src/main/resources/defused.mp3");
                    FileInputStream fileInputStream =
                            new FileInputStream(f);
                    BufferedInputStream bufferedInputStream =
                            new BufferedInputStream(fileInputStream);
                    Player player = new Player(bufferedInputStream);
                    player.play();
                } catch (JavaLayerException | IOException e) {
                    System.err.println(Messages.getMessage(Messages.NO_MUSIC));
                }
            }
        };
        Thread playThread = new Thread(runnablePlay);
        playThread.start();
    }
}
