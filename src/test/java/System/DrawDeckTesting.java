package System;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class DrawDeckTesting {

    @Test
    public void testDrawCard_fromEmptyDrawDeck() {
        User userMock = EasyMock.createMock(User.class);

        DrawDeck deck = new DrawDeck();
        Executable executable = () -> deck.drawCard(userMock);
        Assertions.assertThrows(RuntimeException.class, executable);
    }
}
