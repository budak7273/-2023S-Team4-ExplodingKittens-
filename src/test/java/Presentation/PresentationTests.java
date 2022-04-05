package Presentation;

import System.*;
import org.easymock.EasyMock;
import org.easymock.IMockBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

import static org.easymock.EasyMock.createMock;
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
    public void createGame2PlayerTest() throws InvalidPlayerCountException {
        Gameboard gameboard = new Gameboard();
        Integer twoPlayers = 2;
        List<String> userNameList = new ArrayList<>();
        Queue<User> playerQueue = new ArrayDeque<>();
        userNameList.add("player1");
        userNameList.add("player2");
        Gameboard mockedGameboard = EasyMock.createMockBuilder(Gameboard.class).
                addMockedMethod("readUserInfo").createMock();

        EasyMock.expect(mockedGameboard.readUserInfo()).andReturn(userNameList);
        Setup mockedSetup = EasyMock.createMock(Setup.class);
        User mockedUser1 = EasyMock.createMock(User.class);
        User mockedUser2 = EasyMock.createMock(User.class);
        playerQueue.add(mockedUser1);
        playerQueue.add(mockedUser2);
        EasyMock.expect(mockedSetup.createUsers(userNameList)).andReturn(playerQueue);

        DrawDeck mockedDrawDeck = EasyMock.createMock(DrawDeck.class);
        DiscardDeck mockedDiscardDeck = EasyMock.createMock(DiscardDeck.class);
        File mockedFile = EasyMock.createMock(File.class);
        EasyMock.expect(mockedSetup.createDrawDeck(mockedFile)).andReturn(mockedDrawDeck);
        EasyMock.expect(mockedSetup.createDiscardDeck()).andReturn(mockedDiscardDeck);

        EasyMock.replay(mockedGameboard);
        EasyMock.replay((mockedFile));
        EasyMock.replay(mockedUser1);
        EasyMock.replay(mockedUser2);
        EasyMock.replay(mockedDrawDeck);
        EasyMock.replay(mockedDiscardDeck);
        EasyMock.replay(mockedSetup);

        gameboard.createGame(twoPlayers);
        assertEquals(playerQueue, gameboard.getUsers());
        assertEquals(mockedDrawDeck, gameboard.getDrawDeck());
        assertEquals(mockedDiscardDeck, gameboard.getDiscardDeck());
        EasyMock.verify();

    }


}
