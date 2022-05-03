package system;

import datasource.CardType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CardTesting {
    @Test
    public void testEqualsDifferentClass() {
        Card card = new Card(CardType.ATTACK);
        Object otherObj = 1;
        Assertions.assertFalse(card.equals(otherObj));
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
}
