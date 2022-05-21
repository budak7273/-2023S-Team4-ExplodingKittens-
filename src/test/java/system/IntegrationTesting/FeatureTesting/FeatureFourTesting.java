package system.IntegrationTesting.FeatureTesting;

import datasource.CardType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import presentation.GameDesigner;
import presentation.GamePlayer;
import system.Card;
import system.GameState;
import system.User;
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
        GameState gameState = gamePlayer.getGameState();
        User currentUser = gameState.getUserForCurrentTurn();
        currentUser.addCard(new Card(CardType.ATTACK));
        int currentHandSize = currentUser.getHand().size();
        int currentDeckSize = gameState.getDeckSizeForCurrentTurn();
        Assertions.assertEquals(currentUser.getName(),
                "test1");
        currentUser.getHand().get(currentHandSize - 1)
                .activateEffect(gameState);
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "test2");
        Assertions.assertEquals(currentUser.getHand().size(),
                currentHandSize - 1);
        Assertions.assertEquals(gameState.getDeckSizeForCurrentTurn(),
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
        GameState gameState = gamePlayer.getGameState();
        User currentUser = gameState.getUserForCurrentTurn();
        currentUser.addCard(new Card(CardType.DRAW_FROM_THE_BOTTOM));
        int currentHandSize = currentUser.getHand().size();
        int currentDeckSize = gameState.getDeckSizeForCurrentTurn();
        Card topCard = gameState.getDrawDeck().getCardsAsList().get(0);
        gameState.drawFromBottom();
        Assertions.assertEquals(topCard,
                gameState.getDrawDeck().getCardsAsList().get(0));
        Assertions.assertEquals(currentUser.getHand().size(),
                currentHandSize + 1);
        Assertions.assertEquals(gameState.getDeckSizeForCurrentTurn(),
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
        GameState gameState = gamePlayer.getGameState();
        User currentUser = gameState.getUserForCurrentTurn();
        currentUser.addCard(new Card(CardType.SHUFFLE));
        Card topCard = gameState.getDrawDeck().getCardsAsList().get(0);
        gameState.shuffleDeck();
        Assertions.assertNotEquals(topCard,
                gameState.getDrawDeck().getCardsAsList().get(0));
    }

    @Test
    public void testPlayerUsesSkip() {
        Queue<User> users = new LinkedList<>();
        users.add(new User("test1", true, new ArrayList<>()));
        users.add(new User("test2", true, new ArrayList<>()));
        GameDesigner gameDesigner = new GameDesigner(users, new JFrame());
        gameDesigner.initializeGameState();
        GamePlayer gamePlayer = gameDesigner.getGamePlayer();
        GameState gameState = gamePlayer.getGameState();
        User currentUser = gameState.getUserForCurrentTurn();
        currentUser.addCard(new Card(CardType.SKIP));
        int currentHandSize = currentUser.getHand().size();
        int currentDeckSize = gameState.getDeckSizeForCurrentTurn();
        Assertions.assertEquals(currentUser.getName(),
                "test1");
        currentUser.getHand().get(currentHandSize - 1)
                .activateEffect(gameState);
        Assertions.assertEquals(gameState.getUserForCurrentTurn().getName(),
                "test2");
        Assertions.assertEquals(currentUser.getHand().size(),
                currentHandSize - 1);
        Assertions.assertEquals(gameState.getDeckSizeForCurrentTurn(),
                currentDeckSize);
    }

}
