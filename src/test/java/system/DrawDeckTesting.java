package system;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.EmptyStackException;

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
        Assertions.assertTrue(!user.getHand().isEmpty());
    }

    @Test
    public void testDrawInitialCard_fromEmptyDrawDeck() {
        User player = EasyMock.createMock(User.class);
        EasyMock.replay(player);

        DrawDeck deck = new DrawDeck();
        Executable executable = () -> deck.drawInitialCard(player);

        Assertions.assertThrows(RuntimeException.class, executable);
        EasyMock.verify(player);
    }

    @Test
    public void testDrawInitialCard_fromNonEmptyDrawDeck() {
        User player = EasyMock.createMock(User.class);
        Card drawnCard = EasyMock.createMock(AttackCard.class);
        Card explodeCard = EasyMock.createMock(ExplodingCard.class);
        EasyMock.expect(explodeCard.getName()).andReturn("Exploding Kitten");
        EasyMock.expect(drawnCard.getName()).andReturn("Attack");
        player.addCard(drawnCard);
        EasyMock.replay(player, drawnCard, explodeCard);

        DrawDeck deck = new DrawDeck();
        deck.addCard(explodeCard);
        deck.addCard(drawnCard);

        deck.drawInitialCard(player);
        Assertions.assertEquals(1, deck.getDeckSize());
        EasyMock.verify(player, drawnCard, explodeCard);
    }
}
