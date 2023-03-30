package system.UnitTesting;

import datasource.CardType;
import datasource.Messages;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import system.Card;
import system.DrawDeck;
import system.GameManager;
import system.GameState;
import system.User;

public class CardTesting {
    @Test
    public void testEqualsDifferentClass() {
        Card card = new Card(CardType.ATTACK);
        Object otherObj = 1;
        Assertions.assertFalse(card.equals(otherObj));

        String expectedName = Messages.getMessage("AttackCard");
        String expectedDesc = Messages.getMessage("AttackDesc");
        Assertions.assertEquals(expectedName, card.getName());
        Assertions.assertEquals(expectedDesc, card.getDesc());
    }

    @Test
    public void testEqualsDifferentType() {
        Card card = new Card(CardType.ATTACK);
        Object otherObj = new Card(CardType.ALTER_THE_FUTURE);
        Assertions.assertFalse(card.equals(otherObj));
    }

    @Test
    public void testEqualsSameTypeDifferentInstance() {
        Card card = new Card(CardType.ATTACK);
        Object otherObj = new Card(CardType.ATTACK);
        Assertions.assertTrue(card.equals(otherObj));
    }

    @Test
    public void testEqualsSameInstance() {
        Card card = new Card(CardType.ATTACK);
        Assertions.assertTrue(card.equals(card));
    }

    @Test
    public void testHashCodeSameTypeDifferentInstance() {
        Card card1 = new Card(CardType.ATTACK);
        Card card2 = new Card(CardType.ATTACK);
        Assertions.assertEquals(card1.hashCode(), card2.hashCode());
    }

    @Test
    public void testHashCodeSameInstance() {
        Card card = new Card(CardType.ATTACK);
        Assertions.assertEquals(card.hashCode(), card.hashCode());
    }

    @Test
    public void testHashCodeDifferentType() {
        Card card1 = new Card(CardType.ATTACK);
        for (CardType type : CardType.values()) {
            if (type == CardType.ATTACK) {
                continue;
            }
            Card card2 = new Card(type);
            Assertions.assertNotEquals(card1.hashCode(), card2.hashCode());
        }
    }

    @Test
    public void testActivateEffectOnAttackCard() {
        GameManager gameManager = EasyMock.createMock(GameManager.class);
        GameState gameState = EasyMock.createMock(GameState.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);
        User user = EasyMock.createMock(User.class);
        Card cardToRemove = new Card(CardType.ATTACK);

        gameManager.removeCardFromCurrentUser(cardToRemove);
        EasyMock.expectLastCall();
        EasyMock.expect(gameState.getDrawDeck()).andReturn(drawDeck);
        EasyMock.expectLastCall();
        EasyMock.expect(gameManager.getGameState()).andReturn(gameState);
        gameState.addExtraTurn();
        gameManager.transitionToNextTurn();
        EasyMock.expectLastCall();
        EasyMock.expect(gameState.getUserForCurrentTurn()).andReturn(user);
        EasyMock.replay(gameManager, gameState, user, drawDeck);

        cardToRemove.activateEffect(gameManager);

        EasyMock.verify(gameManager, gameState, user, drawDeck);
    }

}
