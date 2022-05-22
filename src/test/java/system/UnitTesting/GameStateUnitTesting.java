
package system.UnitTesting;

import datasource.CardType;
import org.easymock.IArgumentMatcher;
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

import static org.easymock.EasyMock.isA;


public class GameStateUnitTesting {

    static final int MAX_USER_COUNT = 10;
    static final int ARBITRARY_USER_ID_TO_KILL = 3;

    @Test
    public void testTransitionToNextTurnWithQueueOf1User() {
        Queue<User> pq = new LinkedList<User>();
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        pq.add(new User());
        GamePlayer board = EasyMock.createMock(GamePlayer.class);
        EasyMock.replay(board);
        GameState gameState = new GameState(pq, board, deck);
        Executable executable = gameState::transitionToNextTurn;
        Assertions.assertThrows(IllegalArgumentException.class, executable);
        EasyMock.verify(board);
    }

    @Test
    public void testTransitionToNextTurnWithQueueOf2Users() {
        GamePlayer boardMock = EasyMock.createMock(GamePlayer.class);
        boardMock.toggleCatMode();
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
        boardMock.toggleCatMode();
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
        GamePlayer board = EasyMock.createMock(GamePlayer.class);
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(board, deck);

        GameState gameState = new GameState(pq, board, deck);
        Executable executable = gameState::transitionToNextTurn;
        Assertions.assertThrows(IllegalArgumentException.class, executable);

        EasyMock.verify(board, deck);
    }

    @Test
    public void testTransitionToNextAliveWithThreeAliveUsers() {
        GamePlayer boardMock = EasyMock.createMock(GamePlayer.class);
        boardMock.toggleCatMode();
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
        boardMock.toggleCatMode();
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
        boardMock.toggleCatMode();
        boardMock.updateUI();
        EasyMock.expectLastCall();
        boardMock.displayWinForUser(user3);
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

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
        boardMock.toggleCatMode();
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
        User currentUser = EasyMock.createMock(User.class);
        EasyMock.expect(currentUser.isAlive()).andReturn(true);
        EasyMock.replay(currentUser);
        userQueue.add(currentUser);
        User otherUser = EasyMock.createMock(User.class);
        EasyMock.replay(otherUser);
        userQueue.add(new User());

        GamePlayer gameboard = EasyMock.createMock(GamePlayer.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(userQueue, gameboard, drawDeck);

        EasyMock.expect(drawDeck.drawFromBottomForUser(currentUser))
                .andReturn(false);
        gameboard.toggleCatMode();
        gameboard.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(gameboard, drawDeck);

        gameState.drawFromBottom();

        EasyMock.verify(gameboard, drawDeck, currentUser, otherUser);

    }

    @Test
    public void testDrawFromBottomWithKitten() {
        Queue<User> userQueue = new LinkedList<User>();

        User currentUser = EasyMock.createMock(User.class);
        EasyMock.expect(currentUser.isAlive()).andReturn(false);
        EasyMock.expect(currentUser.isAlive()).andReturn(false);
        EasyMock.expect(currentUser.isAlive()).andReturn(false);
        currentUser.attemptToDie();
        EasyMock.expectLastCall();
        EasyMock.replay(currentUser);
        userQueue.add(currentUser);

        User otherUser = EasyMock.createMock(User.class);
        EasyMock.expect(otherUser.isAlive()).andReturn(true);
        EasyMock.replay(otherUser);
        userQueue.add(otherUser);

        GamePlayer gameboard = EasyMock.createMock(GamePlayer.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(userQueue, gameboard, drawDeck);

        EasyMock.expect(drawDeck.drawFromBottomForUser(currentUser))
                .andReturn(true);
        gameboard.explosionNotification(currentUser.isAlive());
        gameboard.toggleCatMode();
        gameboard.updateUI();
        EasyMock.expectLastCall();
        gameboard.displayWinForUser(otherUser);
        EasyMock.replay(gameboard, drawDeck);

        gameState.drawFromBottom();

        EasyMock.verify(gameboard, drawDeck, currentUser, otherUser);
    }

    @Test
    public void testDrawCardNoKitten() {
        Queue<User> userQueue = new LinkedList<User>();
        User currentUser = EasyMock.createMock(User.class);
        EasyMock.expect(currentUser.isAlive()).andReturn(true);
        EasyMock.replay(currentUser);
        userQueue.add(currentUser);
        User otherUser = EasyMock.createMock(User.class);
        EasyMock.replay(otherUser);
        userQueue.add(new User());

        GamePlayer gameboard = EasyMock.createMock(GamePlayer.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(userQueue, gameboard, drawDeck);

        EasyMock.expect(drawDeck.drawCard(currentUser)).andReturn(false);
        gameboard.toggleCatMode();
        gameboard.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(gameboard, drawDeck);

        gameState.drawCardForCurrentTurn();

        EasyMock.verify(gameboard, drawDeck, currentUser, otherUser);
    }

    @Test
    public void testDrawCardWithKitten() {
        Queue<User> userQueue = new LinkedList<User>();

        User currentUser = EasyMock.createMock(User.class);
        EasyMock.expect(currentUser.isAlive()).andReturn(false);
        EasyMock.expect(currentUser.isAlive()).andReturn(false);
        currentUser.attemptToDie();
        EasyMock.expectLastCall();
        EasyMock.replay(currentUser);
        userQueue.add(currentUser);

        User otherUser = EasyMock.createMock(User.class);
        EasyMock.replay(otherUser);
        userQueue.add(otherUser);

        GamePlayer gameboard = EasyMock.createMock(GamePlayer.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);
        EasyMock.expect(drawDeck.drawCard(currentUser)).andReturn(true);
        gameboard.explosionNotification(currentUser.isAlive());
        EasyMock.expectLastCall();
        EasyMock.replay(gameboard, drawDeck);

        GameState gameState = new GameState(userQueue, gameboard, drawDeck);

        gameState.drawCardForCurrentTurn();
        EasyMock.verify(gameboard, drawDeck, currentUser, otherUser);
    }


    @Test
    public void testShuffleDeck() {
        Queue<User> userQueue = new LinkedList<User>();
        User currentUser = new User();
        userQueue.add(currentUser);
        userQueue.add(new User());

        GamePlayer gamePlayer = EasyMock.createMock(GamePlayer.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);
        EasyMock.expect(drawDeck.shuffle()).andReturn(true);
        EasyMock.expect(drawDeck.shuffle()).andReturn(true);
        EasyMock.replay(drawDeck);

        GameState gameState = new GameState(userQueue, gamePlayer, drawDeck);

        drawDeck.shuffle();
        gamePlayer.updateUI();
        EasyMock.replay(gamePlayer);
        gameState.shuffleDeck();
        Assertions.assertEquals(currentUser, gameState.getUserForCurrentTurn());
        EasyMock.verify(gamePlayer, drawDeck);
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
        EasyMock.replay(gamePlayer, deck);
        GameState gameState = new GameState(queue, gamePlayer, deck);
        Executable executable = gameState::tryToEndGame;
        Assertions.assertThrows(IllegalArgumentException.class, executable);
        EasyMock.verify(gamePlayer, deck);
    }

    @Test
    public void testTryToEndGameWith1UserRemaining() {
        User user = EasyMock.createMock(User.class);
        Queue<User> queue = new LinkedList<>();
        queue.add(user);
        GamePlayer gamePlayer = EasyMock.createMock(GamePlayer.class);
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);
        gamePlayer.displayWinForUser(user);
        EasyMock.expectLastCall();
        EasyMock.replay(user, gamePlayer, deck);
        GameState gameState = new GameState(queue, gamePlayer, deck);


        boolean gameIsOver = gameState.tryToEndGame();
        Assertions.assertTrue(gameIsOver);
        EasyMock.verify(user, gamePlayer, deck);
    }

    @Test
    public void testTryToEndGameWithAllUserRemaining() {
        User user = EasyMock.createMock(User.class);
        Queue<User> queue = new LinkedList<>();
        for (int i = 0; i < MAX_USER_COUNT; i++) {
            queue.add(user);
        }
        GamePlayer gamePlayer = EasyMock.createMock(GamePlayer.class);
        gamePlayer.displayWinForUser(isA(User.class));
        EasyMock.expectLastCall()
                .andThrow(new AssertionFailedError()).anyTimes();
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(user, gamePlayer, deck);
        GameState gameState = new GameState(queue, gamePlayer, deck);

        boolean gameIsOver = gameState.tryToEndGame();
        Assertions.assertFalse(gameIsOver);
        EasyMock.verify(user, gamePlayer, deck);
    }

    @Test
    public void testTransitionToNextTurnWithMinPlayersAndExtraTurn() {
        GamePlayer boardMock = EasyMock.createMock(GamePlayer.class);
        boardMock.toggleCatMode();
        boardMock.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> pq = new LinkedList<User>();
        User userStartingAtTopOfQueue = EasyMock.createMock(User.class);
        User userNextInQueue = EasyMock.createMock(User.class);
        pq.add(userStartingAtTopOfQueue);
        pq.add(userNextInQueue);
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(userStartingAtTopOfQueue, userNextInQueue, deck);

        GameState gameState = new GameState(pq, boardMock, deck);
        gameState.addExtraTurn();
        gameState.transitionToNextTurn();

        User userForCurrentTurn = gameState.getUserForCurrentTurn();
        Assertions.assertEquals(userStartingAtTopOfQueue, userForCurrentTurn);
        Assertions.assertEquals(0,
                gameState.getExtraTurnCountForCurrentUser());

        EasyMock.verify(boardMock, userStartingAtTopOfQueue,
                userNextInQueue, deck);
    }

    @Test
    public void testTransitionToNextTurnWithMaxPlayersAndExtraTurn() {
        GamePlayer boardMock = EasyMock.createMock(GamePlayer.class);
        boardMock.toggleCatMode();
        boardMock.updateUI();
        EasyMock.expectLastCall();
        EasyMock.replay(boardMock);

        Queue<User> pq = new LinkedList<User>();
        User userStartingAtTopOfQueue = EasyMock.createMock(User.class);
        pq.add(userStartingAtTopOfQueue);
        for (int i = 0; i < MAX_USER_COUNT - 1; i++) {
            pq.add(new User());
        }
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(userStartingAtTopOfQueue, deck);

        GameState gameState = new GameState(pq, boardMock, deck);
        gameState.addExtraTurn();
        gameState.transitionToNextTurn();

        User userForCurrentTurn = gameState.getUserForCurrentTurn();
        Assertions.assertEquals(userStartingAtTopOfQueue, userForCurrentTurn);
        Assertions.assertEquals(0, gameState.getExtraTurnCountForCurrentUser());

        EasyMock.verify(boardMock, userStartingAtTopOfQueue, deck);
    }

    @Test
    public void testTriggerDisplayOfTargetedAttackPrompt() {
        Queue<User> pq = new LinkedList<User>();
        User currentUser = EasyMock.createMock(User.class);
        pq.add(currentUser);
        for (int i = 0; i < MAX_USER_COUNT - 1; i++) {
            User user = EasyMock.createMock(User.class);
            pq.add(user);
        }

        GamePlayer gpMock = EasyMock.createMock(GamePlayer.class);
        gpMock.displayTargetedAttackPrompt(
                validTargetListForCurrentUser(currentUser));
        EasyMock.expectLastCall();

        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(gpMock, currentUser, deckMock);

        GameState gameState = new GameState(pq, gpMock, deckMock);
        gameState.triggerDisplayOfTargetedAttackPrompt();

        EasyMock.verify(gpMock, currentUser, deckMock);
    }

    private static List<User> validTargetListForCurrentUser(User user) {
        EasyMock.reportMatcher(new IArgumentMatcher() {
            @Override
            public boolean matches(Object argument) {
                return argument instanceof List
                        && ((List) argument).size() == MAX_USER_COUNT - 1
                        && !((List) argument).contains(user);
            }

            @Override
            public void appendTo(StringBuffer buffer) {
            }
        });
        return null;
    }

    @Test
    public void testExecuteTargetedAttackOnUserLastInQueue() {
        Queue<User> pq = new LinkedList<>();
        for (int i = 0; i < MAX_USER_COUNT - 1; i++) {
            User user = EasyMock.createMock(User.class);
            pq.add(user);
        }
        User targetUser = EasyMock.createMock(User.class);
        pq.add(targetUser);

        GamePlayer gpMock = EasyMock.createMock(GamePlayer.class);
        gpMock.updateUI();
        EasyMock.expectLastCall();
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(targetUser, gpMock, deckMock);

        GameState gameState = new GameState(pq, gpMock, deckMock);
        gameState.executeTargetedAttackOn(targetUser);

        Assertions.assertEquals(targetUser, gameState.getUserForCurrentTurn());
        Assertions.assertEquals(1, gameState.getExtraTurnCountForCurrentUser());
        EasyMock.verify(targetUser, gpMock, deckMock);
    }

    @Test
        public void testAddExplodingKittenIntoDeckTargettedAttack() {
        Queue<User> pq = new LinkedList<>();
        User user = EasyMock.createMock(User.class);
        pq.add(user);

        GamePlayer gpMock = EasyMock.createMock(GamePlayer.class);
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(gpMock, user, deckMock);

        GameState gameState = new GameState(pq, gpMock, deckMock);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            gameState.executeTargetedAttackOn(user);
        });
        EasyMock.verify(gpMock, user, deckMock);
    }

    @Test
    public void testAddExplodingKittenIntoDeck() {
        Queue<User> pq = new LinkedList<>();

        User player = EasyMock.createMock(User.class);
        EasyMock.expect(player.isAlive()).andReturn(true).times(2);
        EasyMock.replay(player);
        pq.add(player);
        pq.add(player);

        GamePlayer gpMock = EasyMock.createMock(GamePlayer.class);
        gpMock.toggleCatMode();
        gpMock.updateUI();
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        deckMock.addExplodingKittenAtLocation(0);
        EasyMock.expectLastCall();
        EasyMock.replay(gpMock, deckMock);

        GameState gameState = new GameState(pq, gpMock, deckMock);
        gameState.addExplodingKittenBackIntoDeck(0);

        Assertions.assertEquals(deckMock, gameState.getDrawDeck());
        EasyMock.verify(gpMock, deckMock, player);
    }

    @Test
    public void testTransitionToTurnOfUserWithQueueOf2Users() {
        Queue<User> pq = new LinkedList<>();
        User user = EasyMock.createMock(User.class);
        pq.add(user);
        User targetUser = EasyMock.createMock(User.class);
        pq.add(targetUser);

        GamePlayer gpMock = EasyMock.createMock(GamePlayer.class);
        gpMock.updateUI();
        EasyMock.expectLastCall();
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(targetUser, gpMock, user, deckMock);

        GameState gameState = new GameState(pq, gpMock, deckMock);
        gameState.executeTargetedAttackOn(targetUser);

        Assertions.assertEquals(targetUser, gameState.getUserForCurrentTurn());
        Assertions.assertEquals(1, gameState.getExtraTurnCountForCurrentUser());
        EasyMock.verify(targetUser, gpMock, user, deckMock);
    }

@Test
    public void testRemoveCardFromCurrentUser() {
        Queue<User> pq = new LinkedList<>();
        Card cardMock = EasyMock.createMockBuilder(Card.class)
                .withConstructor(CardType.ATTACK).createMock();
        EasyMock.replay(cardMock);

        User currentUser = EasyMock.createMock(User.class);
        currentUser.removeCard(cardMock);
        EasyMock.expectLastCall();
        EasyMock.replay(currentUser);
        pq.add(currentUser);

        GamePlayer gpMock = EasyMock.createMock(GamePlayer.class);
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(gpMock, deckMock);

        GameState gameState = new GameState(pq, gpMock, deckMock);
        gameState.removeCardFromCurrentUser(cardMock);
        EasyMock.verify(gpMock, deckMock, cardMock, currentUser);
}

    private List<User> validFavorListForCurrentUser(User user) {
        EasyMock.reportMatcher(new IArgumentMatcher() {
            @Override
            public boolean matches(Object argument) {
                return argument instanceof List
                        && ((List) argument).size() == MAX_USER_COUNT - 1
                        && !((List) argument).contains(user);
            }

            @Override
            public void appendTo(StringBuffer buffer) {
            }
        });
        return null;
    }

    @Test
    public void testExecuteFavorOnUserLastInQueue() {
        Queue<User> pq = new LinkedList<>();
        Card c = new Card(CardType.ATTACK);
        Card cardToGive = new Card(CardType.ATTACK);
        User currentUser = EasyMock.createMock(User.class);
        currentUser.addCard(cardToGive);
        currentUser.addCard(c);
        EasyMock.expectLastCall();
        EasyMock.replay(currentUser);
        currentUser.addCard(c);
        pq.add(currentUser);
        for (int i = 0; i < MAX_USER_COUNT - 1; i++) {
            User user = EasyMock.createMock(User.class);
            user.addCard(c);
            pq.add(user);
        }
        User targetUser = EasyMock.createMock(User.class);
        EasyMock.expect(targetUser.removeHand(0)).andReturn(cardToGive);
        EasyMock.replay(targetUser);
        pq.add(targetUser);

        GamePlayer gpMock = EasyMock.createMock(GamePlayer.class);
        EasyMock.expect(gpMock.inputForStealCard(targetUser)).andReturn(-1);
        EasyMock.expect(gpMock.inputForStealCard(targetUser)).andReturn(0);
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(gpMock, deckMock);

        GameState gameState = new GameState(pq, gpMock, deckMock);
        gameState.executeFavorOn(targetUser);

        Assertions.assertNotEquals(targetUser,
                gameState.getUserForCurrentTurn());
        Assertions.assertEquals(currentUser,
                gameState.getUserForCurrentTurn());

        EasyMock.verify(gpMock, deckMock, targetUser, currentUser);
    }

    @Test
    public void testSettingCardExecutionState0() {
        Queue<User> pq = new LinkedList<>();
        GamePlayer gpMock = EasyMock.createMock(GamePlayer.class);
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(gpMock, deckMock);

        GameState gameState = new GameState(pq, gpMock, deckMock);
        gameState.setCardExecutionState(0);
        Assertions.assertEquals(0, gameState.getCardExecutionState());
        EasyMock.verify(gpMock, deckMock);
    }

    @Test
    public void testSettingCardExecutionState1() {
        Queue<User> pq = new LinkedList<>();
        GamePlayer gpMock = EasyMock.createMock(GamePlayer.class);
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(gpMock, deckMock);

        GameState gameState = new GameState(pq, gpMock, deckMock);
        gameState.setCardExecutionState(1);
        Assertions.assertEquals(1, gameState.getCardExecutionState());
        EasyMock.verify(gpMock, deckMock);
    }

    @Test
    public void testTriggeringDisplayOfFavorPrompt() {
        Queue<User> pq = new LinkedList<>();
        ArrayList<User> users = new ArrayList<>();
        Card c = new Card(CardType.ATTACK);
        User currentUser = EasyMock.createMock(User.class);
        currentUser.addCard(c);
        pq.add(currentUser);
        for (int i = 0; i < MAX_USER_COUNT - 1; i++) {
            User user = EasyMock.createMock(User.class);
            user.addCard(c);
            pq.add(user);
        }
        User targetUser = EasyMock.createMock(User.class);
        EasyMock.replay(targetUser);
        pq.add(targetUser);
        users.addAll(pq);
        users.remove(currentUser);
        GamePlayer gpMock = EasyMock.createMock(GamePlayer.class);
        gpMock.displayFavorPrompt(users);
        EasyMock.expectLastCall();
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(gpMock, deckMock);

        GameState gameState = new GameState(pq, gpMock, deckMock);
        gameState.triggerDisplayOfFavorPrompt();
        EasyMock.verify(targetUser, deckMock, gpMock);
    }
}
