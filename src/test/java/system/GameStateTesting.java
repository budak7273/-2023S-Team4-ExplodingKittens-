package system;

import presentation.Gameboard;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.LinkedList;
import java.util.Queue;

public class GameStateTesting {

    static final int MAX_USER_COUNT = 10;
    static final int ARBITRARY_USER_ID_TO_KILL = 3;

    @Test
    public void testTransitionToNextTurnWithQueueOf1User() {
        Queue<User> playerQueue = new LinkedList<User>();
        playerQueue.add(new User());
        GameState gameState = new GameState(playerQueue, new Gameboard(), new DrawDeck());
        Executable executable = () -> gameState.transitionToNextTurn();
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testTransitionToNextTurnWithQueueOf2Users() {
        Gameboard boardMock = EasyMock.createMock(Gameboard.class);
        boardMock.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> playerQueue = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        User userNextInQueue = new User();
        playerQueue.add(userStartingAtTopOfQueue);
        playerQueue.add(userNextInQueue);

        GameState gameState = new GameState(playerQueue, boardMock, new DrawDeck());
        gameState.transitionToNextTurn();

        User userForCurrentTurn = gameState.getUserForCurrentTurn();
        Assertions.assertEquals(userNextInQueue, userForCurrentTurn);

        EasyMock.verify(boardMock);
    }

    @Test
    public void testTransitionToNextTurnWithQueueOf10Users() {
        Gameboard boardMock = EasyMock.createMock(Gameboard.class);
        boardMock.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> playerQueue = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        User userNextInQueue = new User();
        playerQueue.add(userStartingAtTopOfQueue);
        playerQueue.add(userNextInQueue);
        for (int i = 0; i < MAX_USER_COUNT - 2; i++) {
            playerQueue.add(new User());
        }

        GameState gameState = new GameState(playerQueue, boardMock, new DrawDeck());
        gameState.transitionToNextTurn();
        User userForCurrentTurn = gameState.getUserForCurrentTurn();
        Assertions.assertEquals(userNextInQueue, userForCurrentTurn);

        EasyMock.verify(boardMock);
    }

    @Test
    public void testTransitionToNextTurnWithQueueOf11Users() {
        Queue<User> playerQueue = new LinkedList<User>();
        for (int i = 0; i < MAX_USER_COUNT + 1; i++) {
            playerQueue.add(new User());
        }
        GameState gameState = new GameState(playerQueue, new Gameboard(), new DrawDeck());
        Executable executable = () -> gameState.transitionToNextTurn();
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testTransitionToNextAliveWithThreeAliveUsers() {
        Gameboard boardMock = EasyMock.createMock(Gameboard.class);
        boardMock.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> playerQueue = new LinkedList<User>();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        playerQueue.add(user1);
        playerQueue.add(user2);
        playerQueue.add(user3);

        GameState gameState = new GameState(playerQueue, boardMock, new DrawDeck());
        gameState.transitionToNextTurn();

        Queue<User> expected = new LinkedList<User>();
        expected.add(user2);
        expected.add(user3);
        expected.add(user1);
        Assertions.assertEquals(expected, gameState.getPlayerQueue());

        EasyMock.verify(boardMock);
    }

    @Test
    public void testTransitionToNextAliveWithThreeUsersFirstDead() {
        Gameboard boardMock = EasyMock.createMock(Gameboard.class);
        boardMock.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> playerQueue = new LinkedList<User>();
        User user1 = new User();
        user1.die();
        User user2 = new User();
        User user3 = new User();
        playerQueue.add(user1);
        playerQueue.add(user2);
        playerQueue.add(user3);

        GameState gameState = new GameState(playerQueue, boardMock, new DrawDeck());
        gameState.transitionToNextTurn();

        Queue<User> expected = new LinkedList<User>();
        expected.add(user2);
        expected.add(user3);
        Assertions.assertEquals(expected, gameState.getPlayerQueue());

        EasyMock.verify(boardMock);
    }

    @Test
    public void testTransitionToNextAliveWithThreeUsersTwoDead() {
        Gameboard boardMock = EasyMock.createMock(Gameboard.class);
        boardMock.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> playerQueue = new LinkedList<User>();
        User user1 = new User();
        user1.die();
        User user2 = new User();
        user2.die();
        User user3 = new User();
        playerQueue.add(user1);
        playerQueue.add(user2);
        playerQueue.add(user3);

        GameState gameState = new GameState(playerQueue, boardMock, new DrawDeck());
        gameState.transitionToNextTurn();

        Queue<User> expected = new LinkedList<User>();
        expected.add(user3);
        Assertions.assertEquals(expected, gameState.getPlayerQueue());

        EasyMock.verify(boardMock);
    }

    @Test
    public void testTransitionToNextAliveWithTenUsersAndU1U2U4Dead() {
        Gameboard boardMock = EasyMock.createMock(Gameboard.class);
        boardMock.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> playerQueue = new LinkedList<User>();
        Queue<User> expected = new LinkedList<User>();

        for (int i = 0; i < MAX_USER_COUNT; i++) {
            User user = new User();
            if (i == 0 || i == 1 || i == ARBITRARY_USER_ID_TO_KILL) {
                user.die();
            }
            playerQueue.add(user);
            if (i != 0 && i != 1) {
                expected.add(user);
            }
        }

        GameState gameState = new GameState(playerQueue, boardMock, new DrawDeck());
        gameState.transitionToNextTurn();

        Assertions.assertEquals(expected, gameState.getPlayerQueue());

        EasyMock.verify(boardMock);
    }

    @Test
    public void testDrawFromBottom() {
        Queue<User> userQueue = new LinkedList<>();
        User currentUser = new User();
        userQueue.add(currentUser);
        userQueue.add(new User());

        Gameboard gameboard = EasyMock.createMock(Gameboard.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(userQueue, gameboard, drawDeck);

        drawDeck.drawFromBottomForUser(currentUser);
        EasyMock.expectLastCall();
        gameboard.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(gameboard, drawDeck);

        gameState.drawFromBottom();

        EasyMock.verify();
    }

}
