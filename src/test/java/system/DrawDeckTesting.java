package system;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import system.cards.AttackCard;
import system.cards.ExplodingCard;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DrawDeckTesting {
    @Test
    public void testGetCards() {
        ArrayList<Card> cards = new ArrayList<>();

        DrawDeck deck = new DrawDeck(cards);
        assertTrue(deck.getCards().isEmpty());
    }

    @Test
    public void testDrawCardFromEmptyDrawDeck() {
        ArrayList<Card> cards = new ArrayList<>();
        DrawDeck deck = new DrawDeck(cards);
        Executable executable = () -> deck.drawCard(new User());
        Assertions.assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void testDrawCardFromNonEmptyDrawDeck() {
        User user = new User();
        ArrayList<Card> cards = new ArrayList<>();

        DrawDeck deck = new DrawDeck(cards);
        deck.addCard(new AttackCard());
        deck.drawCard(user);

        assertTrue(deck.getCards().isEmpty());
        assertTrue(!user.getHand().isEmpty());
    }

    @Test
    public void testDrawInitialCardFromEmptyDrawDeck() {
        User player = EasyMock.createMock(User.class);
        EasyMock.replay(player);
        ArrayList<Card> cards = new ArrayList<>();

        DrawDeck deck = new DrawDeck(cards);
        Executable executable = () -> deck.drawInitialCard(player);

        Assertions.assertThrows(RuntimeException.class, executable);
        EasyMock.verify(player);
    }

    @Test
    public void testDrawInitialCardFromNonEmptyDrawDeck() {
        User player = EasyMock.createMock(User.class);
        Card drawnCard = EasyMock.createMock(AttackCard.class);
        Card explodeCard = EasyMock.createMock(ExplodingCard.class);
        EasyMock.expect(explodeCard.getName()).andReturn("Exploding Kitten");
        EasyMock.expect(drawnCard.getName()).andReturn("Attack");
        player.addCard(drawnCard);
        EasyMock.replay(player, drawnCard, explodeCard);
        ArrayList<Card> cards = new ArrayList<>();

        DrawDeck deck = new DrawDeck(cards);
        deck.addCard(explodeCard);
        deck.addCard(drawnCard);

        deck.drawInitialCard(player);
        Assertions.assertEquals(1, deck.getDeckSize());
        EasyMock.verify(player, drawnCard, explodeCard);
    }

    @Test
    public void testShuffleOnEmptyDeck() {
        ArrayList<Card> cards = new ArrayList<>();
        DrawDeck deck = new DrawDeck(cards);
        deck.shuffle();
        Assertions.assertEquals(0, deck.getDeckSize());
    }

    @Test
    public void testShuffleOnDeckOfOneCard() {
        ArrayList<Card> cards = new ArrayList<>();
        DrawDeck deck = new DrawDeck(cards);
        Card card = new AttackCard();
        deck.addCard(card);
        deck.shuffle();

        Assertions.assertEquals(1, deck.getDeckSize());
        Assertions.assertEquals(card, deck.getCards().get(0));
    }

    @Test
    public void testShuffleOnDeckOfMultipleCards() {
        ArrayList<Card> cards = new ArrayList<>();
        DrawDeck deck = new DrawDeck(cards);
        Card card1 = new AttackCard();
        Card card2 = new AttackCard();
        deck.addCard(card1);
        deck.addCard(card2);
        deck.shuffle();

        Assertions.assertEquals(2, deck.getDeckSize());
        Assertions.assertTrue(deck.getCards().contains(card1));
        Assertions.assertTrue(deck.getCards().contains(card2));
    }

    @Test
    public void testDrawFromBottomForUserWithEmptyDeck() {
        ArrayList<Card> cards = new ArrayList<>();
        DrawDeck deck = new DrawDeck(cards);
        User user = new User();

        Executable executable = () -> deck.drawFromBottomForUser(user);

        Assertions.assertThrows(RuntimeException.class, executable);
    }

    @Test
    public void testDrawFromBottomForUserWithNonEmptyDeck() {
        ArrayList<Card> cards = new ArrayList<>();
        DrawDeck deck = new DrawDeck(cards);
        deck.addCard(new AttackCard());
        Card bottomCard = new AttackCard();
        deck.addCard(bottomCard);

        User user = EasyMock.createMock(User.class);
        user.addCard(bottomCard);
        EasyMock.expectLastCall();
        EasyMock.replay(user);

        deck.drawFromBottomForUser(user);

        Assertions.assertFalse(deck.getCards().contains(bottomCard));

        EasyMock.verify(user);
    }
}
