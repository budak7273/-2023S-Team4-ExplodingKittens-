package system.UnitTesting;

import datasource.CardType;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import system.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DrawDeckUnitTesting {
    @Test
    public void testGetCards() {
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        assertTrue(deck.getCards().isEmpty());
    }

    @Test
    public void testDrawCardFromEmptyDrawDeck() {
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        Executable executable = () -> deck.drawCard(new User());
        Assertions.assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void testDrawCardFromNonEmptyDrawDeck() {
        User user = new User();

        DrawDeck deck = new DrawDeck(new ArrayList<>());
        deck.addCard(new Card(CardType.ATTACK));
        deck.drawCard(user);

        assertTrue(deck.getCards().isEmpty());
        assertTrue(!user.getHand().isEmpty());
    }

    @Test
    public void testDrawInitialCardFromEmptyDrawDeck() {
        User player = EasyMock.createMock(User.class);
        EasyMock.replay(player);

        DrawDeck deck = new DrawDeck(new ArrayList<>());
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
        player.addCard(drawnCard);
        EasyMock.replay(player, drawnCard, explodeCard);

        DrawDeck deck = new DrawDeck(new ArrayList<>());
        deck.addCard(explodeCard);
        deck.addCard(drawnCard);

        deck.drawInitialCard(player);
        Assertions.assertEquals(1, deck.getDeckSize());
        EasyMock.verify(player, drawnCard, explodeCard);
    }

    @Test
    public void testShuffleOnEmptyDeck() {
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        deck.shuffle();
        Assertions.assertEquals(0, deck.getDeckSize());
    }

    @Test
    public void testShuffleOnDeckOfOneCard() {
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        Card card = new Card(CardType.ATTACK);
        deck.addCard(card);
        deck.shuffle();

        Assertions.assertEquals(1, deck.getDeckSize());
        Assertions.assertEquals(card, deck.getCards().get(0));
    }

    @Test
    public void testShuffleOnDeckOfMultipleCards() {
        DrawDeck deck = new DrawDeck(new ArrayList<>());
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
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        User user = new User();

        Executable executable = () -> deck.drawFromBottomForUser(user);

        Assertions.assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void testDrawFromBottomForUserWithNonEmptyDeck() {
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        deck.addCard(new Card(CardType.ATTACK));
        Card bottomCard = new Card(CardType.ALTER_THE_FUTURE);
        deck.addCard(bottomCard);

        User user = EasyMock.createMock(User.class);
        user.addCard(bottomCard);
        EasyMock.expectLastCall();

        deck.drawFromBottomForUser(user);

        Assertions.assertFalse(deck.getCards().contains(bottomCard));
    }

    @Test
    public void testGetTopOfDeckWithEmptyDeck() {
        DrawDeck deck = new DrawDeck(new ArrayList<>());

        Executable executable = deck::getTopOfDeck;

        Assertions.assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void testGetTopOfDeckWithOneCard() {
        Card topCard = EasyMock.createMock(Card.class);
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        deck.addCard(topCard);
        EasyMock.replay(topCard);

        List<Card> future = deck.getTopOfDeck();

        Assertions.assertEquals(1, future.size());
        Assertions.assertEquals(topCard, future.get(0));
        Assertions.assertEquals(0, deck.getDeckSize());
    }

    @Test
    public void testGetTopOfDeckWithThreeCards() {
        Card first = EasyMock.createMock(Card.class);
        Card second = EasyMock.createMock(Card.class);
        Card third = EasyMock.createMock(Card.class);
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        deck.addCard(first);
        deck.addCard(second);
        deck.addCard(third);

        EasyMock.replay(first, second, third);

        List<Card> future = deck.getTopOfDeck();

        final int expectedFutureSize = 3;
        Assertions.assertEquals(expectedFutureSize, future.size());
        Assertions.assertEquals(first, future.get(0));
        Assertions.assertEquals(second, future.get(1));
        Assertions.assertEquals(third, future.get(2));
        Assertions.assertEquals(0, deck.getDeckSize());
    }

    @Test
    public void testGetTopOfDeckWithFourCards() {
        Card first = EasyMock.createMock(Card.class);
        Card second = EasyMock.createMock(Card.class);
        Card third = EasyMock.createMock(Card.class);
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        deck.addCard(first);
        deck.addCard(second);
        deck.addCard(third);
        deck.addCard(new Card(CardType.ATTACK));

        EasyMock.replay(first, second, third);

        List<Card> future = deck.getTopOfDeck();

        final int expectedFutureSize = 3;
        Assertions.assertEquals(expectedFutureSize, future.size());
        Assertions.assertEquals(first, future.get(0));
        Assertions.assertEquals(second, future.get(1));
        Assertions.assertEquals(third, future.get(2));
        Assertions.assertEquals(1, deck.getDeckSize());
    }

    @Test
    public void testPrependCard() {
        Card second = EasyMock.createMock(Card.class);
        Card first = EasyMock.createMock(Card.class);
        EasyMock.replay(second, first);

        DrawDeck deck = new DrawDeck(new ArrayList<>());

        deck.prependCard(second);
        deck.prependCard(first);

        List<Card> orderedCards = deck.getCards();

        Assertions.assertEquals(2, orderedCards.size());
        Assertions.assertEquals(first, orderedCards.get(0));
        Assertions.assertEquals(second, orderedCards.get(1));
    }
}
