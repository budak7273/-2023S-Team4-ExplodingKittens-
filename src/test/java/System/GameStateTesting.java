package System;

import Presentation.Gameboard;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

    @Test
    public void testDistributeCards1User() {
        Queue<User> users = new LinkedList<>();
        users.add(new User());

        GameState gameState = new GameState(users, new Gameboard());
        Executable executable = () -> gameState.dealHands(new DrawDeck());
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testDistributeCards2Users() {
        User player1 = EasyMock.createMock(User.class);
        User player2 = EasyMock.createMock(User.class);
        DrawDeck setDeck = EasyMock.createMock(DrawDeck.class);
        for (int i=0; i<7; i++) {
            setDeck.drawInitialCard(player1);
            setDeck.drawInitialCard(player2);
        }
        EasyMock.replay(player1, player2, setDeck);

        Queue<User> users = new LinkedList<>();
        users.add(player1);
        users.add(player2);

        GameState gameState = new GameState(users, new Gameboard());
        gameState.dealHands(setDeck);

        EasyMock.verify(player1, player2, setDeck);
    }

    @Test
    public void testDistributeCards10Users() {
        List<User> players = new ArrayList<User>();
        for (int i=0; i<10; i++) {
            players.add(EasyMock.createMock(User.class));
        }
        DrawDeck setDeck = EasyMock.createMock(DrawDeck.class);
        for (int i=0; i<7; i++) {
            players.forEach(setDeck::drawInitialCard);
        }
        EasyMock.replay(players.get(0), players.get(1), players.get(2),
                players.get(3), players.get(4), players.get(5),
                players.get(6), players.get(7), players.get(8),
                players.get(9), setDeck);

        Queue<User> users = new LinkedList<>(players);

        GameState gameState = new GameState(users, new Gameboard());
        gameState.dealHands(setDeck);

        EasyMock.verify(players.get(0), players.get(1), players.get(2),
                players.get(3), players.get(4), players.get(5),
                players.get(6), players.get(7), players.get(8),
                players.get(9), setDeck);
    }
}
