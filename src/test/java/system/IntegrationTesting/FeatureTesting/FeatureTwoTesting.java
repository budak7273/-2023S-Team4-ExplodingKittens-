package system.IntegrationTesting.FeatureTesting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import presentation.GameDesigner;
import presentation.GamePlayer;
import system.GameState;
import system.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FeatureTwoTesting {

    @Test
    public void testDisplayingCurrentPlayersName() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState();
        GamePlayer gamePlayer = gameDesigner.getGamePlayer();
        GameState gameState = gamePlayer.getGameState();
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "test1");
        gameState.transitionToNextTurn();
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "test2");
        gameState.transitionToNextTurn();
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "test1");
    }

    @Test
    public void testDisplayingCurrentPlayersNameWithLargePlayerCount() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        users.add(new User("test3", true, new ArrayList<>()));
        users.add(new User("test4", true, new ArrayList<>()));
        users.add(new User("test5", true, new ArrayList<>()));
        users.add(new User("test6", true, new ArrayList<>()));
        users.add(new User("test7", true, new ArrayList<>()));
        users.add(new User("test8", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState();
        GamePlayer gamePlayer = gameDesigner.getGamePlayer();
        GameState gameState = gamePlayer.getGameState();
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "test1");
        for (int i = 2; i < gameState.getPlayerQueue().size() + 1; i++) {
            gameState.transitionToNextTurn();
            Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                    "test" + (i % (gameState.getPlayerQueue().size() + 1)));
        }
        gameState.transitionToNextTurn();
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "test1");
    }
}
