package system.IntegrationTesting.FeatureTesting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    JFrame frame;

    @BeforeEach
    void runAsHeadless() {
        System.setProperty("java.awt.headless", "true");
        frame = TestingUtils.getFakeFrame();
    }

    @Test
    void testDisplayingUsersInOrderTheyAreEnteredSmallGame() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("thisIsFirst", true, new ArrayList<>()));
        users.add(new User("thisIsSecond", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, frame);
        gameDesigner.initializeGameState(TestingUtils.getTestRandom());
        GameWindow gameWindow = gameDesigner.getGameWindow();
        GameManager gameManager = gameWindow.getGameManager();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(), "thisIsFirst");
        gameManager.transitionToNextTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(), "thisIsSecond");
        gameManager.transitionToNextTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(), "thisIsFirst");
    }

    @Test
    void testDisplayingUsersInOrderTheyAreEnteredBigGame() {
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
        GameDesigner gameDesigner = new GameDesigner(users, frame);
        gameDesigner.initializeGameState(TestingUtils.getTestRandom());
        GameWindow gameWindow = gameDesigner.getGameWindow();
        GameManager gameManager = gameWindow.getGameManager();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(), "thisIsFirst");
        gameManager.transitionToNextTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(), "thisIsSecond");
        gameManager.transitionToNextTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(), "thisIsThird");
        gameManager.transitionToNextTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(), "thisIsFourth");
        gameManager.transitionToNextTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(), "thisIsFifth");
        gameManager.transitionToNextTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(), "thisIsSixth");
        gameManager.transitionToNextTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(), "thisIsSeventh");
        gameManager.transitionToNextTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(), "thisIsEighth");
        gameManager.transitionToNextTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(), "thisIsNinth");
        gameManager.transitionToNextTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(), "thisIsTenth");
        gameManager.transitionToNextTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(), "thisIsFirst");
    }
}
