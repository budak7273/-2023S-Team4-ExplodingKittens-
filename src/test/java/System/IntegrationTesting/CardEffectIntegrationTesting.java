package system;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import system.cardEffects.*;
import system.cards.*;
import presentation.Gameboard;
import datasource.CardType;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

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
    public void testDrawFromBottomIntegrationTest() {
        EffectPattern drawFromBottomEffect = new DrawFromBottomEffect();
        Gameboard gameBoard = new Gameboard();
        List<String> playerUsernames = new ArrayList<>();
        playerUsernames.add("Player1ForIntegrationTest");
        playerUsernames.add("Player2ForIntegrationTest");
        playerUsernames.add("Player3ForIntegrationTest");
        playerUsernames.add("Player4ForIntegrationTest");
        playerUsernames.add("Player5ForIntegrationTest");

        gameBoard.initializeGameState(playerUsernames);

        GameState gameState = gameBoard.getGameState();
        int beforeCount = gameBoard.getDrawDeck().getCards().size();
        gameState.drawFromBottom();
        Assertions.assertEquals(beforeCount - 1, gameBoard.getDrawDeck().getCards().size());
        drawFromBottomEffect.useEffect(gameState);
        Assertions.assertEquals(beforeCount - 2, gameBoard.getDrawDeck().getCards().size());

    }

    @Test
    public void testSkipIntegrationTest() {
        EffectPattern skipEffect = new SkipEffect();
        Gameboard gameBoard = new Gameboard();
        List<String> playerUsernames = new ArrayList<>();
        playerUsernames.add("Player1ForIntegrationTest");
        playerUsernames.add("Player2ForIntegrationTest");
        playerUsernames.add("Player3ForIntegrationTest");
        playerUsernames.add("Player4ForIntegrationTest");
        playerUsernames.add("Player5ForIntegrationTest");

        gameBoard.initializeGameState(playerUsernames);

        GameState gameState = gameBoard.getGameState();
        gameState.transitionToNextTurn();

        skipEffect.useEffect(gameState);

    }

}
