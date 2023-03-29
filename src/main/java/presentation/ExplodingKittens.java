package presentation;

import javax.swing.*;

public final class ExplodingKittens {
    private ExplodingKittens() { }
    public static void main(final String[] args) {
        GameDesigner gd = new GameDesigner(new JFrame());
        AudioPlayer.enableSound = true;
        try {
            gd.createGame();
        } catch (InvalidPlayerCountException e) {
            System.out.println(e.getMessage());
        }
    }
}
