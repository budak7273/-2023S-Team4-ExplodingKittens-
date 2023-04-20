package system.IntegrationTesting.FeatureTesting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import presentation.GameDesigner;
import presentation.GameWindow;
import system.GameManager;
import system.TestingUtils;
import system.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class FeatureNineTesting {

    @Test
    void testDisplayingUsersInOrderTheyAreEnteredSmallGame() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("thisIsFirst", true, new ArrayList<>()));
        users.add(new User("thisIsSecond", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState(TestingUtils.getTestRandom());
        GameWindow gameWindow = gameDesigner.getGameWindow();
        GameManager gameManager = gameWindow.getGameManager();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                "thisIsFirst");
        gameManager.transitionToNextTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                "thisIsSecond");
        gameManager.transitionToNextTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                "thisIsFirst");
    }

    @Test
    void testDisplayingUsersInOrderTheyAreEnteredBigGame() {
        Queue<User> users = new LinkedList<>();

        for (int i = 1; i < 11; i++) {
            users.add(new User("User" + i, true, new ArrayList<>()));
        }

        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState(TestingUtils.getTestRandom());
        GameWindow gameWindow = gameDesigner.getGameWindow();
        GameManager gameManager = gameWindow.getGameManager();

        for (int j = 1; j < 11; j++) {
            Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                                    "User" + j);
            gameManager.transitionToNextTurn();
        }
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                                "User1");
    }
}
