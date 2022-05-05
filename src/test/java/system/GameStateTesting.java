package system;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import presentation.GameDesigner;
import system.cards.AttackCard;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameStateTesting {

    private final List<Card> cards = new ArrayList<>();

    public GameStateTesting() {
        cards.add(new AttackCard());
    }

    static final int MAX_USER_COUNT = 10;
    static final int ARBITRARY_USER_ID_TO_KILL = 3;

    @Test
    public void testTransitionToNextTurnWithQueueOf1User() {
        Queue<User> pq = new LinkedList<User>();
        GameDesigner board = new GameDesigner();
        pq.add(new User());
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);
        GameState gameState = new GameState(pq, board, deck);
        Executable executable = () -> gameState.transitionToNextTurn();
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testTransitionToNextTurnWithQueueOf2Users() {
        GameDesigner boardMock = EasyMock.createMock(GameDesigner.class);
        boardMock.updatePlayerUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> pq = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        User userNextInQueue = new User();
        pq.add(userStartingAtTopOfQueue);
        pq.add(userNextInQueue);
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(pq, boardMock, deck);
        gameState.transitionToNextTurn();

        User userForCurrentTurn = gameState.getUserForCurrentTurn();
        Assertions.assertEquals(userNextInQueue, userForCurrentTurn);

        EasyMock.verify(boardMock);
    }

    @Test
    public void testTransitionToNextTurnWithQueueOf10Users() {
        GameDesigner boardMock = EasyMock.createMock(GameDesigner.class);
        boardMock.updatePlayerUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> pq = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        User userNextInQueue = new User();
        pq.add(userStartingAtTopOfQueue);
        pq.add(userNextInQueue);
        for (int i = 0; i < MAX_USER_COUNT - 2; i++) {
            pq.add(new User());
        }
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(pq, boardMock, deck);
        gameState.transitionToNextTurn();
        User userForCurrentTurn = gameState.getUserForCurrentTurn();
        Assertions.assertEquals(userNextInQueue, userForCurrentTurn);

        EasyMock.verify(boardMock);
    }

    @Test
    public void testTransitionToNextTurnWithQueueOf11Users() {
        Queue<User> pq = new LinkedList<User>();
        for (int i = 0; i < MAX_USER_COUNT + 1; i++) {
            pq.add(new User());
        }
        GameDesigner board = new GameDesigner();
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(pq, board, deck);
        Executable executable = () -> gameState.transitionToNextTurn();
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testTransitionToNextAliveWithThreeAliveUsers() {
        GameDesigner boardMock = EasyMock.createMock(GameDesigner.class);
        boardMock.updatePlayerUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> pq = new LinkedList<User>();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        pq.add(user1);
        pq.add(user2);
        pq.add(user3);
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);


        GameState gameState = new GameState(pq, boardMock, deck);
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
        GameDesigner boardMock = EasyMock.createMock(GameDesigner.class);
        boardMock.updatePlayerUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> pq = new LinkedList<User>();
        User user1 = new User();
        user1.die();
        User user2 = new User();
        User user3 = new User();
        pq.add(user1);
        pq.add(user2);
        pq.add(user3);
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(pq, boardMock, deck);
        gameState.transitionToNextTurn();

        Queue<User> expected = new LinkedList<User>();
        expected.add(user2);
        expected.add(user3);
        Assertions.assertEquals(expected, gameState.getPlayerQueue());

        EasyMock.verify(boardMock);
    }

    @Test
    public void testTransitionToNextAliveWithThreeUsersTwoDead() {
        GameDesigner boardMock = EasyMock.createMock(GameDesigner.class);
        boardMock.updatePlayerUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> pq = new LinkedList<User>();
        User user1 = new User();
        user1.die();
        User user2 = new User();
        user2.die();
        User user3 = new User();
        pq.add(user1);
        pq.add(user2);
        pq.add(user3);
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(pq, boardMock, deck);
        gameState.transitionToNextTurn();

        Queue<User> expected = new LinkedList<User>();
        expected.add(user3);
        Assertions.assertEquals(expected, gameState.getPlayerQueue());

        EasyMock.verify(boardMock);
    }

    @Test
    public void testTransitionToNextAliveWithTenUsersAndU1U2U4Dead() {
        GameDesigner boardMock = EasyMock.createMock(GameDesigner.class);
        boardMock.updatePlayerUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

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
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(pq, boardMock, deck);
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

        GameDesigner gameboard = EasyMock.createMock(GameDesigner.class);
        gameboard.updatePlayerUI();
        EasyMock.expectLastCall();
        EasyMock.replay(gameboard);
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);


        GameState gameState = new GameState(userQueue, gameboard, deck);

        gameState.drawFromBottom();

        EasyMock.verify(gameboard);
    }

    @Test
    public void testDrawFromCurrentDeck() {
        Queue<User> userQueue = new LinkedList<>();
        User currentUser = new User();
        userQueue.add(currentUser);
        userQueue.add(new User());

        GameDesigner gameboard = EasyMock.createMock(GameDesigner.class);
        gameboard.updatePlayerUI();
        EasyMock.expectLastCall();
        EasyMock.replay(gameboard);
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(userQueue, gameboard, deck);


        gameState.drawCardForCurrentTurn();
        int deckSize = gameState.getDeckSizeForCurrentTurn();
        final int expectedDeckSize = 0;
        Assertions.assertEquals(deckSize, expectedDeckSize);

        EasyMock.verify(gameboard);
    }

}
