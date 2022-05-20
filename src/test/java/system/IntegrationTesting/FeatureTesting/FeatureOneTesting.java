package system.IntegrationTesting.FeatureTesting;

import datasource.CardType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import presentation.GameDesigner;
import presentation.GamePlayer;
import system.Card;
import system.DrawDeck;
import system.GameState;
import system.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FeatureOneTesting {

    @Test
    public void testDetectWhenPlayerWinsGame() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState();
        GamePlayer gamePlayer = gameDesigner.getGamePlayer();
        GameState gameState = gamePlayer.getGameState();
        DrawDeck drawDeck = gameState.getDrawDeck();
        drawDeck.addCardToTop(new Card(CardType.EXPLODING_KITTEN));
        User currentUser = gameState.getUserForCurrentTurn();
        currentUser.removeCard(new Card(CardType.DEFUSE));
        while (gameState.getPlayerQueue().size() > 1) {
            gameState.drawCardForCurrentTurn();
        }
        Assertions.assertEquals(gameState.getPlayerQueue().size(), 1);
        Assertions.assertTrue(gameState.tryToEndGame());
    }

    @Test
    public void testWhenMultiplePlayersInGameAndOneWins() {
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
        DrawDeck drawDeck = gameState.getDrawDeck();
        while (gameState.getPlayerQueue().size() > 1) {
            drawDeck.addCardToTop(new Card(CardType.EXPLODING_KITTEN));
            User currentUser = gameState.getUserForCurrentTurn();
            currentUser.removeCard(new Card(CardType.DEFUSE));
            gameState.drawCardForCurrentTurn();
        }
        Assertions.assertEquals(gameState.getPlayerQueue().size(), 1);
        Assertions.assertTrue(gameState.tryToEndGame());
    }


}
