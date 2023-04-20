package system.IntegrationTesting;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.ArrayList;

import presentation.GameWindow;
import system.GameManager;
import system.TestingUtils;
import system.cardEffects.*;
import system.User;
import system.DrawDeck;
import system.GameState;

import javax.swing.*;


class CardEffectIntegrationTesting {

    private static final int NUM_PLAYERS = 5;
    private GameManager gameManager;

    @BeforeEach
    void runAsHeadless() {
        System.setProperty("java.awt.headless", "true");
    }

    @BeforeEach
    void setUp() {
        Queue<User> playerQueue = new ArrayDeque<>();
        for (int index = 1; index <= NUM_PLAYERS; index++) {
            String username = "Player" + index + "ForIntegrationTest";
            playerQueue.add(new User(username));
        }

        JFrame frame = TestingUtils.getFakeFrame();
        GameWindow gameWindow = new GameWindow(frame, true);
        DrawDeck drawDeck = new DrawDeck(new ArrayList<>());

        GameState gameState = new GameState(playerQueue, drawDeck);
        gameManager = new GameManager(gameState, gameWindow);
        gameWindow.setGameManager(gameManager);
    }

    @Test
    void testDefuseBombEffectUseIntegrationTest() {
        EffectPattern bombEffectPattern = new DefuseBombEffect();
        bombEffectPattern.setCurrentState(gameManager);

        Assertions.assertDoesNotThrow(bombEffectPattern::useEffect);
    }

    @Test
    void testAttackEffectUseIntegrationTest() {
        EffectPattern bombEffectPattern = new AttackEffect();
        bombEffectPattern.setCurrentState(gameManager);

        Assertions.assertDoesNotThrow(bombEffectPattern::useEffect);
    }
}
