package system.IntegrationTesting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.LinkedList;
import java.util.Queue;

import presentation.Gameboard;


public class GameStateIntegrationTesting {

    static final int MAX_USER_COUNT = 10;
    static final int ARBITRARY_USER_ID_TO_KILL = 3;

    @Test
    public void testTransitionToNextTurnWithQueueOf1UserIntegrationTest() {
        Queue<User> pq = new LinkedList<User>();
        Gameboard board = new Gameboard();
        DrawDeck deck = new DrawDeck();
        pq.add(new User());
        GameState gameState = new GameState(pq, board, deck);
        Executable executable = () -> gameState.transitionToNextTurn();
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testTransitionToNextTurnWithQueueOf2UsersIntegrationTest() {
        Gameboard boardMock = EasyMock.createMock(Gameboard.class);
        boardMock.updateUI();
        Queue<User> pq = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        User userNextInQueue = new User();
        pq.add(userStartingAtTopOfQueue);
        pq.add(userNextInQueue);
        DrawDeck deck = new DrawDeck();

        GameState gameState = new GameState(pq, boardMock, deck);
        gameState.transitionToNextTurn();

        User userForCurrentTurn = gameState.getUserForCurrentTurn();
        Assertions.assertEquals(userNextInQueue, userForCurrentTurn);

    }

    @Test
    public void testTransitionToNextTurnWithQueueOf10UsersIntegrationTest() {
        Gameboard boardMock = EasyMock.createMock(Gameboard.class);
        boardMock.updateUI();

        Queue<User> pq = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        User userNextInQueue = new User();
        pq.add(userStartingAtTopOfQueue);
        pq.add(userNextInQueue);
        for (int i = 0; i < MAX_USER_COUNT - 2; i++) {
            pq.add(new User());
        }
        DrawDeck deck = new DrawDeck();

        GameState gameState = new GameState(pq, boardMock, deck);
        gameState.transitionToNextTurn();
        User userForCurrentTurn = gameState.getUserForCurrentTurn();
        Assertions.assertEquals(userNextInQueue, userForCurrentTurn);

    }

    @Test
    public void testTransitionToNextTurnWithQueueOf11UsersIntegrationTest() {
        Queue<User> pq = new LinkedList<User>();
        for (int i = 0; i < MAX_USER_COUNT + 1; i++) {
            pq.add(new User());
        }
        Gameboard board = new Gameboard();
        DrawDeck deck = new DrawDeck();

        GameState gameState = new GameState(pq, board, deck);
        Executable executable = () -> gameState.transitionToNextTurn();
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testTransitionToNextAliveWithThreeAliveUsersIntegrationTest() {
        Gameboard boardMock = EasyMock.createMock(Gameboard.class);
        boardMock.updateUI();

        Queue<User> pq = new LinkedList<User>();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        pq.add(user1);
        pq.add(user2);
        pq.add(user3);
        DrawDeck deck = new DrawDeck();

        GameState gameState = new GameState(pq, boardMock, deck);
        gameState.transitionToNextTurn();

        Queue<User> expected = new LinkedList<User>();
        expected.add(user2);
        expected.add(user3);
        expected.add(user1);
        Assertions.assertEquals(expected, gameState.getPlayerQueue());

    }

    @Test
    public void testTransitionToNextAliveWithThreeUsersFirstDeadIntegrationTest() {
        Gameboard boardMock = EasyMock.createMock(Gameboard.class);
        boardMock.updateUI();

        Queue<User> pq = new LinkedList<User>();
        User user1 = new User();
        user1.die();
        User user2 = new User();
        User user3 = new User();
        pq.add(user1);
        pq.add(user2);
        pq.add(user3);
        DrawDeck deck = new DrawDeck();

        GameState gameState = new GameState(pq, boardMock, deck);
        gameState.transitionToNextTurn();

        Queue<User> expected = new LinkedList<User>();
        expected.add(user2);
        expected.add(user3);
        Assertions.assertEquals(expected, gameState.getPlayerQueue());

    }

    @Test
    public void testTransitionToNextAliveWithThreeUsersTwoDeadIntegrationTest() {
        Gameboard boardMock = EasyMock.createMock(Gameboard.class);
        boardMock.updateUI();
        Queue<User> pq = new LinkedList<User>();
        User user1 = new User();
        user1.die();
        User user2 = new User();
        user2.die();
        User user3 = new User();
        pq.add(user1);
        pq.add(user2);
        pq.add(user3);
        DrawDeck deck = new DrawDeck();

        GameState gameState = new GameState(pq, boardMock, deck);
        gameState.transitionToNextTurn();

        Queue<User> expected = new LinkedList<User>();
        expected.add(user3);
        Assertions.assertEquals(expected, gameState.getPlayerQueue());

    }

    @Test
    public void testTransitionToNextAliveWithTenUsersAndU1U2U4DeadIntegrationTest() {
        Gameboard boardMock = EasyMock.createMock(Gameboard.class);
        boardMock.updateUI();
        Queue<User> pq = new LinkedList<User>();
        Queue<User> expected = new LinkedList<User>();

        for (int i = 0; i < MAX_USER_COUNT; i++) {
            User user = new User();
            if (i == 0 || i == 1 || i == ARBITRARY_USER_ID_TO_KILL) {
                user.die();
            }
            pq.add(user);
            if (i != 0 && i != 1) {
                expected.add(user);
            }
        }
        DrawDeck deck = new DrawDeck();

        GameState gameState = new GameState(pq, boardMock, deck);
        gameState.transitionToNextTurn();

        Assertions.assertEquals(expected, gameState.getPlayerQueue());

    }

    @Test
    public void testDrawFromBottomIntegrationTest() {
        Queue<User> userQueue = new LinkedList<>();
        User currentUser = new User();
        userQueue.add(currentUser);
        userQueue.add(new User());

        Gameboard gameboard = EasyMock.createMock(Gameboard.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(userQueue, gameboard, drawDeck);

        drawDeck.drawFromBottomForUser(currentUser);

        gameboard.updateUI();

        gameState.drawFromBottom();

    }

    @Test
    public void testShuffleDeckIntegrationTest() {
        Queue<User> userQueue = new LinkedList<>();
        User currentUser = new User();
        userQueue.add(currentUser);
        userQueue.add(new User());

        Gameboard gameboard = EasyMock.createMock(Gameboard.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(userQueue, gameboard, drawDeck);

        drawDeck.shuffle();

        gameState.shuffleDeck();
        Assertions.assertEquals(currentUser, gameState.getUserForCurrentTurn());

    }

}
