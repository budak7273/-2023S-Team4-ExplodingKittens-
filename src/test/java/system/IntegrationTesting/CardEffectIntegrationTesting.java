package system.IntegrationTesting;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

import presentation.GameDesigner;
import presentation.GamePlayer;
import system.cardEffects.*;
import system.User;
import system.DrawDeck;
import system.GameState;

import javax.swing.*;


public class CardEffectIntegrationTesting {

    private Queue<User> playerQueue;

    private List<String> playerUsernames;

    public CardEffectIntegrationTesting() {
        System.setProperty("java.awt.headless", "false");
        playerQueue = new ArrayDeque<User>();
        User player1 = new User("Player1ForIntegrationTest");
        User player2 = new User("Player2ForIntegrationTest");
        User player3 = new User("Player3ForIntegrationTest");
        User player4 = new User("Player4ForIntegrationTest");
        User player5 = new User("Player5ForIntegrationTest");
        playerQueue.add(player1);
        playerQueue.add(player2);
        playerQueue.add(player3);
        playerQueue.add(player4);
        playerQueue.add(player5);
        playerUsernames = new ArrayList<>();
        playerUsernames.add("Player1ForIntegrationTest");
        playerUsernames.add("Player2ForIntegrationTest");
        playerUsernames.add("Player3ForIntegrationTest");
        playerUsernames.add("Player4ForIntegrationTest");
        playerUsernames.add("Player5ForIntegrationTest");
    }

    @Test
    public void testDefuseBombEffectUseIntegrationTest() {
        EffectPattern bombEffectPattern = new DefuseBombEffect();
        GamePlayer gameBoard = new GamePlayer(new JFrame());
        DrawDeck drawDeck = new DrawDeck(new ArrayList<>());

        GameState gameState = new GameState(playerQueue, gameBoard, drawDeck);

        Executable executable = () -> bombEffectPattern.useEffect(gameState);
        Assertions.assertDoesNotThrow(executable);

    }

    @Test
    public void testAttackEffectUseIntegrationTest() {
        EffectPattern bombEffectPattern = new AttackEffect();
        GamePlayer gamePlayer = new GamePlayer(new JFrame());
        DrawDeck drawDeck = new DrawDeck(new ArrayList<>());

        GameState gameState = new GameState(playerQueue, gamePlayer, drawDeck);
        gamePlayer.setGameState(gameState);

        Executable executable = () -> bombEffectPattern.useEffect(gameState);
        Assertions.assertDoesNotThrow(executable);

    }

    @Test
    public void testDrawFromBottomIntegrationTest() {
        EffectPattern drawFromBottomEffect = new DrawFromBottomEffect();
        GameDesigner gameDesigner = new GameDesigner(new JFrame());

        gameDesigner.initializeGameState(playerUsernames);
        GamePlayer gameBoard = gameDesigner.getGamePlayer();

        GameState gameState = gameBoard.getGameState();
        int beforeCount = gameState.getDeckSizeForCurrentTurn();
        gameState.drawFromBottom();
        Assertions.assertEquals(
                beforeCount - 1, gameState.getDeckSizeForCurrentTurn());
        drawFromBottomEffect.useEffect(gameState);
        Assertions.assertEquals(
                beforeCount - 2, gameState.getDeckSizeForCurrentTurn());

    }

    @Test
    public void testSkipIntegrationTest() {
        EffectPattern skipEffect = new SkipEffect();
        GameDesigner gameDesigner = new GameDesigner(new JFrame());

        gameDesigner.initializeGameState(playerUsernames);
        GamePlayer gameBoard = gameDesigner.getGamePlayer();

        GameState gameState = gameBoard.getGameState();
        gameState.transitionToNextTurn();

        skipEffect.useEffect(gameState);

    }

}
