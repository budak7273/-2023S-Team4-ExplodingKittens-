package Presentation;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PresentationTests {
    @Test
    public void createGame1PlayerTest()  {
        Gameboard gameboard = new Gameboard();
        Integer onePlayer = 1;
        Throwable exception = assertThrows(InvalidPlayerCountException.class,  () -> gameboard.createGame(onePlayer));
        assertEquals("ERROR: Must have at least 2 players!", exception.getMessage());
    }
    @Test
    public void createGame2PlayerTest(){
//        Gameboard gameboard = new Gameboard();
//        Integer twoPlayers = 2;
//        EasyMock mockedDrawDeck = EasyMock.createMock(DrawDeck.class);
//        EasyMock mockedDiscardDeck = EasyMock.createMock(DiscardDeck.class);
//        EasyMock mockedUser = EasyMock.createMock(User.class);
//
//        EasyMock.expect(mockedDrawDeckdrawDeck.size());
//        EasyMock.expect(mockedDiscardDeckdiscardDeck.size());
//
//
//        assertEquals(0, gameboard.playerQueue.size());
//        assertEquals(0, gameboard.drawDeck.size());
//
//        EasyMock.expect(mockedUser.getHandSize());
//
//        assertEquals(0, gameboard.discardDeck.size());
//        try {
//            gameboard.createGame(twoPlayers);
//        } catch (InvalidPlayerCountException e) {
//            Assertions.fail();
//        }
//        assertEquals(0, gameboard.playerQueue.size());
//        assertEquals(0, gameboard.drawDeck.size());
//        assertEquals(0, gameboard.discardDeck.size());
            fail("Not yet implemented");
    }


}
