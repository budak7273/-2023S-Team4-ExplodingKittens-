package presentation;


import java.util.Locale;
import datasource.Messages;

public final class ExplodingKittens {
    private ExplodingKittens() { }
    public static void main(final String[] args) {
        Gameboard g = new Gameboard();
        try {
            g.createGame();
        } catch (InvalidPlayerCountException e) {
            String key = e.getMessage();
            String invalidPlayerMessage = Messages.getMessage(key);
            System.out.println(invalidPlayerMessage);
        }
    }
}
