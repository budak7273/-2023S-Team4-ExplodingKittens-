package system.IntegrationTesting;

import datasource.CardType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.*;

import presentation.GameDesigner;
import presentation.GameWindow;
import system.Card;
import system.GameManager;
import system.TestingUtils;
import system.User;
import system.DrawDeck;
import system.GameState;

import javax.swing.*;


class GameStateIntegrationTesting {

    GameStateIntegrationTesting() {
        System.setProperty("java.awt.headless", "false");
    }

    static final int MAX_USER_COUNT = 10;
    static final int ARBITRARY_USER_ID_TO_KILL = 3;

    @Test
    void testTransitionToNextTurnWithQueueOf1UserIntegrationTest() {
        Queue<User> pq = new LinkedList<User>();
        GameWindow board = new GameWindow(new JFrame());
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        pq.add(new User());
        GameState gameState = new GameState(pq, deck);
        GameManager gameManager = new GameManager(gameState, board);
        Executable executable = gameManager::transitionToNextTurn;
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void testTransitionToNextTurnWithQueueOf2UsersIntegrationTest() {
        Queue<User> pq = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        User userNextInQueue = new User();
        pq.add(userStartingAtTopOfQueue);
        pq.add(userNextInQueue);

        GameDesigner gameboard = new GameDesigner(pq, new JFrame());
        gameboard.initializeGameState(TestingUtils.getTestRandom());
        GameWindow gameWindow = gameboard.getGameWindow();
        GameManager gameManager = gameWindow.getGameManager();
        gameManager.transitionToNextTurn();

        User userForCurrentTurn = gameManager.getUserForCurrentTurn();
        Assertions.assertEquals(userNextInQueue, userForCurrentTurn);

    }

    @Test
    void testTransitionToNextTurnWithQueueOf10UsersIntegrationTest() {
        Queue<User> pq = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        User userNextInQueue = new User();
        pq.add(userStartingAtTopOfQueue);
        pq.add(userNextInQueue);
        for (int i = 0; i < MAX_USER_COUNT - 2; i++) {
            pq.add(new User());
        }
        GameDesigner gameboard = new GameDesigner(pq, new JFrame());
        gameboard.initializeGameState(TestingUtils.getTestRandom());
        GameWindow gameWindow = gameboard.getGameWindow();
        GameManager gameManager = gameWindow.getGameManager();
        gameManager.transitionToNextTurn();

        User userForCurrentTurn = gameManager.getUserForCurrentTurn();
        Assertions.assertEquals(userNextInQueue, userForCurrentTurn);

    }

    @Test
    void testTransitionToNextTurnWithQueueOf11UsersIntegrationTest() {
        Queue<User> pq = new LinkedList<User>();
        for (int i = 0; i < MAX_USER_COUNT + 1; i++) {
            pq.add(new User());
        }
        GameDesigner gameboard = new GameDesigner(pq, new JFrame());
        Executable executable = () -> gameboard.initializeGameState(TestingUtils.getTestRandom());
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    void testTransitionToNextAliveWithThreeAliveUsersIntegrationTest() {

        Queue<User> pq = new LinkedList<User>();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        pq.add(user1);
        pq.add(user2);
        pq.add(user3);

        GameDesigner gameboard = new GameDesigner(pq, new JFrame());
        gameboard.initializeGameState(TestingUtils.getTestRandom());
        GameWindow gameWindow = gameboard.getGameWindow();
        GameManager gameManager = gameWindow.getGameManager();

        gameManager.transitionToNextTurn();

        Queue<User> expected = new LinkedList<User>();
        expected.add(user2);
        expected.add(user3);
        expected.add(user1);
        Assertions.assertEquals(expected, gameManager.getPlayerQueue());

    }

    @Test
    void testTransitionToNextAliveWithThreeUsersFirstDeadIntegrationTest() {

        Queue<User> pq = new LinkedList<User>();
        User user1 = new User();
        user1.attemptToDie();
        User user2 = new User();
        User user3 = new User();
        pq.add(user1);
        pq.add(user2);
        pq.add(user3);

        GameDesigner gameboard = new GameDesigner(pq, new JFrame());
        gameboard.initializeGameState(TestingUtils.getTestRandom());
        GameWindow gameWindow = gameboard.getGameWindow();
        GameManager gameManager = gameWindow.getGameManager();

        gameManager.transitionToNextTurn();

        Queue<User> expected = new LinkedList<>();
        expected.add(user2);
        expected.add(user3);
        Assertions.assertEquals(expected, gameManager.getPlayerQueue());

    }

    @Test
    void testTransitionToNextAliveWithThreeUsersTwoDeadIntegrationTest() {

        Queue<User> pq = new LinkedList<>();
        User user1 = new User();
        user1.attemptToDie();
        User user2 = new User();
        user2.attemptToDie();
        User user3 = new User();
        pq.add(user1);
        pq.add(user2);
        pq.add(user3);

        GameDesigner gameboard = new GameDesigner(pq, new JFrame());
        gameboard.initializeGameState(TestingUtils.getTestRandom());
        GameWindow gameWindow = gameboard.getGameWindow();
        GameManager gameManager = gameWindow.getGameManager();

        gameManager.transitionToNextTurn();

        Queue<User> expected = new LinkedList<User>();
        expected.add(user3);
        Assertions.assertEquals(expected, gameManager.getPlayerQueue());

    }

    @Test
    void testTransitionToNextAliveWithTenUsersAndU1U2U4DeadIntegrationTest() {

        Queue<User> pq = new LinkedList<User>();
        Queue<User> expected = new LinkedList<User>();

        for (int i = 0; i < MAX_USER_COUNT; i++) {
            User user = new User();
            if (i == 0 || i == 1 || i == ARBITRARY_USER_ID_TO_KILL) {
                user.attemptToDie();
            }
            pq.add(user);
            if (i != 0 && i != 1) {
                expected.add(user);
            }
        }
        GameDesigner gameboard = new GameDesigner(pq, new JFrame());
        gameboard.initializeGameState(TestingUtils.getTestRandom());
        GameWindow gameWindow = gameboard.getGameWindow();
        GameManager gameManager = gameWindow.getGameManager();

        gameManager.transitionToNextTurn();

        Assertions.assertEquals(expected, gameManager.getPlayerQueue());

    }

    @Test
    void testRemoveCardWithKitten() {
        ArrayList<Card> cards = new ArrayList<Card>();
        Card attackCard = new Card(CardType.ATTACK);
        cards.add(attackCard);
        Queue<User> userQueue = new LinkedList<User>();
        User currentUser = new User("testUser1", false, cards);
        userQueue.add(currentUser);
        userQueue.add(new User());

        GameDesigner gameboard = new GameDesigner(userQueue, new JFrame());
        gameboard.initializeGameState(TestingUtils.getTestRandom());
        GameWindow gameWindow = gameboard.getGameWindow();
        GameManager gameManager = gameWindow.getGameManager();

        gameManager.removeCardFromCurrentUser(attackCard);
    }

    @Test
    void testShuffleDeckIntegrationTest() {
        Queue<User> userQueue = new LinkedList<>();
        User currentUser = new User();
        userQueue.add(currentUser);
        userQueue.add(new User());

        GameWindow gameWindow = new GameWindow(new JFrame());
        DrawDeck drawDeck = new DrawDeck(new ArrayList<>());
        GameState gameState = new GameState(userQueue, drawDeck);
        GameManager gameManager = new GameManager(gameState, gameWindow);
        gameWindow.setGameManager(gameManager);

        gameManager.shuffleDeck(true);
        Assertions.assertEquals(currentUser, gameState.getUserForCurrentTurn());
    }

    @Test
    void testExecuteCatStealOn() {
        Queue<User> pq = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        User secondUser = new User();
        User targetUser = new User();
        pq.add(userStartingAtTopOfQueue);
        pq.add(secondUser);
        pq.add(targetUser);
        final int alreadyAddedUsers = 3;
        for (int i = 0; i < MAX_USER_COUNT - alreadyAddedUsers; i++) {
            pq.add(new User());
        }

        GameDesigner boardMock = new GameDesigner(pq, new JFrame());
        boardMock.initializeGameState(TestingUtils.getTestRandom());
        GameWindow gameWindow = boardMock.getGameWindow();
        GameManager gameManager = gameWindow.getGameManager();
        int currentTargetSize = targetUser.getHand().size();
        gameManager.executeCatStealOn(targetUser, new Random());
        Assertions.assertEquals(targetUser.getHand().size(),
                currentTargetSize - 1);
    }

}
