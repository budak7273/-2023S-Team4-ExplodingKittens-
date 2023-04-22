package system.IntegrationTesting.FeatureTesting;

import datasource.CardType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import presentation.GameDesigner;
import presentation.GameWindow;
import system.Card;
import system.GameManager;
import system.TestingUtils;
import system.User;
import system.cardEffects.ShuffleEffect;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class FeatureFourTesting {

    private GameManager gameManager;
    private User currentUser;

    @BeforeEach
    void runAsHeadless() {
        System.setProperty("java.awt.headless", "true");
    }

    @BeforeEach
    void setUp() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, TestingUtils.getFakeFrame());
        gameDesigner.initializeGameState(TestingUtils.getTestRandom());
        GameWindow gameWindow = gameDesigner.getGameWindow();
        gameManager = gameWindow.getGameManager();
        currentUser = gameManager.getUserForCurrentTurn();
    }

    @Test
    void testPlayerUsesAttack() {
        currentUser.addCard(new Card(CardType.ATTACK, null));
        int currentHandSize = currentUser.getHand().size();
        int currentDeckSize = gameManager.getDeckSizeForCurrentTurn();
        Assertions.assertEquals(currentUser.getName(),
                                "test1");
        currentUser.getHand().get(currentHandSize - 1).activateEffect(gameManager);
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                                "test2");
        Assertions.assertEquals(currentUser.getHand().size(),
                                currentHandSize - 1);
        Assertions.assertEquals(gameManager.getDeckSizeForCurrentTurn(),
                                currentDeckSize);
    }

    @Test
    void testPlayerUsesDrawFromBottom() {
        Card cardToAdd = new Card(CardType.DRAW_FROM_THE_BOTTOM, null);
        currentUser.addCard(cardToAdd);
        int currentHandSize = currentUser.getHand().size();
        int currentDeckSize = gameManager.getDeckSizeForCurrentTurn();
        Card topCard = gameManager.getDrawDeck().getCardsAsList().get(0);
        currentUser.getHand().get(currentHandSize - 1).activateEffect(gameManager);
        gameManager.removeCardFromCurrentUser(cardToAdd);
        Assertions.assertEquals(topCard,
                                gameManager.getDrawDeck().getCardsAsList().get(0));
        Assertions.assertEquals(currentUser.getHand().size(),
                                currentHandSize);
        Assertions.assertEquals(gameManager.getDeckSizeForCurrentTurn(),
                                currentDeckSize - 1);
    }

    @Test
    void testPlayerUsesShuffle() {
        ShuffleEffect shuffleEffect = new ShuffleEffect();
        shuffleEffect.setCurrentState(gameManager);
        currentUser.addCard(new Card(CardType.SHUFFLE, null));
        int handSize = currentUser.getHand().size();
        Card drawDeckTopCard = gameManager.getDrawDeck().getCardsAsList().get(0);

        currentUser.getHand().get(handSize - 1).activateEffect(gameManager);

        Assertions.assertNotEquals(drawDeckTopCard,
                                   gameManager.getDrawDeck().getCardsAsList().get(0));
    }

    @Test
    void testPlayerUsesSkip() {
        currentUser.addCard(new Card(CardType.SKIP, null));
        int currentHandSize = currentUser.getHand().size();
        int currentDeckSize = gameManager.getDeckSizeForCurrentTurn();

        Assertions.assertEquals(currentUser.getName(),
                                "test1");

        currentUser.getHand().get(currentHandSize - 1).activateEffect(gameManager);

        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                                "test2");
        Assertions.assertEquals(currentUser.getHand().size(),
                                currentHandSize - 1);
        Assertions.assertEquals(gameManager.getDeckSizeForCurrentTurn(),
                                currentDeckSize);
    }
}
