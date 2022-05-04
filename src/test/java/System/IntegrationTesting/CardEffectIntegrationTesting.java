package system;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import system.cardEffects.*;
import presentation.Gameboard;

import java.util.ArrayDeque;
import java.util.Queue;

public class CardEffectIntegrationTesting {

    @Test
    public void testDefuseBombEffectUseIntegrationTest() {
        EffectPattern bombEffectPattern = new DefuseBombEffect();
        Queue<User> playerQueue = new ArrayDeque<User>();
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
        Gameboard gameBoard = new Gameboard();
        DrawDeck drawDeck = new DrawDeck();

        GameState gameState = new GameState(playerQueue, gameBoard, drawDeck);

        Executable executable = () -> bombEffectPattern.useEffect(gameState);
        Assertions.assertDoesNotThrow(executable);

    }

    @Test
    public void testAttackEffectUseIntegrationTest() {
        EffectPattern bombEffectPattern = new AttackEffect();
        GameState gameState = new GameState();

        Executable executable = () -> bombEffectPattern.useEffect(gameState);
        Assertions.assertDoesNotThrow(executable);

    }

    @Test
    public void testDrawFromBottomIntegrationTest() {
        EffectPattern drawFromBottomEffect = new DrawFromBottomEffect();
        GameState gameState = new GameState();
        gameState.drawFromBottom();

        drawFromBottomEffect.useEffect(gameState);

    }

    @Test
    public void testSkipIntegrationTest() {
        EffectPattern skipEffect = new SkipEffect();
        GameState gameState = new GameState();
        gameState.transitionToNextTurn();

        skipEffect.useEffect(gameState);

    }

}
