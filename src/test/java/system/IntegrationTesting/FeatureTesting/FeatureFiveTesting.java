package system.IntegrationTesting.FeatureTesting;

import datasource.CardType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presentation.GameDesigner;
import presentation.GamePlayer;
import system.Card;
import system.GameManager;
import system.TestingUtils;
import system.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class FeatureFiveTesting {

    private GameManager gameManager;

    public static final int NUM_PLAYERS = 10;

    @BeforeEach
    void setUp() {
        Queue<User> users = new LinkedList<>();
        for (int i = 1; i <= NUM_PLAYERS; i++) {
            users.add(new User("test" + i, true, new ArrayList<>()));
        }
        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState(new Random(TestingUtils.TESTS_RANDOM_SEED));
        GamePlayer gamePlayer = gameDesigner.getGamePlayer();
        gameManager = gamePlayer.getGameManager();
    }

    @Test
    void testGameWith10Players() {

        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                "test1");
        for (int i = 2; i < gameManager.getPlayerQueue().size() + 1; i++) {
            gameManager.transitionToNextTurn();
            Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                    "test" + (i % (gameManager.getPlayerQueue().size() + 1)));
        }
        gameManager.transitionToNextTurn();
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                "test1");
    }

    @Test
    void testEndGameStartingAt10Players() {
        gameManager.transitionToNextTurn();
        final int startingPoint = 2;
        final int endingPoint = NUM_PLAYERS + 1;
        for (int i = startingPoint; i < endingPoint; i++) {
            gameManager.addCardToDeck(new Card(CardType.EXPLODING_KITTEN));
            User currentUser = gameManager.getUserForCurrentTurn();
            gameManager.removeCardFromCurrentUser(new Card(CardType.DEFUSE));
            gameManager.removeCardFromCurrentUser(new Card(CardType.DEFUSE));
            gameManager.drawCardForCurrentTurn();
            Assertions.assertFalse(
                    gameManager.getPlayerQueue().contains(currentUser));
        }
        Assertions.assertEquals(1, gameManager.getPlayerQueue().size());
        Assertions.assertTrue(gameManager.tryToEndGame());
    }
}
