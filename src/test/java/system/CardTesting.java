package system;

import datasource.CardType;
import datasource.Messages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CardTesting {
    @Test
    public void testEqualsDifferentClass() {
        Card card = new Card(CardType.ATTACK, Messages.ATTACK_DESC);
        Object otherObj = 1;
        Assertions.assertFalse(card.equals(otherObj));
    }

    @Test
    public void testEqualsDifferentType() {
        Card card = new Card(CardType.ATTACK, Messages.ATTACK_DESC);
        Object otherObj = new Card(CardType.ALTER_THE_FUTURE, Messages.ALTER_DESC);
        Assertions.assertFalse(card.equals(otherObj));
    }

    @Test
    public void testEqualsSameTypeDifferentInstance() {
        Card card = new Card(CardType.ATTACK, Messages.ATTACK_DESC);
        Object otherObj = new Card(CardType.ATTACK, Messages.ALTER_DESC);
        Assertions.assertTrue(card.equals(otherObj));
    }

    @Test
    public void testEqualsSameInstance() {
        Card card = new Card(CardType.ATTACK, Messages.ATTACK_DESC);
        Assertions.assertTrue(card.equals(card));
    }

    @Test
    public void testHashCodeSameTypeDifferentInstance() {
        Card card1 = new Card(CardType.ATTACK, Messages.ATTACK_DESC);
        Card card2 = new Card(CardType.ATTACK, Messages.ATTACK_DESC);
        Assertions.assertEquals(card1.hashCode(), card2.hashCode());
    }

    @Test
    public void testHashCodeSameInstance() {
        Card card = new Card(CardType.ATTACK, Messages.ATTACK_DESC);
        Assertions.assertEquals(card.hashCode(), card.hashCode());
    }

    @Test
    public void testHashCodeDifferentType() {
        Card card1 = new Card(CardType.ATTACK, Messages.ATTACK_DESC);
        for (CardType type : CardType.values()) {
            if (type == CardType.ATTACK) {
                continue;
            }
            Card card2 = new Card(type, CardType.getDescription(type));
            Assertions.assertNotEquals(card1.hashCode(), card2.hashCode());
        }
    }
}
