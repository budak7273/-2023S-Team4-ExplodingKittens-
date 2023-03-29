package system.IntegrationTesting.FeatureTesting;

import datasource.CardType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import presentation.GameDesigner;
import presentation.GamePlayer;
import system.Card;
import system.DrawDeck;
import system.GameManager;
import system.TestingUtils;
import system.User;
import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class FeatureOneTesting {

    @Test
    void testDetectWhenPlayerWinsGame() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState(TestingUtils.getTestRandom());
        GamePlayer gamePlayer = gameDesigner.getGamePlayer();
        GameManager gameManager = gamePlayer.getGameManager();
        DrawDeck drawDeck = gameManager.getDrawDeck();
        drawDeck.addCardToTop(new Card(CardType.EXPLODING_KITTEN));
        User currentUser = gameManager.getUserForCurrentTurn();
        currentUser.removeCard(new Card(CardType.DEFUSE));
        currentUser.removeCard(new Card(CardType.DEFUSE));
        currentUser.removeCard(new Card(CardType.DEFUSE));
        while (gameManager.getPlayerQueue().size() > 1) {
            gameManager.drawCardForCurrentTurn();
        }
        Assertions.assertEquals(gameManager.getPlayerQueue().size(), 1);
        Assertions.assertTrue(gameManager.tryToEndGame());
    }

    @Test
    void testWhenMultiplePlayersInGameAndOneWins() {
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
        gameDesigner.initializeGameState(TestingUtils.getTestRandom());
        GamePlayer gamePlayer = gameDesigner.getGamePlayer();
        GameManager gameManager = gamePlayer.getGameManager();
        DrawDeck drawDeck = gameManager.getDrawDeck();
        while (gameManager.getPlayerQueue().size() > 1) {
            drawDeck.addCardToTop(new Card(CardType.EXPLODING_KITTEN));
            User currentUser = gameManager.getUserForCurrentTurn();
            currentUser.removeCard(new Card(CardType.DEFUSE));
            currentUser.removeCard(new Card(CardType.DEFUSE));
            currentUser.removeCard(new Card(CardType.DEFUSE));
            gameManager.drawCardForCurrentTurn();
        }
        Assertions.assertEquals(gameManager.getPlayerQueue().size(), 1);
        Assertions.assertTrue(gameManager.tryToEndGame());
    }


}
