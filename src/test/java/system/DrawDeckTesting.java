package system;

import datasource.CardType;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DrawDeckTesting {
    @Test
    public void testGetCards() {
        DrawDeck deck = new DrawDeck();
        assertTrue(deck.getCards().isEmpty());
    }

    @Test
    public void testDrawCardFromEmptyDrawDeck() {
        DrawDeck deck = new DrawDeck();
        Executable executable = () -> deck.drawCard(new User());
        Assertions.assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void testDrawCardFromNonEmptyDrawDeck() {
        User user = new User();

        DrawDeck deck = new DrawDeck();
        deck.addCard(new Card(CardType.ATTACK));
        deck.drawCard(user);

        assertTrue(deck.getCards().isEmpty());
        assertTrue(!user.getHand().isEmpty());
    }

    @Test
    public void testDrawInitialCardFromEmptyDrawDeck() {
        User player = EasyMock.createMock(User.class);
        EasyMock.replay(player);

        DrawDeck deck = new DrawDeck();
        Executable executable = () -> deck.drawInitialCard(player);

        Assertions.assertThrows(RuntimeException.class, executable);
        EasyMock.verify(player);
    }

    @Test
    public void testDrawInitialCardFromNonEmptyDrawDeck() {
        User player = EasyMock.createMock(User.class);
        Card drawnCard = EasyMock.createMockBuilder(Card.class)
                .withConstructor(CardType.class)
                .withArgs(CardType.ATTACK)
                .createMock();
        Card explodeCard = EasyMock.createMockBuilder(Card.class)
                .withConstructor(CardType.class)
                .withArgs(CardType.EXPLODING_KITTEN)
                .createMock();
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

    @Test
    public void testShuffleOnEmptyDeck() {
        DrawDeck deck = new DrawDeck();
        deck.shuffle();
        Assertions.assertEquals(0, deck.getDeckSize());
    }

    @Test
    public void testShuffleOnDeckOfOneCard() {
        DrawDeck deck = new DrawDeck();
        Card card = new Card(CardType.ATTACK);
        deck.addCard(card);
        deck.shuffle();

        Assertions.assertEquals(1, deck.getDeckSize());
        Assertions.assertEquals(card, deck.getCards().get(0));
    }

    @Test
    public void testShuffleOnDeckOfMultipleCards() {
        DrawDeck deck = new DrawDeck();
        Card card1 = new Card(CardType.ATTACK);
        Card card2 = new Card(CardType.ATTACK);
        deck.addCard(card1);
        deck.addCard(card2);
        deck.shuffle();

        Assertions.assertEquals(2, deck.getDeckSize());
        Assertions.assertTrue(deck.getCards().contains(card1));
        Assertions.assertTrue(deck.getCards().contains(card2));
    }

    @Test
    public void testDrawFromBottomForUserWithEmptyDeck() {
        DrawDeck deck = new DrawDeck();
        User user = new User();

        Executable executable = () -> deck.drawFromBottomForUser(user);

        Assertions.assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void testDrawFromBottomForUserWithNonEmptyDeck() {
        DrawDeck deck = new DrawDeck();
        deck.addCard(new Card(CardType.ATTACK));
        Card bottomCard = new Card(CardType.ATTACK);
        deck.addCard(bottomCard);

        User user = EasyMock.createMock(User.class);
        user.addCard(bottomCard);
        EasyMock.expectLastCall();
        EasyMock.replay();

        deck.drawFromBottomForUser(user);

        Assertions.assertFalse(deck.getCards().contains(bottomCard));

        EasyMock.verify();
    }
}
