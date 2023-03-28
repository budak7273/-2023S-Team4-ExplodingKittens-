package system.IntegrationTesting.FeatureTesting;

import datasource.CardType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import presentation.GameDesigner;
import presentation.GamePlayer;
import system.Card;
import system.GameManager;
import system.User;
import system.cardEffects.ShuffleEffect;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FeatureFourTesting {

    @Test
    public void testPlayerUsesAttack() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState();
        GamePlayer gamePlayer = gameDesigner.getGamePlayer();
        GameManager gameManager = gamePlayer.getGameManager();
        User currentUser = gameManager.getUserForCurrentTurn();
        currentUser.addCard(new Card(CardType.ATTACK));
        int currentHandSize = currentUser.getHand().size();
        int currentDeckSize = gameManager.getDeckSizeForCurrentTurn();
        Assertions.assertEquals(currentUser.getName(),
                "test1");
        currentUser.getHand().get(currentHandSize - 1)
                .activateEffect(gameManager);
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                "test2");
        Assertions.assertEquals(currentUser.getHand().size(),
                currentHandSize - 1);
        Assertions.assertEquals(gameManager.getDeckSizeForCurrentTurn(),
                currentDeckSize);
    }

    @Test
    public void testPlayerUsesDrawFromBottom() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState();
        GamePlayer gamePlayer = gameDesigner.getGamePlayer();
        GameManager gameManager = gamePlayer.getGameManager();
        User currentUser = gameManager.getUserForCurrentTurn();
        Card cardToAdd = new Card(CardType.DRAW_FROM_THE_BOTTOM);
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
    public void testPlayerUsesShuffle() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState();
        GamePlayer gamePlayer = gameDesigner.getGamePlayer();
        GameManager gameManager = gamePlayer.getGameManager();
        User currentUser = gameManager.getUserForCurrentTurn();
        ShuffleEffect shuffleEffect = new ShuffleEffect();
        shuffleEffect.setCurrentState(gameManager);
        CardType shuffleType = CardType.SHUFFLE;
        currentUser.addCard(new Card(shuffleType));
        int handSize = currentUser.getHand().size();
        Card topCard = gameManager.getDrawDeck().getCardsAsList().get(0);
        currentUser.getHand().get(handSize - 1).activateEffect(gameManager);
        Assertions.assertNotEquals(topCard,
                                   gameManager.getDrawDeck().getCardsAsList().get(0));
    }

    @Test
    public void testPlayerUsesSkip() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState();
        GamePlayer gamePlayer = gameDesigner.getGamePlayer();
        GameManager gameManager = gamePlayer.getGameManager();
        User currentUser = gameManager.getUserForCurrentTurn();
        currentUser.addCard(new Card(CardType.SKIP));
        int currentHandSize = currentUser.getHand().size();
        int currentDeckSize = gameManager.getDeckSizeForCurrentTurn();
        Assertions.assertEquals(currentUser.getName(),
                "test1");
        currentUser.getHand().get(currentHandSize - 1)
                .activateEffect(gameManager);
        Assertions.assertEquals(gameManager.getUserForCurrentTurn().getName(),
                "test2");
        Assertions.assertEquals(currentUser.getHand().size(),
                currentHandSize - 1);
        Assertions.assertEquals(gameManager.getDeckSizeForCurrentTurn(),
                currentDeckSize);
    }

}
