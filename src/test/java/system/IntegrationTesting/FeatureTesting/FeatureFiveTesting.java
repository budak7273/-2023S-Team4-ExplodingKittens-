package system.IntegrationTesting.FeatureTesting;

import datasource.CardType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import presentation.GameDesigner;
import presentation.GamePlayer;
import system.Card;
import system.GameManager;
import system.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FeatureFiveTesting {

    @Test
    public void testGameWith10Players() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        users.add(new User("test3", true, new ArrayList<>()));
        users.add(new User("test4", true, new ArrayList<>()));
        users.add(new User("test5", true, new ArrayList<>()));
        users.add(new User("test6", true, new ArrayList<>()));
        users.add(new User("test7", true, new ArrayList<>()));
        users.add(new User("test8", true, new ArrayList<>()));
        users.add(new User("test9", true, new ArrayList<>()));
        users.add(new User("test10", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState();
        GamePlayer gamePlayer = gameDesigner.getGamePlayer();
        GameManager gameManager = gamePlayer.getGameManager();
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
    public void testEndGameStartingAt10Players() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        users.add(new User("test3", true, new ArrayList<>()));
        users.add(new User("test4", true, new ArrayList<>()));
        users.add(new User("test5", true, new ArrayList<>()));
        users.add(new User("test6", true, new ArrayList<>()));
        users.add(new User("test7", true, new ArrayList<>()));
        users.add(new User("test8", true, new ArrayList<>()));
        users.add(new User("test9", true, new ArrayList<>()));
        users.add(new User("test10", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState();
        GamePlayer gamePlayer = gameDesigner.getGamePlayer();
        GameManager gameManager = gamePlayer.getGameManager();
        gameManager.transitionToNextTurn();
        final int startingPoint = 2;
        final int endingPoint = 11;
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
