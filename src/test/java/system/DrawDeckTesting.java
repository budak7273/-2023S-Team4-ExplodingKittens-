package system;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DrawDeckTesting {
    @Test
    public void testGetCards(){
        DrawDeck deck = new DrawDeck();
        assertTrue(deck.getCards().isEmpty());
    }
    @Test
    public void testDrawCard_fromEmptyDrawDeck() {
        DrawDeck deck = new DrawDeck();
        Executable executable = () -> deck.drawCard(new User());
        Assertions.assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void testDrawCard_fromNonEmptyDrawDeck() {
        User user = new User();

        DrawDeck deck = new DrawDeck();
        deck.addCard(new AttackCard());
        deck.drawCard(user);

        assertTrue(deck.getCards().isEmpty());
        assertTrue(!user.hand.isEmpty());
    }

}
