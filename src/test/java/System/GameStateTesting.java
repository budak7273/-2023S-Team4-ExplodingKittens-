package System;

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
        GameState gameState = new GameState(playerQueue);
        Executable executable = () -> gameState.transitionToNextTurn();
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testTransitionToNextTurn_withQueueOf2Users() {
        Queue<User> playerQueue = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        User userNextInQueue = new User();
        playerQueue.add(userStartingAtTopOfQueue);
        playerQueue.add(userNextInQueue);

        GameState gameState = new GameState(playerQueue);
        gameState.transitionToNextTurn();
        Assertions.assertEquals(userNextInQueue, gameState.getUserForCurrentTurn());
    }

    @Test
    public void testTransitionToNextTurn_withQueueOf10Users() {
        Queue<User> playerQueue = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        User userNextInQueue = new User();
        playerQueue.add(userStartingAtTopOfQueue);
        playerQueue.add(userNextInQueue);
        for (int i = 0; i < 8; i++) {
            playerQueue.add(new User());
        }

        GameState gameState = new GameState(playerQueue);
        gameState.transitionToNextTurn();
        Assertions.assertEquals(userNextInQueue, gameState.getUserForCurrentTurn());
    }

    @Test
    public void testTransitionToNextTurn_withQueueOf11Users() {
        Queue<User> playerQueue = new LinkedList<User>();
        for (int i = 0; i < 11; i++) {
            playerQueue.add(new User());
        }
        GameState gameState = new GameState(playerQueue);
        Executable executable = () -> gameState.transitionToNextTurn();
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }
}
