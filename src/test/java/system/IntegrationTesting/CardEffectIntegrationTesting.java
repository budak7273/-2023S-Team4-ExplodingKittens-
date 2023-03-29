package system.IntegrationTesting;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

import presentation.GameDesigner;
import presentation.GameWindow;
import system.GameManager;
import system.cardEffects.*;
import system.User;
import system.DrawDeck;
import system.GameState;

import javax.swing.*;


class CardEffectIntegrationTesting {

    private Queue<User> playerQueue;

    private List<String> playerUsernames;

    @BeforeEach
    void setUp() {
        playerQueue = new ArrayDeque<>();
        playerUsernames = new ArrayList<>();
        for (int index = 1; index <= 5; index++) {
            String username = "Player" + index + "ForIntegrationTest";
            playerQueue.add(new User(username));
            playerUsernames.add(username);
        }
    }

    @Test
    void testDefuseBombEffectUseIntegrationTest() {
        EffectPattern bombEffectPattern = new DefuseBombEffect();
        GameWindow gameWindow = new GameWindow(new JFrame());
        DrawDeck drawDeck = new DrawDeck(new ArrayList<>());

        GameState gameState = new GameState(playerQueue, drawDeck);
        GameManager gameManager = new GameManager(gameState, gameWindow);
        gameWindow.setGameManager(gameManager);
        bombEffectPattern.setCurrentState(gameManager);

        Assertions.assertDoesNotThrow(bombEffectPattern::useEffect);

    }

    @Test
    void testAttackEffectUseIntegrationTest() {
        EffectPattern bombEffectPattern = new AttackEffect();
        GameWindow gameWindow = new GameWindow(new JFrame());
        DrawDeck drawDeck = new DrawDeck(new ArrayList<>());

        GameState gameState = new GameState(playerQueue, drawDeck);
        GameManager gameManager = new GameManager(gameState, gameWindow);
        gameWindow.setGameManager(gameManager);
        bombEffectPattern.setCurrentState(gameManager);

        Assertions.assertDoesNotThrow(bombEffectPattern::useEffect);

    }

    @Test
    void testDrawFromBottomIntegrationTest() {
        EffectPattern drawFromBottomEffect = new DrawFromBottomEffect();
        GameDesigner gameDesigner = new GameDesigner(new JFrame());

        gameDesigner.initializeGameState(playerUsernames);
        GameWindow gameBoard = gameDesigner.getGameWindow();

        GameManager gameManager = gameBoard.getGameManager();
        int beforeCount = gameManager.getDeckSizeForCurrentTurn();
        drawFromBottomEffect.setCurrentState(gameManager);
        Assertions.assertEquals(
                beforeCount, gameManager.getDeckSizeForCurrentTurn());
        drawFromBottomEffect.useEffect();
        Assertions.assertEquals(
                beforeCount - 1, gameManager.getDeckSizeForCurrentTurn());

    }

    @Test
    void testSkipIntegrationTest() {
        EffectPattern skipEffect = new SkipEffect();
        GameDesigner gameDesigner = new GameDesigner(new JFrame());

        gameDesigner.initializeGameState(playerUsernames);
        GameWindow gameBoard = gameDesigner.getGameWindow();

        GameManager gameManager = gameBoard.getGameManager();
        skipEffect.setCurrentState(gameManager);
        skipEffect.useEffect();

    }

}
