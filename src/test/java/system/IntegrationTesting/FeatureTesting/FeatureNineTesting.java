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

public class FeatureNineTesting {

    @Test
    public void
    testDisplayingUsersInOrderTheyAreEnteredSmallGame() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("thisIsFirst", true, new ArrayList<>()));
        users.add(new User("thisIsSecond", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState();
        GamePlayer gamePlayer = gameDesigner.getGamePlayer();
        GameState gameState = gamePlayer.getGameState();
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "thisIsFirst");
        gameState.transitionToNextTurn();
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "thisIsSecond");
        gameState.transitionToNextTurn();
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "thisIsFirst");
    }

    @Test
    public void
    testDisplayingUsersInOrderTheyAreEnteredBigGame() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("thisIsFirst", true, new ArrayList<>()));
        users.add(new User("thisIsSecond", true, new ArrayList<>()));
        users.add(new User("thisIsThird", true, new ArrayList<>()));
        users.add(new User("thisIsFourth", true, new ArrayList<>()));
        users.add(new User("thisIsFifth", true, new ArrayList<>()));
        users.add(new User("thisIsSixth", true, new ArrayList<>()));
        users.add(new User("thisIsSeventh", true, new ArrayList<>()));
        users.add(new User("thisIsEighth", true, new ArrayList<>()));
        users.add(new User("thisIsNinth", true, new ArrayList<>()));
        users.add(new User("thisIsTenth", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState();
        GamePlayer gamePlayer = gameDesigner.getGamePlayer();
        GameState gameState = gamePlayer.getGameState();
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "thisIsFirst");
        gameState.transitionToNextTurn();
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "thisIsSecond");
        gameState.transitionToNextTurn();
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "thisIsThird");
        gameState.transitionToNextTurn();
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "thisIsFourth");
        gameState.transitionToNextTurn();
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "thisIsFifth");
        gameState.transitionToNextTurn();
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "thisIsSixth");
        gameState.transitionToNextTurn();
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "thisIsSeventh");
        gameState.transitionToNextTurn();
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "thisIsEighth");
        gameState.transitionToNextTurn();
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "thisIsNinth");
        gameState.transitionToNextTurn();
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "thisIsTenth");
        gameState.transitionToNextTurn();
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "thisIsFirst");
    }
}
