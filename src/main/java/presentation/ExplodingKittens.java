package presentation;


import com.sun.org.glassfish.gmbal.GmbalException;
import datasource.Messages;

import javax.swing.*;

public final class ExplodingKittens {
    private ExplodingKittens() { }
    public static void main(final String[] args) {
        JFrame gameFrame = new JFrame();
        GamePlayer gp = new GamePlayer(gameFrame);
        GameDesigner gd = new GameDesigner(gp);
//        Gameboard g = new Gameboard();
        try {
            gd.createGame();
        } catch (InvalidPlayerCountException e) {
            System.out.println(e.getMessage());
        }
    }
}
