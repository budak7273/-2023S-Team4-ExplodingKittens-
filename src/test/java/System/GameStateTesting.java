package System;

import Presentation.Gameboard;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.LinkedList;
import java.util.Queue;

public class GameStateTesting {
    @Test
    public void testTransitionToNextTurn_withQueueOf1User() {
        Queue<User> playerQueue = new LinkedList<User>();
        playerQueue.add(new User());
        GameState gameState = new GameState(playerQueue, new Gameboard());
        Executable executable = () -> gameState.transitionToNextTurn();
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testTransitionToNextTurn_withQueueOf2Users() {
        Gameboard boardMock = EasyMock.createMock(Gameboard.class);
        boardMock.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> playerQueue = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        User userNextInQueue = new User();
        playerQueue.add(userStartingAtTopOfQueue);
        playerQueue.add(userNextInQueue);

        GameState gameState = new GameState(playerQueue, boardMock);
        gameState.transitionToNextTurn();
        Assertions.assertEquals(userNextInQueue, gameState.getUserForCurrentTurn());

        EasyMock.verify(boardMock);
    }

    @Test
    public void testTransitionToNextTurn_withQueueOf10Users() {
        Gameboard boardMock = EasyMock.createMock(Gameboard.class);
        boardMock.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> playerQueue = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        User userNextInQueue = new User();
        playerQueue.add(userStartingAtTopOfQueue);
        playerQueue.add(userNextInQueue);
        for (int i = 0; i < 8; i++) {
            playerQueue.add(new User());
        }

        GameState gameState = new GameState(playerQueue, boardMock);
        gameState.transitionToNextTurn();
        Assertions.assertEquals(userNextInQueue, gameState.getUserForCurrentTurn());

        EasyMock.verify(boardMock);
    }

    @Test
    public void testTransitionToNextTurn_withQueueOf11Users() {
        Queue<User> playerQueue = new LinkedList<User>();
        for (int i = 0; i < 11; i++) {
            playerQueue.add(new User());
        }
        GameState gameState = new GameState(playerQueue, new Gameboard());
        Executable executable = () -> gameState.transitionToNextTurn();
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }
}
