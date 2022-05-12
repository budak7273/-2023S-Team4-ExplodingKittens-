
package system.UnitTesting;

import org.opentest4j.AssertionFailedError;
import presentation.GamePlayer;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import system.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class GameStateUnitTesting {

    static final int MAX_USER_COUNT = 10;
    static final int ARBITRARY_USER_ID_TO_KILL = 3;

    @Test
    public void testTransitionToNextTurnWithQueueOf1User() {
        Queue<User> pq = new LinkedList<User>();
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        pq.add(new User());
        GamePlayer board = new GamePlayer();
        GameState gameState = new GameState(pq, board, deck);
        Executable executable = gameState::transitionToNextTurn;
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testTransitionToNextTurnWithQueueOf2Users() {
        GamePlayer boardMock = EasyMock.createMock(GamePlayer.class);
        boardMock.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> pq = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        User userNextInQueue = new User();
        pq.add(userStartingAtTopOfQueue);
        pq.add(userNextInQueue);
        DrawDeck deck = new DrawDeck(new ArrayList<>());

        GameState gameState = new GameState(pq, boardMock, deck);
        gameState.transitionToNextTurn();

        User userForCurrentTurn = gameState.getUserForCurrentTurn();
        Assertions.assertEquals(userNextInQueue, userForCurrentTurn);

        EasyMock.verify(boardMock);
    }

    @Test
    public void testTransitionToNextTurnWithQueueOf10Users() {
        GamePlayer boardMock = EasyMock.createMock(GamePlayer.class);
        boardMock.updateUI();
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
        DrawDeck deck = new DrawDeck(new ArrayList<>());

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
        GamePlayer board = new GamePlayer();
        DrawDeck deck = new DrawDeck(new ArrayList<>());

        GameState gameState = new GameState(pq, board, deck);
        Executable executable = gameState::transitionToNextTurn;
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testTransitionToNextAliveWithThreeAliveUsers() {
        GamePlayer boardMock = EasyMock.createMock(GamePlayer.class);
        boardMock.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> pq = new LinkedList<User>();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        pq.add(user1);
        pq.add(user2);
        pq.add(user3);
        DrawDeck deck = new DrawDeck(new ArrayList<>());

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
        GamePlayer boardMock = EasyMock.createMock(GamePlayer.class);
        boardMock.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> pq = new LinkedList<User>();
        User user1 = new User();
        user1.attemptToDie();
        User user2 = new User();
        User user3 = new User();
        pq.add(user1);
        pq.add(user2);
        pq.add(user3);
        DrawDeck deck = new DrawDeck(new ArrayList<>());

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
        GamePlayer boardMock = EasyMock.createMock(GamePlayer.class);
        boardMock.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> pq = new LinkedList<User>();
        User user1 = new User();
        user1.attemptToDie();
        User user2 = new User();
        user2.attemptToDie();
        User user3 = new User();
        pq.add(user1);
        pq.add(user2);
        pq.add(user3);
        DrawDeck deck = new DrawDeck(new ArrayList<>());

        GameState gameState = new GameState(pq, boardMock, deck);
        gameState.transitionToNextTurn();

        Queue<User> expected = new LinkedList<User>();
        expected.add(user3);
        Assertions.assertEquals(expected, gameState.getPlayerQueue());

        EasyMock.verify(boardMock);
    }

    @Test
    public void testTransitionToNextAliveWithTenUsersAndU1U2U4Dead() {
        GamePlayer boardMock = EasyMock.createMock(GamePlayer.class);
        boardMock.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

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
        DrawDeck deck = new DrawDeck(new ArrayList<>());

        GameState gameState = new GameState(pq, boardMock, deck);
        gameState.transitionToNextTurn();

        Assertions.assertEquals(expected, gameState.getPlayerQueue());

        EasyMock.verify(boardMock);
    }

    @Test
    public void testDrawFromBottomNoKitten() {
        Queue<User> userQueue = new LinkedList<User>();
        User currentUser = new User();
        userQueue.add(currentUser);
        userQueue.add(new User());

        GamePlayer gameboard = EasyMock.createMock(GamePlayer.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(userQueue, gameboard, drawDeck);

        EasyMock.expect(drawDeck.drawFromBottomForUser(currentUser))
                .andReturn(false);
        gameboard.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(gameboard, drawDeck);

        gameState.drawFromBottom();

    }

    @Test
    public void testDrawFromBottomWithKitten() {
        Queue<User> userQueue = new LinkedList<User>();
        User currentUser = new User();
        userQueue.add(currentUser);
        userQueue.add(new User());

        GamePlayer gameboard = EasyMock.createMock(GamePlayer.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(userQueue, gameboard, drawDeck);

        EasyMock.expect(drawDeck.drawFromBottomForUser(currentUser))
                .andReturn(true);
        currentUser.attemptToDie();
        gameboard.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(gameboard, drawDeck);

        gameState.drawFromBottom();

    }

    @Test
    public void testDrawCardNoKitten() {
        Queue<User> userQueue = new LinkedList<User>();
        User currentUser = new User();
        userQueue.add(currentUser);
        userQueue.add(new User());

        GamePlayer gameboard = EasyMock.createMock(GamePlayer.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(userQueue, gameboard, drawDeck);

        EasyMock.expect(drawDeck.drawCard(currentUser)).andReturn(false);
        gameboard.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(gameboard, drawDeck);

        gameState.drawCardForCurrentTurn();
    }

    @Test
    public void testDrawCardWithKitten() {
        Queue<User> userQueue = new LinkedList<User>();
        User currentUser = new User();
        userQueue.add(currentUser);
        userQueue.add(new User());

        GamePlayer gameboard = EasyMock.createMock(GamePlayer.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(userQueue, gameboard, drawDeck);

        EasyMock.expect(drawDeck.drawCard(currentUser)).andReturn(true);
        currentUser.attemptToDie();
        gameboard.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(gameboard, drawDeck);

        gameState.drawCardForCurrentTurn();
    }


    @Test
    public void testShuffleDeck() {
        Queue<User> userQueue = new LinkedList<User>();
        User currentUser = new User();
        userQueue.add(currentUser);
        userQueue.add(new User());

        GamePlayer gameboard = EasyMock.createMock(GamePlayer.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(userQueue, gameboard, drawDeck);

        drawDeck.shuffle();
        EasyMock.replay(gameboard, drawDeck);

        gameState.shuffleDeck();
        Assertions.assertEquals(currentUser, gameState.getUserForCurrentTurn());
    }

    @Test
    public void testSeeTheFuture() {
        Queue<User> userQueue = new LinkedList<User>();
        User currentUser = new User();
        userQueue.add(currentUser);
        userQueue.add(new User());

        GamePlayer gameboard = EasyMock.createMock(GamePlayer.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(userQueue, gameboard, drawDeck);

        List<Card> future = EasyMock.createMock(ArrayList.class);
        EasyMock.expect(drawDeck.drawThreeCardsFromTop()).andReturn(future);

        gameboard.displayFutureCards(future);
        EasyMock.replay(gameboard, drawDeck, future);

        gameState.seeTheFuture();
        Assertions.assertEquals(currentUser, gameState.getUserForCurrentTurn());

        EasyMock.verify(gameboard, drawDeck, future);
    }

    @Test
    public void testAlterTheFuture() {
        Queue<User> userQueue = new LinkedList<User>();
        User currentUser = new User();
        userQueue.add(currentUser);
        userQueue.add(new User());

        GamePlayer gameboard = EasyMock.createMock(GamePlayer.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(userQueue, gameboard, drawDeck);

        List<Card> future = EasyMock.createMock(ArrayList.class);
        EasyMock.expect(drawDeck.drawThreeCardsFromTop()).andReturn(future);

        gameboard.editFutureCards(future);
        EasyMock.replay(gameboard, drawDeck, future);

        gameState.alterTheFuture();
        Assertions.assertEquals(currentUser, gameState.getUserForCurrentTurn());

        EasyMock.verify(gameboard, drawDeck, future);
    }

    @Test
    public void testReturnFutureCards() {
        Queue<User> userQueue = new LinkedList<User>();
        User currentUser = new User();
        userQueue.add(currentUser);
        userQueue.add(new User());

        Card first = EasyMock.createMock(Card.class);
        Card second = EasyMock.createMock(Card.class);
        Card third = EasyMock.createMock(Card.class);
        ArrayList<Card> futures = new ArrayList<Card>();
        futures.add(third);
        futures.add(second);
        futures.add(first);

        GamePlayer gameboard = EasyMock.createMock(GamePlayer.class);
        DrawDeck drawDeck = EasyMock.createStrictMock(DrawDeck.class);
        drawDeck.addCardToTop(first);
        drawDeck.addCardToTop(second);
        drawDeck.addCardToTop(third);

        GameState gameState = new GameState(userQueue, gameboard, drawDeck);
        EasyMock.replay(first, second, third, gameboard, drawDeck);

        gameState.returnFutureCards(futures);
        EasyMock.verify(first, second, third, gameboard, drawDeck);
    }

    @Test
    public void testTryToEndGameWithEmptyQueue() {
        Queue<User> queue = new LinkedList<>();
        GamePlayer gamePlayer = EasyMock.createMock(GamePlayer.class);
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);
        GameState gameState = new GameState(queue, gamePlayer, deck);

        Executable executable = gameState::tryToEndGame;
        Assertions.assertThrows(IllegalArgumentException.class, executable);
    }

    @Test
    public void testTryToEndGameWith1UserRemaining() {
        User user = EasyMock.createMock(User.class);
        Queue<User> queue = new LinkedList<>();
        queue.add(user);
        GamePlayer gamePlayer = EasyMock.createMock(GamePlayer.class);
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);
        GameState gameState = new GameState(queue, gamePlayer, deck);

        gamePlayer.endGame();
        EasyMock.expectLastCall();

        boolean gameIsOver = gameState.tryToEndGame();
        Assertions.assertTrue(gameIsOver);
    }

    @Test
    public void testTryToEndGameWithAllUserRemaining() {
        User user = EasyMock.createMock(User.class);
        Queue<User> queue = new LinkedList<>();
        for (int i = 0; i < MAX_USER_COUNT; i++) {
            queue.add(user);
        }
        GamePlayer gamePlayer = EasyMock.createMock(GamePlayer.class);
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);
        GameState gameState = new GameState(queue, gamePlayer, deck);

        gamePlayer.endGame();
        EasyMock.expectLastCall()
                .andThrow(new AssertionFailedError()).anyTimes();

        boolean gameIsOver = gameState.tryToEndGame();
        Assertions.assertFalse(gameIsOver);
    }

    @Test
    public void testTransitionToNextTurnWithMinPlayersAndExtraTurn() {
        GamePlayer boardMock = EasyMock.createMock(GamePlayer.class);
        boardMock.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> pq = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        User userNextInQueue = new User();
        pq.add(userStartingAtTopOfQueue);
        pq.add(userNextInQueue);
        DrawDeck deck = new DrawDeck(new ArrayList<>());

        GameState gameState = new GameState(pq, boardMock, deck);
        gameState.addExtraTurn();
        gameState.transitionToNextTurn();

        User userForCurrentTurn = gameState.getUserForCurrentTurn();
        Assertions.assertEquals(userStartingAtTopOfQueue, userForCurrentTurn);
        Assertions.assertEquals(0, gameState.getExtraTurnCountForCurrentUser());

        EasyMock.verify(boardMock);
    }

    @Test
    public void testTransitionToNextTurnWithMaxPlayersAndExtraTurn() {
        GamePlayer boardMock = EasyMock.createMock(GamePlayer.class);
        boardMock.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> pq = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        pq.add(userStartingAtTopOfQueue);
        for (int i = 0; i < MAX_USER_COUNT - 1; i++) {
            pq.add(new User());
        }
        DrawDeck deck = new DrawDeck(new ArrayList<>());

        GameState gameState = new GameState(pq, boardMock, deck);
        gameState.addExtraTurn();
        gameState.transitionToNextTurn();

        User userForCurrentTurn = gameState.getUserForCurrentTurn();
        Assertions.assertEquals(userStartingAtTopOfQueue, userForCurrentTurn);
        Assertions.assertEquals(0, gameState.getExtraTurnCountForCurrentUser());

        EasyMock.verify(boardMock);
    }
}
