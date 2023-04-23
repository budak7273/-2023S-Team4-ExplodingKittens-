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
    private JFrame frame;
    private final String[] names = {"thisIsFirst", "thisIsSecond", "thisIsThird", "thisIsFourth", "thisIsFifth",
            "thisIsSixth",
            "thisIsSeventh", "thisIsEighth", "thisIsNinth", "thisIsTenth"};

    @BeforeEach
    void runAsHeadless() {
        System.setProperty("java.awt.headless", "true");
        frame = TestingUtils.getFakeFrame();
    }

    @Test
    void testDisplayingUsersInOrderTheyAreEnteredSmallGame() {
        Queue<User> users = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            users.add(new User(names[i], true, new ArrayList<>()));
        }
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
        final int playerCount = 10;
        Queue<User> users = new LinkedList<>();
        for (int i = 0; i < playerCount; i++) {
            users.add(new User(names[i], true, new ArrayList<>()));
        }
        GameDesigner gameDesigner = new GameDesigner(users, frame);
        gameDesigner.initializeGameState(TestingUtils.getTestRandom());
        GameWindow gameWindow = gameDesigner.getGameWindow();
        GameManager gameManager = gameWindow.getGameManager();
        for (int i = 0; i < playerCount; i++) {
            Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(), names[i]);
            gameManager.transitionToNextTurn();
        }
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(), names[0]);
    }
}
