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

public class FeatureThreeTesting {


    @Test
    public void testDetectWhenPlayerDies() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        users.add(new User("test3", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState();
        GamePlayer gamePlayer = gameDesigner.getGamePlayer();
        GameState gameState = gamePlayer.getGameState();
        DrawDeck drawDeck = gameState.getDrawDeck();
        drawDeck.addCardToTop(new Card(CardType.EXPLODING_KITTEN));
        User currentUser = gameState.getUserForCurrentTurn();
        currentUser.removeCard(new Card(CardType.DEFUSE));
        gameState.drawCardForCurrentTurn();
        Assertions.assertEquals(2, gameState.getPlayerQueue().size());
        Assertions.assertFalse(gameState.getPlayerQueue().contains(currentUser));
    }

    @Test
    public void testWhenMultiplePlayersInGameDie() {
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
        gameState.transitionToNextTurn();
        gameState.transitionToNextTurn();
        for (int i = 3; i < 9; i++) {
            drawDeck.addCardToTop(new Card(CardType.EXPLODING_KITTEN));
            User currentUser = gameState.getUserForCurrentTurn();
            currentUser.removeCard(new Card(CardType.DEFUSE));
            currentUser.removeCard(new Card(CardType.DEFUSE));
            gameState.drawCardForCurrentTurn();
            Assertions.assertFalse(gameState.getPlayerQueue().contains(currentUser));
        }
        Assertions.assertEquals(2, gameState.getPlayerQueue().size());
    }
}
