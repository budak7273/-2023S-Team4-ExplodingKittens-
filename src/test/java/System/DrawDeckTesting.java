package System;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class DrawDeckTesting {

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

        Assertions.assertTrue(deck.cards.isEmpty());
        Assertions.assertTrue(!user.hand.isEmpty());
    }

    @Test
    public void testDrawInitialCard_fromEmptyDrawDeck() {
        DrawDeck deck = new DrawDeck();
        Executable executable = deck::drawInitialCard;
        Assertions.assertThrows(RuntimeException.class, executable);
    }
}
