package system.IntegrationTesting.FeatureTesting;

import datasource.CardType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presentation.GameDesigner;
import presentation.GameWindow;
import system.Card;
import system.GameManager;
import system.TestingUtils;
import system.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class FeatureSevenTesting {

    private User currentUser;
    private GameManager gameManager;

    @BeforeEach
    void setUp() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState(TestingUtils.getTestRandom());
        GameWindow gameWindow = gameDesigner.getGameWindow();
        gameManager = gameWindow.getGameManager();
        currentUser = gameManager.getUserForCurrentTurn();
    }

    @Test
    void testUserDrawsACardToEndTurn() {
        int currentHandSize = currentUser.getHand().size();
        int currentDeckSize = gameManager.getDeckSizeForCurrentTurn();
        Assertions.assertEquals(currentUser.getName(),
                "test1");
        gameManager.drawCardForCurrentTurn();
        gameManager.transitionToNextTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                "test2");
        Assertions.assertEquals(currentUser.getHand().size(),
                currentHandSize + 1);
        Assertions.assertEquals(gameManager.getDeckSizeForCurrentTurn(),
                currentDeckSize - 1);
    }

    @Test
    void testUserPlaysSkipCard() {
        currentUser.addCard(new Card(CardType.SKIP));
        int currentHandSize = currentUser.getHand().size();
        int currentDeckSize = gameManager.getDeckSizeForCurrentTurn();
        Assertions.assertEquals(currentUser.getName(),
                "test1");
        currentUser.getHand().get(currentHandSize - 1)
                .activateEffect(gameManager);
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                "test2");
        Assertions.assertEquals(currentUser.getHand().size(),
                currentHandSize - 1);
        Assertions.assertEquals(gameManager.getDeckSizeForCurrentTurn(),
                currentDeckSize);
    }

    @Test
    void testUserPlaysAttackCardWithNoRetaliation() {
        currentUser.addCard(new Card(CardType.ATTACK));
        int currentHandSize = currentUser.getHand().size();
        int currentDeckSize = gameManager.getDeckSizeForCurrentTurn();
        Assertions.assertEquals(currentUser.getName(),
                "test1");
        currentUser.getHand().get(currentHandSize - 1)
                .activateEffect(gameManager);
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                "test2");
        Assertions.assertEquals(currentUser.getHand().size(),
                currentHandSize - 1);
        Assertions.assertEquals(gameManager.getDeckSizeForCurrentTurn(),
                currentDeckSize);
    }
}
