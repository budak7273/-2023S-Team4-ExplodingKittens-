
package system.UnitTesting;

import datasource.CardType;
import org.easymock.IArgumentMatcher;
import org.opentest4j.AssertionFailedError;
import presentation.ExecutionState;
import presentation.GameWindow;
import org.easymock.EasyMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import system.*;

import java.util.*;

import static org.easymock.EasyMock.isA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class GameStateUnitTesting {

    static final int MAX_USER_COUNT = 10;
    static final int ARBITRARY_USER_ID_TO_KILL = 3;

    @Test
    public void testTransitionToNextTurnWithQueueOf1User() {
        Queue<User> pq = new LinkedList<User>();
        DrawDeck deck = new DrawDeck(new ArrayList<>());
        pq.add(new User());
        GameWindow board = EasyMock.createMock(GameWindow.class);
        EasyMock.replay(board);
        GameState gameState = new GameState(pq, deck);
        GameManager gameManager = new GameManager(gameState, board);
        Executable executable = gameManager::transitionToNextTurn;
        Assertions.assertThrows(IllegalArgumentException.class, executable);
        EasyMock.verify(board);
    }

    @Test
    public void testTransitionToNextTurnWithQueueOf2Users() {
        GameWindow gw = EasyMock.createMock(GameWindow.class);
        gw.disableCatMode();
        gw.updateUI();
        gw.updateEventHistoryLog("Event Log contents for player: \n");
        EasyMock.replay(gw);

        Queue<User> pq = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        User userNextInQueue = new User();
        pq.add(userStartingAtTopOfQueue);
        pq.add(userNextInQueue);
        DrawDeck deck = new DrawDeck(new ArrayList<>());

        GameState gameState = new GameState(pq, deck);
        GameManager gameManager = new GameManager(gameState, gw);
        gameManager.transitionToNextTurn();

        User userForCurrentTurn = gameManager.getUserForCurrentTurn();
        Assertions.assertEquals(userNextInQueue, userForCurrentTurn);

        EasyMock.verify(gw);
    }

    @Test
    public void testTransitionToNextTurnWithQueueOf10Users() {
        GameWindow gw = EasyMock.createMock(GameWindow.class);
        gw.disableCatMode();
        gw.updateUI();
        gw.updateEventHistoryLog("Event Log contents for player: \n");
        EasyMock.replay(gw);

        Queue<User> pq = new LinkedList<User>();
        User userStartingAtTopOfQueue = new User();
        User userNextInQueue = new User();
        pq.add(userStartingAtTopOfQueue);
        pq.add(userNextInQueue);
        for (int i = 0; i < MAX_USER_COUNT - 2; i++) {
            pq.add(new User());
        }
        DrawDeck deck = new DrawDeck(new ArrayList<>());

        GameState gameState = new GameState(pq, deck);
        GameManager gameManager = new GameManager(gameState, gw);
        gameManager.transitionToNextTurn();
        User userForCurrentTurn = gameManager.getUserForCurrentTurn();
        Assertions.assertEquals(userNextInQueue, userForCurrentTurn);

        EasyMock.verify(gw);
    }

    @Test
    public void testTransitionToNextTurnWithQueueOf11Users() {
        Queue<User> pq = new LinkedList<User>();
        for (int i = 0; i < MAX_USER_COUNT + 1; i++) {
            pq.add(new User());
        }
        GameWindow board = EasyMock.createMock(GameWindow.class);
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(board, deck);

        GameState gameState = new GameState(pq, deck);
        GameManager gameManager = new GameManager(gameState, board);
        Executable executable = gameManager::transitionToNextTurn;
        Assertions.assertThrows(IllegalArgumentException.class, executable);

        EasyMock.verify(board, deck);
    }

    @Test
    public void testTransitionToNextAliveWithThreeAliveUsers() {
        GameWindow gw = EasyMock.createMock(GameWindow.class);
        gw.disableCatMode();
        gw.updateUI();
        gw.updateEventHistoryLog("Event Log contents for player: \n");
        EasyMock.replay(gw);

        Queue<User> pq = new LinkedList<User>();
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        pq.add(user1);
        pq.add(user2);
        pq.add(user3);
        DrawDeck deck = new DrawDeck(new ArrayList<>());

        GameState gameState = new GameState(pq, deck);
        GameManager gameManager = new GameManager(gameState, gw);
        gameManager.transitionToNextTurn();

        Queue<User> expected = new LinkedList<User>();
        expected.add(user2);
        expected.add(user3);
        expected.add(user1);
        Assertions.assertEquals(expected, gameManager.getPlayerQueue());

        EasyMock.verify(gw);
    }

    @Test
    public void testTransitionToNextAliveWithThreeUsersFirstDead() {
        GameWindow gw = EasyMock.createMock(GameWindow.class);
        gw.disableCatMode();
        gw.updateUI();
        gw.updateEventHistoryLog("Event Log contents for player: \n");
        EasyMock.replay(gw);

        Queue<User> pq = new LinkedList<User>();
        User user1 = new User();
        user1.attemptToDie();
        User user2 = new User();
        User user3 = new User();
        pq.add(user1);
        pq.add(user2);
        pq.add(user3);
        DrawDeck deck = new DrawDeck(new ArrayList<>());

        GameState gameState = new GameState(pq, deck);
        GameManager gameManager = new GameManager(gameState, gw);
        gameManager.transitionToNextTurn();

        Queue<User> expected = new LinkedList<User>();
        expected.add(user2);
        expected.add(user3);
        Assertions.assertEquals(expected, gameManager.getPlayerQueue());

        EasyMock.verify(gw);
    }

    @Test
    public void testTransitionToNextAliveWithThreeUsersTwoDead() {
        GameWindow gw = EasyMock.createMock(GameWindow.class);

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
        gw.disableCatMode();
        gw.updateUI();
        gw.updateEventHistoryLog("Event Log contents for player: \n");
        gw.displayWinForUser(user3);
        EasyMock.replay(gw);

        GameState gameState = new GameState(pq, deck);
        GameManager gameManager = new GameManager(gameState, gw);
        gameManager.transitionToNextTurn();

        Queue<User> expected = new LinkedList<User>();
        expected.add(user3);
        Assertions.assertEquals(expected, gameManager.getPlayerQueue());

        EasyMock.verify(gw);
    }

    @Test
    public void testTransitionToNextAliveWithTenUsersAndU1U2U4Dead() {
        GameWindow gw = EasyMock.createMock(GameWindow.class);
        gw.disableCatMode();
        gw.updateUI();
        gw.updateEventHistoryLog("Event Log contents for player: \n");
        EasyMock.replay(gw);

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

        GameState gameState = new GameState(pq, deck);
        GameManager gameManager = new GameManager(gameState, gw);
        gameManager.transitionToNextTurn();

        Assertions.assertEquals(expected, gameManager.getPlayerQueue());

        EasyMock.verify(gw);
    }

    @Test
    public void testDrawCardNoExplodingKitten() {
        Queue<User> userQueue = new LinkedList<>();
        User currentUser = EasyMock.createMock(User.class);
        EasyMock.expect(currentUser.isAlive()).andReturn(true);
        EasyMock.expect(currentUser.getName()).andReturn("").times(2);
        EasyMock.expect(currentUser.getLastCardInHand()).andReturn(EasyMock.createNiceMock(Card.class));
        EasyMock.replay(currentUser);
        userQueue.add(currentUser);
        User otherUser = EasyMock.createMock(User.class);
        EasyMock.replay(otherUser);
        userQueue.add(new User());

        GameWindow gw = EasyMock.createMock(GameWindow.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(userQueue, drawDeck);
        GameManager gameManager = new GameManager(gameState, gw);

        EasyMock.expect(drawDeck.drawCard(currentUser)).andReturn(false);
        gw.disableCatMode();
        gw.updateUI();
        gw.updateEventHistoryLog("Event Log contents for player: \n"
                                 + "You drew a null\n");
        gw.updateEventHistoryLog("Event Log contents for player: \n"
                                 + " drew a card\n");
        EasyMock.replay(gw, drawDeck);

        gameManager.drawCardForCurrentTurn();
        gameManager.transitionToNextTurn();

        EasyMock.verify(gw, drawDeck, currentUser, otherUser);
    }

    @Test
    public void testDrawCardWithExplodingKitten() {
        Queue<User> userQueue = new LinkedList<User>();

        User currentUser = EasyMock.createMock(User.class);
        EasyMock.expect(currentUser.isAlive()).andReturn(false).times(2);
        EasyMock.expect(currentUser.getName()).andReturn("").times(2);
        EasyMock.expect(currentUser.getLastCardInHand()).andReturn(EasyMock.createNiceMock(Card.class));
        currentUser.attemptToDie();
        EasyMock.expectLastCall();
        EasyMock.replay(currentUser);
        userQueue.add(currentUser);

        User otherUser = EasyMock.createMock(User.class);
        EasyMock.replay(otherUser);
        userQueue.add(otherUser);

        GameWindow gw = EasyMock.createMock(GameWindow.class);
        gw.updateEventHistoryLog("Event Log contents for player: \n"
                                 + "You drew a null\n");
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);
        EasyMock.expect(drawDeck.drawCard(currentUser)).andReturn(true);
        gw.explosionNotification(currentUser.isAlive());
        EasyMock.expectLastCall();
        EasyMock.replay(gw, drawDeck);

        GameState gameState = new GameState(userQueue, drawDeck);
        GameManager gameManager = new GameManager(gameState, gw);

        gameManager.drawCardForCurrentTurn();
        EasyMock.verify(gw, drawDeck, currentUser, otherUser);
    }


    @Test
    public void testShuffleDeck() {
        Queue<User> userQueue = new LinkedList<User>();
        User currentUser = new User();
        userQueue.add(currentUser);
        userQueue.add(new User());

        GameWindow gameWindow = EasyMock.createMock(GameWindow.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);
        EasyMock.expect(drawDeck.shuffle()).andReturn(true);
        EasyMock.replay(drawDeck);

        GameState gameState = new GameState(userQueue, drawDeck);
        GameManager gameManager = new GameManager(gameState, gameWindow);

        drawDeck.shuffle();
        gameWindow.updateUI();
        gameWindow.updateEventHistoryLog("Event Log contents for player: \n"
                                         + " shuffled the deck\n");
        EasyMock.replay(gameWindow);

        gameManager.shuffleDeck(true);
        Assertions.assertEquals(currentUser, gameState.getUserForCurrentTurn());
        EasyMock.verify(gameWindow, drawDeck);
    }

    @Test
    public void testSeeTheFuture() {
        Queue<User> userQueue = new LinkedList<User>();
        User currentUser = new User();
        userQueue.add(currentUser);
        userQueue.add(new User());

        GameWindow gw = EasyMock.createMock(GameWindow.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(userQueue, drawDeck);
        GameManager gameManager = new GameManager(gameState, gw);

        ArrayList<Card> future = new ArrayList<Card>();

        gw.displayFutureCards(future);
        gw.updateEventHistoryLog("Event Log contents for player: \n"
                                 + " played See the Future\n");
        EasyMock.replay(gw, drawDeck);

        gameManager.executeSeeTheFuture(future);
        Assertions.assertEquals(currentUser, gameState.getUserForCurrentTurn());

        EasyMock.verify(gw, drawDeck);
    }

    @Test
    public void testAlterTheFuture() {
        Queue<User> userQueue = new LinkedList<User>();
        User currentUser = new User();
        userQueue.add(currentUser);
        userQueue.add(new User());

        GameWindow gw = EasyMock.createMock(GameWindow.class);
        DrawDeck drawDeck = EasyMock.createMock(DrawDeck.class);

        GameState gameState = new GameState(userQueue, drawDeck);
        GameManager gameManager = new GameManager(gameState, gw);

        ArrayList<Card> future = new ArrayList<Card>();

        gw.editFutureCards(future);
        gw.updateEventHistoryLog("Event Log contents for player: \n"
                                 + " played Alter the Future\n");
        EasyMock.replay(gw, drawDeck);

        gameManager.executeAlterTheFuture(future);
        Assertions.assertEquals(currentUser, gameState.getUserForCurrentTurn());

        EasyMock.verify(gw, drawDeck);
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

        GameWindow gameboard = EasyMock.createMock(GameWindow.class);
        DrawDeck drawDeck = EasyMock.createStrictMock(DrawDeck.class);
        drawDeck.addCardToTop(first);
        drawDeck.addCardToTop(second);
        drawDeck.addCardToTop(third);

        GameState gameState = new GameState(userQueue, drawDeck);
        GameManager gameManager = new GameManager(gameState, gameboard);
        EasyMock.replay(first, second, third, gameboard, drawDeck);

        gameManager.returnFutureCards(futures);
        EasyMock.verify(first, second, third, gameboard, drawDeck);
    }

    @Test
    public void testTryToEndGameWithEmptyQueue() {
        Queue<User> queue = new LinkedList<>();
        GameWindow gameWindow = EasyMock.createMock(GameWindow.class);
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(gameWindow, deck);
        GameState gameState = new GameState(queue, deck);
        GameManager gameManager = new GameManager(gameState, gameWindow);
        Executable executable = gameManager::tryToEndGame;
        Assertions.assertThrows(IllegalArgumentException.class, executable);
        EasyMock.verify(gameWindow, deck);
    }

    @Test
    public void testTryToEndGameWith1UserRemaining() {
        User user = EasyMock.createMock(User.class);
        Queue<User> queue = new LinkedList<>();
        queue.add(user);
        GameWindow gameWindow = EasyMock.createMock(GameWindow.class);
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);
        gameWindow.displayWinForUser(user);
        EasyMock.expectLastCall();
        EasyMock.replay(user, gameWindow, deck);
        GameState gameState = new GameState(queue, deck);
        GameManager gameManager = new GameManager(gameState, gameWindow);

        boolean gameIsOver = gameManager.tryToEndGame();
        Assertions.assertTrue(gameIsOver);
        EasyMock.verify(user, gameWindow, deck);
    }

    @Test
    public void testTryToEndGameWithAllUserRemaining() {
        User user = EasyMock.createMock(User.class);
        Queue<User> queue = new LinkedList<>();
        for (int i = 0; i < MAX_USER_COUNT; i++) {
            queue.add(user);
        }
        GameWindow gameWindow = EasyMock.createMock(GameWindow.class);
        gameWindow.displayWinForUser(isA(User.class));
        EasyMock.expectLastCall()
                .andThrow(new AssertionFailedError()).anyTimes();
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(user, gameWindow, deck);
        GameState gameState = new GameState(queue, deck);
        GameManager gameManager = new GameManager(gameState, gameWindow);

        boolean gameIsOver = gameManager.tryToEndGame();
        Assertions.assertFalse(gameIsOver);
        EasyMock.verify(user, gameWindow, deck);
    }

    @Test
    public void testTransitionToNextTurnWithMinPlayersAndExtraTurn() {
        GameWindow gw = EasyMock.createMock(GameWindow.class);
        gw.disableCatMode();
        gw.updateUI();
        gw.updateEventHistoryLog("Event Log contents for player: \n");
        EasyMock.replay(gw);

        Queue<User> pq = new LinkedList<User>();
        User userStartingAtTopOfQueue = EasyMock.createMock(User.class);
        User userNextInQueue = EasyMock.createMock(User.class);
        pq.add(userStartingAtTopOfQueue);
        pq.add(userNextInQueue);
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);
        EasyMock.expect(userStartingAtTopOfQueue.getName()).andReturn("");
        EasyMock.replay(userStartingAtTopOfQueue, userNextInQueue, deck);

        GameState gameState = new GameState(pq, deck);
        GameManager gameManager = new GameManager(gameState, gw);
        gameState.addExtraTurn();
        gameManager.transitionToNextTurn();

        User userForCurrentTurn = gameState.getUserForCurrentTurn();
        Assertions.assertEquals(userStartingAtTopOfQueue, userForCurrentTurn);
        Assertions.assertEquals(0,
                                gameState.getExtraTurnCountForCurrentUser());

        EasyMock.verify(gw, userStartingAtTopOfQueue,
                        userNextInQueue, deck);
    }

    @Test
    public void testTransitionToNextTurnWithMaxPlayersAndExtraTurn() {
        GameWindow gw = EasyMock.createMock(GameWindow.class);
        gw.disableCatMode();
        gw.updateUI();
        gw.updateEventHistoryLog("Event Log contents for player: \n");
        EasyMock.replay(gw);

        Queue<User> pq = new LinkedList<User>();
        User userStartingAtTopOfQueue = EasyMock.createMock(User.class);
        pq.add(userStartingAtTopOfQueue);
        for (int i = 0; i < MAX_USER_COUNT - 1; i++) {
            pq.add(new User());
        }
        DrawDeck deck = EasyMock.createMock(DrawDeck.class);
        EasyMock.expect(userStartingAtTopOfQueue.getName()).andReturn("");
        EasyMock.replay(userStartingAtTopOfQueue, deck);

        GameState gameState = new GameState(pq, deck);
        GameManager gameManager = new GameManager(gameState, gw);
        gameState.addExtraTurn();
        gameManager.transitionToNextTurn();

        User userForCurrentTurn = gameManager.getUserForCurrentTurn();
        Assertions.assertEquals(userStartingAtTopOfQueue, userForCurrentTurn);
        Assertions.assertEquals(0, gameState.getExtraTurnCountForCurrentUser());

        EasyMock.verify(gw, userStartingAtTopOfQueue, deck);
    }

    @Test
    public void testTriggerDisplayOfTargetedAttackPrompt() {
        Queue<User> pq = new LinkedList<User>();
        List<User> userList = new LinkedList<>();
        User currentUser = EasyMock.createMock(User.class);
        pq.add(currentUser);
        for (int i = 0; i < MAX_USER_COUNT - 1; i++) {
            User user = EasyMock.createMock(User.class);
            pq.add(user);
            userList.add(user);
        }

        GameWindow gpMock = EasyMock.createMock(GameWindow.class);
        gpMock.displayTargetedAttackPrompt(
                validTargetListForCurrentUser(currentUser));
        EasyMock.expectLastCall();

        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(gpMock, currentUser, deckMock);

        GameState gameState = new GameState(pq, deckMock);
        GameManager gameManager = new GameManager(gameState, gpMock);
        gameManager.triggerDisplayOfTargetedAttackPrompt(userList);

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
            User user = EasyMock.createNiceMock(User.class);
            EasyMock.expect(user.getName()).andReturn("");
            EasyMock.replay(user);
            pq.add(user);
        }
        User targetUser = EasyMock.createMock(User.class);
        pq.add(targetUser);

        GameWindow gw = EasyMock.createMock(GameWindow.class);
        gw.updateEventHistoryLog("Event Log contents for player: null\n"
                                 + " called a Targeted Attack on \n");
        gw.updateEventHistoryLog("Event Log contents for player: \n"
                                 + "You were attacked by null, so you have to play two turns\n");
        gw.updateUI();
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        EasyMock.expect(targetUser.getName()).andReturn("").times(2);
        EasyMock.replay(targetUser, gw, deckMock);

        GameState gameState = new GameState(pq, deckMock);
        GameManager gameManager = new GameManager(gameState, gw);
        gameManager.executeTargetedAttackOn(targetUser);

        Assertions.assertEquals(targetUser, gameState.getUserForCurrentTurn());
        Assertions.assertEquals(1, gameState.getExtraTurnCountForCurrentUser());
        EasyMock.verify(targetUser, gw, deckMock);
    }

    @Test
    public void testAddExplodingKittenIntoDeckTargetedAttack() {
        Queue<User> pq = new LinkedList<>();
        User user = EasyMock.createMock(User.class);
        final int expectedMessageDisplayCount = 4;
        EasyMock.expect(user.getName()).andReturn("").times(expectedMessageDisplayCount);
        pq.add(user);

        GameWindow gw = EasyMock.createMock(GameWindow.class);
        gw.updateEventHistoryLog("Event Log contents for player: \n"
                                 + "You were attacked by , so you have to play two turns\n");
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(gw, user, deckMock);

        GameState gameState = new GameState(pq, deckMock);
        GameManager gameManager = new GameManager(gameState, gw);
        try {
            gameManager.executeTargetedAttackOn(user);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Illegal number of players in queue", e.getMessage());
        }
        EasyMock.verify(gw, user, deckMock);
    }

    @Test
    public void testAddExplodingKittenIntoDeck() {
        Queue<User> pq = new LinkedList<>();

        User user = EasyMock.createMock(User.class);
        GameWindow window = EasyMock.createMock(GameWindow.class);
        EasyMock.expect(user.isAlive()).andReturn(true).times(2);
        EasyMock.expect(user.getName()).andReturn("");
        window.updateUI();

        EasyMock.replay(user);
        pq.add(user);
        pq.add(user);

        GameWindow gw = EasyMock.createMock(GameWindow.class);
        gw.disableCatMode();
        gw.updateUI();
        gw.updateEventHistoryLog("Event Log contents for player: \n");

        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        deckMock.addExplodingKittenAtLocation(0);
        EasyMock.expectLastCall();
        EasyMock.replay(gw, deckMock);

        GameState gameState = new GameState(pq, deckMock);
        GameManager gameManager = new GameManager(gameState, gw);
        gameManager.addExplodingKittenBackIntoDeck(0);
        Assertions.assertEquals(deckMock, gameState.getDrawDeck());
        EasyMock.verify(gw, deckMock, user);
    }

    @Test
    public void testTransitionToTurnOfUserWithQueueOf2Users() {
        Queue<User> pq = new LinkedList<>();
        User user = EasyMock.createMock(User.class);
        final int expectedMessageDisplayCount = 3;
        EasyMock.expect(user.getName()).andReturn("").times(expectedMessageDisplayCount);
        pq.add(user);
        User targetUser = EasyMock.createMock(User.class);
        EasyMock.expect(targetUser.getName()).andReturn("").times(2);
        pq.add(targetUser);

        GameWindow gw = EasyMock.createMock(GameWindow.class);
        gw.updateUI();
        EasyMock.expectLastCall();
        gw.updateEventHistoryLog("Event Log contents for player: \n"
                                 + " called a Targeted Attack on \n");
        gw.updateEventHistoryLog("Event Log contents for player: \n"
                                 + "You were attacked by , so you have to play two turns\n");
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(targetUser, gw, user, deckMock);

        GameState gameState = new GameState(pq, deckMock);
        GameManager gameManager = new GameManager(gameState, gw);
        gameManager.executeTargetedAttackOn(targetUser);

        Assertions.assertEquals(targetUser, gameState.getUserForCurrentTurn());
        Assertions.assertEquals(1, gameState.getExtraTurnCountForCurrentUser());
        EasyMock.verify(targetUser, gw, user, deckMock);
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

        GameWindow gpMock = EasyMock.createMock(GameWindow.class);
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(gpMock, deckMock);

        GameState gameState = new GameState(pq, deckMock);
        GameManager gameManager = new GameManager(gameState, gpMock);
        gameManager.removeCardFromCurrentUser(cardMock);
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
        final int expectedMessageDisplayCount = 3;
        EasyMock.expect(currentUser.getName()).andReturn("").times(expectedMessageDisplayCount);
        currentUser.addCard(cardToGive);
        currentUser.addCard(c);
        EasyMock.expectLastCall();
        GameWindow window = EasyMock.createMock(GameWindow.class);
        window.updateEventHistoryLog("\"Event Log contents for player: \n"
                                     + " took a Attack from  via a Favor\n"
                                     + "\"");
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
        EasyMock.expect(targetUser.getCardFromHand(0)).andReturn(cardToGive);
        EasyMock.expect(targetUser.getName()).andReturn("").times(2);
        targetUser.removeCard(cardToGive);
        EasyMock.replay(targetUser);
        pq.add(targetUser);

        GameWindow gw = EasyMock.createMock(GameWindow.class);
        EasyMock.expect(gw.inputForStealCard(targetUser)).andReturn(-1);
        EasyMock.expect(gw.inputForStealCard(targetUser)).andReturn(0);
        gw.updateEventHistoryLog("Event Log contents for player: \n"
                                 + " took a Attack from  via a Favor\n");
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(gw, deckMock);

        GameState gameState = new GameState(pq, deckMock);
        GameManager gameManager = new GameManager(gameState, gw);
        gameManager.executeFavorOn(targetUser);

        Assertions.assertNotEquals(targetUser,
                                   gameState.getUserForCurrentTurn());
        Assertions.assertEquals(currentUser,
                                gameState.getUserForCurrentTurn());

        EasyMock.verify(gw, deckMock, targetUser, currentUser);
    }

    @Test
    public void testSettingCardExecutionState0() {
        Queue<User> pq = new LinkedList<>();
        GameWindow gpMock = EasyMock.createMock(GameWindow.class);
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(gpMock, deckMock);

        GameState gameState = new GameState(pq, deckMock);
        GameManager gameManager = new GameManager(gameState, gpMock);
        gameManager.setCardExecutionState(ExecutionState.NORMAL);
        Assertions.assertEquals(ExecutionState.NORMAL, gameManager.getCardExecutionState());
        EasyMock.verify(gpMock, deckMock);
    }

    @Test
    public void testSettingCardExecutionState1() {
        Queue<User> pq = new LinkedList<>();
        GameWindow gpMock = EasyMock.createMock(GameWindow.class);
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(gpMock, deckMock);

        GameState gameState = new GameState(pq, deckMock);
        GameManager gameManager = new GameManager(gameState, gpMock);
        gameManager.setCardExecutionState(ExecutionState.ACTIVATED_EFFECT);
        Assertions.assertEquals(ExecutionState.ACTIVATED_EFFECT, gameManager.getCardExecutionState());
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
        GameWindow gpMock = EasyMock.createMock(GameWindow.class);
        gpMock.displayFavorPrompt(users);
        EasyMock.expectLastCall();
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(gpMock, deckMock);

        GameState gameState = new GameState(pq, deckMock);
        GameManager gameManager = new GameManager(gameState, gpMock);
        gameManager.triggerDisplayOfFavorPrompt(users);
        EasyMock.verify(targetUser, deckMock, gpMock);
    }

    @Test
    public void testCatCardStealingDisplay() {
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
        GameWindow gpMock = EasyMock.createMock(GameWindow.class);
        gpMock.displayCatStealPrompt(users);
        EasyMock.expectLastCall();
        DrawDeck deckMock = EasyMock.createMock(DrawDeck.class);
        EasyMock.replay(gpMock, deckMock);

        GameState gameState = new GameState(pq, deckMock);
        GameManager gameManager = new GameManager(gameState, gpMock);
        gameManager.triggerDisplayOfCatStealPrompt();
        EasyMock.verify(gpMock, deckMock);
    }
}
