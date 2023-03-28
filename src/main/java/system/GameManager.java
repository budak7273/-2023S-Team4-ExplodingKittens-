package system;

import datasource.Messages;
import presentation.GamePlayer;

import java.util.List;
import java.util.Queue;
import java.util.Random;

public class GameManager {

    private final GameState gameState;
    private final GamePlayer gamePlayer;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 10;

    public GameManager(GameState state, GamePlayer player) {
        this.gameState = state;
        this.gamePlayer = player;
    }

    private void transitionToTurnOfUser(User targetUser) {
        Queue<User> playerQueue = gameState.getPlayerQueue();
        throwIfQueueSizeIsInvalid();

        User userAtTopOfQueue = playerQueue.peek();
        while (userAtTopOfQueue != targetUser) {
            userAtTopOfQueue = playerQueue.poll();
            playerQueue.add(userAtTopOfQueue);
            userAtTopOfQueue = playerQueue.peek();
        }

        gamePlayer.updateUI();

    }

    private void throwIfQueueSizeIsInvalid() {
        Queue<User> playerQueue = gameState.getPlayerQueue();
        if (playerQueue.size() < MIN_PLAYERS
                || playerQueue.size() > MAX_PLAYERS) {
            throw new IllegalArgumentException(
                    Messages.getMessage(Messages.ILLEGAL_PLAYERS));
        }
    }

    public void transitionToNextTurn() {
        Queue<User> playerQueue = gameState.getPlayerQueue();
        throwIfQueueSizeIsInvalid();

        if (gameState.getExtraTurnCountForCurrentUser() != 0) {
            gameState.removeExtraTurn();
        } else {
            User userForCurrentTurn = playerQueue.poll();
            if (userForCurrentTurn.isAlive()) {
                playerQueue.add(userForCurrentTurn);
            }
            while (!gameState.getUserForCurrentTurn().isAlive()) {
                playerQueue.poll();
            }
        }
        gameState.setPlayerQueue(playerQueue);
        gamePlayer.toggleCatMode();
        gamePlayer.updateUI();
        tryToEndGame();
    }

    public boolean tryToEndGame() {
        Queue<User> playerQueue = gameState.getPlayerQueue();
        if (playerQueue.size() < 1) {
            String msg = Messages.getMessage(Messages.ILLEGAL_PLAYERS);
            throw new IllegalArgumentException(msg);
        }
        if (playerQueue.size() == 1) {
            gamePlayer.displayWinForUser(gameState.getUserForCurrentTurn());
            return true;
        }
        return false;
    }

    public void drawCardForCurrentTurn() {
        User currentPlayer = gameState.getUserForCurrentTurn();
        boolean drawnExplodingKitten = gameState.getDrawDeck().drawCard(currentPlayer);
        if (drawnExplodingKitten) {
            checkExplodingKitten();
        } else {
            transitionToNextTurn();
        }
    }

    public void checkExplodingKitten() {
        gameState.getUserForCurrentTurn().attemptToDie();
        gamePlayer.explosionNotification(gameState.getUserForCurrentTurn().isAlive());
    }

    public void returnFutureCards(List<Card> future) {
        for (int i = future.size() - 1; i >= 0; i--) {
            Card replace = future.get(i);
            gameState.getDrawDeck().addCardToTop(replace);
        }
    }

    public void shuffleDeck(Boolean shuffled) {
        if (shuffled) {
            gamePlayer.updateUI();
        }
    }

    public void seeTheFuture(List<Card> futureCards) {
        gamePlayer.displayFutureCards(futureCards);
    }

    public void alterTheFuture(List<Card> futureCards) {
        gamePlayer.editFutureCards(futureCards);
    }

    public void addExplodingKittenBackIntoDeck(Integer location) {
        gameState.getDrawDeck().addExplodingKittenAtLocation(location);
        transitionToNextTurn();
    }

    public void triggerDisplayOfCatStealPrompt() {
        List<User> targets = gameState.getTargetsForCardEffects();
        gamePlayer.displayCatStealPrompt(targets);
    }

    public void triggerDisplayOfFavorPrompt(List<User> targets) {
        gamePlayer.displayFavorPrompt(targets);
    }

    public void executeTargetedAttackOn(User user) {
        transitionToTurnOfUser(user);
        gameState.addExtraTurn();
    }

    public void executeFavorOn(User user) {
        int i = gamePlayer.inputForStealCard(user);
        while (i == -1) {
            i = gamePlayer.inputForStealCard(user);
        }
        Card stealCard = user.removeHand(i);
        gameState.getUserForCurrentTurn().addCard(stealCard);
    }

    public void executeCatStealOn(User user, Random random) {
        int i = random.nextInt(user.getHand().size());
        Card stealCard = user.removeHand(i);
        gameState.getUserForCurrentTurn().addCard(stealCard);
    }

    public void triggerDisplayOfTargetedAttackPrompt(List<User> targets) {
        gamePlayer.displayTargetedAttackPrompt(targets);
    }

    public void removeCardFromCurrentUser(Card card) {
        User currentUser = gameState.getUserForCurrentTurn();
        currentUser.removeCard(card);
    }

    public GameState getGameState() {
        return gameState;
    }

    public Queue<User> getPlayerQueue() {
        return gameState.getPlayerQueue();
    }

    public User getUserForCurrentTurn() {
        return gameState.getUserForCurrentTurn();
    }

    public int getDeckSizeForCurrentTurn() {
        return gameState.getDeckSizeForCurrentTurn();
    }

    public DrawDeck getDrawDeck() {
        return gameState.getDrawDeck();
    }

    public void setCardExecutionState(int state) {
        gameState.setCardExecutionState(state);
    }

    public int getCardExecutionState() {
        return gameState.getCardExecutionState();
    }

}
