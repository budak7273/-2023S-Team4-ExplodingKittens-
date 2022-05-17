package system.UnitTesting;

import datasource.CardType;
import datasource.Messages;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import system.Card;
import system.GameState;
import system.User;
import system.cardEffects.EffectPattern;

import java.util.ArrayList;

public class CardTesting {
    @Test
    public void testEqualsDifferentClass() {
        Card card = new Card(CardType.ATTACK);
        Object otherObj = 1;
        Assertions.assertFalse(card.equals(otherObj));

        String expectedName = Messages.getMessage(Messages.ATTACK_CARD);
        String expectedDesc = Messages.getMessage(Messages.ATTACK_DESC);
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
    public void testActivateEffectOnAttackCard(){
        GameState gameState = EasyMock.createMock(GameState.class);
        Card cardToRemove = new Card(CardType.ATTACK);

        gameState.removeCardFromCurrentUser(cardToRemove);
        EasyMock.expectLastCall();
        gameState.transitionToNextTurn();
        EasyMock.expectLastCall();
        gameState.addExtraTurn();
        EasyMock.expectLastCall();
        EasyMock.replay(gameState);

        cardToRemove.activateEffect(gameState);

        EasyMock.verify(gameState);
    }

}
