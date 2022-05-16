package presentation;

import datasource.Messages;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AudioPlayer {
    public void playMusicOnStartup() {
        Runnable runnablePlay = new Runnable() {
            @Override
            public void run() {
                try{
                    File explosion = new File("src/main/resources/explosion.mp3");
                    FileInputStream fileInputStream = new FileInputStream(explosion);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
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
