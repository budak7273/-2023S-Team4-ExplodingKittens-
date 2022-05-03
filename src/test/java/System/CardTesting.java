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
}
