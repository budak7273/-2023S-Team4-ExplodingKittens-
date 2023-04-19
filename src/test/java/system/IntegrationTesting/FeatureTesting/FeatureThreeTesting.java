package system.IntegrationTesting.FeatureTesting;

import datasource.CardType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presentation.GameDesigner;
import presentation.GameWindow;
import system.Card;
import system.DrawDeck;
import system.GameManager;
import system.TestingUtils;
import system.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class FeatureThreeTesting {

    private GameManager gameManager;

    @BeforeEach
    void runAsHeadless() {
        System.setProperty("java.awt.headless", "true");
    }

    void setUpInternals(Queue<User> users) {
        GameDesigner gameDesigner = new GameDesigner(users, TestingUtils.getFakeFrame());
        gameDesigner.initializeGameState(TestingUtils.getTestRandom());
        GameWindow gameWindow = gameDesigner.getGameWindow();
        gameManager = gameWindow.getGameManager();
    }

    @Test
    void testDetectWhenPlayerDies() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        users.add(new User("test3", true, new ArrayList<>()));
        setUpInternals(users);

        DrawDeck drawDeck = gameManager.getDrawDeck();
        drawDeck.addCardToTop(new Card(CardType.EXPLODING_KITTEN));
        User currentUser = gameManager.getUserForCurrentTurn();
        currentUser.removeCard(new Card(CardType.DEFUSE));
        gameManager.drawCardForCurrentTurn();
        Assertions.assertEquals(2, gameManager.getPlayerQueue().size());
        Assertions.assertFalse(
                gameManager.getPlayerQueue().contains(currentUser));
    }

    @Test
    void testWhenMultiplePlayersInGameDie() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        users.add(new User("test3", true, new ArrayList<>()));
        users.add(new User("test4", true, new ArrayList<>()));
        users.add(new User("test5", true, new ArrayList<>()));
        users.add(new User("test6", true, new ArrayList<>()));
        users.add(new User("test7", true, new ArrayList<>()));
        users.add(new User("test8", true, new ArrayList<>()));
        setUpInternals(users);

        DrawDeck drawDeck = gameManager.getDrawDeck();
        gameManager.transitionToNextTurn();
        gameManager.transitionToNextTurn();
        final int startingPoint = 3;
        final int endingPoint = 9;
        for (int i = startingPoint; i < endingPoint; i++) {
            drawDeck.addCardToTop(new Card(CardType.EXPLODING_KITTEN));
            User currentUser = gameManager.getUserForCurrentTurn();
            currentUser.removeCard(new Card(CardType.DEFUSE));
            currentUser.removeCard(new Card(CardType.DEFUSE));
            gameManager.drawCardForCurrentTurn();
            Assertions.assertFalse(
                    gameManager.getPlayerQueue().contains(currentUser));
        }
        Assertions.assertEquals(2, gameManager.getPlayerQueue().size());
    }
}
