package system;

import datasource.I18n;
import presentation.GameWindow;
import system.messages.EventMessage;

import java.util.List;
import java.util.Queue;
import java.util.Random;

import static system.Utils.forUsers;

public class GameManager {

    private final GameState gameState;
    private final GameWindow gameWindow;
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 10;

    public GameManager(GameState state, GameWindow player) {
        this.gameState = state;
        this.gameWindow = player;
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

        updateEventLogDisplay();
        gameWindow.updateUI();
    }

    private void updateEventLogDisplay() {

        String message =
                "Event Log contents for player: "
                + getUserForCurrentTurn().getName()
                + "\n" + gameState.getEventLogForCurrentTurn();
        gameWindow.updateEventHistoryLog(message);
    }

    public boolean checkCurrentUsersSpecialEffect() {
        return this.getUserForCurrentTurn().checkForCatCardEffects();
    }

    private void throwIfQueueSizeIsInvalid() {
        Queue<User> playerQueue = gameState.getPlayerQueue();
        if (playerQueue.size() < MIN_PLAYERS
            || playerQueue.size() > MAX_PLAYERS) {
            throw new IllegalArgumentException(I18n.getMessage("IllegalPlayersMessage"));
        }
    }

    public void executeSkip() {
        postMessage(EventMessage.publicMessage(
                String.format(I18n.getMessage("SkipPublic"), getUserForCurrentTurn().getName())));
        transitionToNextTurn();
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
            while (!getUserForCurrentTurn().isAlive()) {
                playerQueue.poll();
            }
        }
        gameState.setPlayerQueue(playerQueue);
        gameWindow.disableCatMode();

        updateEventLogDisplay();
        gameWindow.updateUI();
        tryToEndGame();
    }

    public boolean tryToEndGame() {
        Queue<User> playerQueue = gameState.getPlayerQueue();
        if (playerQueue.size() < 1) {
            String msg = I18n.getMessage("IllegalPlayersMessage");
            throw new IllegalArgumentException(msg);
        }
        if (playerQueue.size() == 1) {
            gameWindow.displayWinForUser(getUserForCurrentTurn());
            return true;
        }
        return false;
    }

    public boolean drawCardForCurrentTurn() {
        User current = getUserForCurrentTurn();
        boolean drawnExplodingKitten = gameState.getDrawDeck().drawCard(current);
        // TODO switch drawCard to return the card instead of wasExplodingKitten
        postMessage(new EventMessage(
                forUsers(current),
                String.format(I18n.getMessage("DrawCardPublic"), current.getName()),
                String.format(I18n.getMessage("DrawCardPrivate"), current.getLastCardInHand().getName())));
        if (drawnExplodingKitten) {
            current.attemptToDie();
            gameWindow.explosionNotification(current.isAlive());
        }
        return drawnExplodingKitten;
    }

    public void checkExplodingKitten() {
        getUserForCurrentTurn().attemptToDie();
        gameWindow.explosionNotification(getUserForCurrentTurn().isAlive());
    }

    public void returnFutureCards(List<Card> future) {
        for (int i = future.size() - 1; i >= 0; i--) {
            Card replace = future.get(i);
            gameState.getDrawDeck().addCardToTop(replace);
        }
    }

    public void shuffleDeck(Boolean shuffled) {
        if (shuffled) {
            gameWindow.updateUI();
            postMessage(EventMessage.publicMessage(
                    String.format(I18n.getMessage("ShufflePublic"), getUserForCurrentTurn().getName())));
        }
    }

    public void executeSeeTheFuture(List<Card> cards) {
        postMessage(EventMessage.publicMessage(
                String.format(I18n.getMessage("SeeTheFuturePublic"), getUserForCurrentTurn().getName())));
        gameWindow.displayFutureCards(cards);
    }

    public void executeSeeTheEnd(List<Card> cards) {
        postMessage(EventMessage.publicMessage(
                String.format(I18n.getMessage("SeeTheEndPublic"), getUserForCurrentTurn().getName())));
        gameWindow.displayFutureCards(cards);
    }

    public void executeAlterTheFuture(List<Card> futureCards) {
        postMessage(EventMessage.publicMessage(
                String.format(I18n.getMessage("AlterFuturePublic"), getUserForCurrentTurn().getName())));
        gameWindow.editFutureCards(futureCards);
    }

    public void executeAttack() {
        User attacker = getUserForCurrentTurn();
        transitionToNextTurn();
        User target = getUserForCurrentTurn();
        postMessage(new EventMessage(
                forUsers(target),
                String.format(I18n.getMessage("AttackPublic"), attacker.getName(), target.getName()),
                String.format(I18n.getMessage("AttackPrivate"), attacker.getName())));
        gameState.addExtraTurn();
    }

    public void addExplodingKittenBackIntoDeck(Integer location) {
        gameState.getDrawDeck().addExplodingKittenAtLocation(location);
        transitionToNextTurn();
    }

    // TODO this method is only used by tests
    public void addCardToDeck(Card card) {
        gameState.getDrawDeck().addCardToTop(card);
    }

    public void triggerDisplayOfCatStealPrompt() {
        List<User> targets = gameState.getTargetsForCardEffects();
        gameWindow.displayCatStealPrompt(targets);
    }

    public void triggerDisplayOfFavorPrompt(List<User> targets) {
        gameWindow.displayFavorPrompt(targets);
    }

    public void executeTargetedAttackOn(User target) {
        User attacker = getUserForCurrentTurn();
        postMessage(new EventMessage(
                forUsers(target),
                String.format(I18n.getMessage("TargetedAttackPublic"), attacker.getName(), target.getName()),
                String.format(I18n.getMessage("TargetedAttackPrivate"), attacker.getName())));
        transitionToTurnOfUser(target);
        gameState.addExtraTurn();
    }

    public void executeFavorOn(User target) {
        int i = gameWindow.inputForStealCard(target);
        while (i == -1) {
            i = gameWindow.inputForStealCard(target);
        }
        Card stealCard = target.getCardFromHand(i);
        target.removeCard(stealCard);
        User current = getUserForCurrentTurn();
        current.addCard(stealCard);
        postMessage(new EventMessage(
                forUsers(current, target),
                String.format(I18n.getMessage("FavorPublic"), current.getName(), target.getName()),
                String.format(I18n.getMessage("FavorPrivate"), current.getName(), stealCard.getName(),
                              target.getName())));
    }


    public void executeCatStealOn(User target, Random random) {
        User attacker = getUserForCurrentTurn();
        int i = random.nextInt(target.getHand().size());
        Card stealCard = target.getCardFromHand(i);
        target.removeCard(stealCard);
        attacker.addCard(stealCard);
        postMessage(new EventMessage(
                forUsers(attacker, target),
                String.format(I18n.getMessage("CatComboPublic"),
                              attacker.getName(), target.getName()),
                String.format(I18n.getMessage("CatComboPrivate"),
                              attacker.getName(), stealCard.getName(), target.getName())));
    }

    public void triggerDisplayOfTargetedAttackPrompt(List<User> targets) {
        gameWindow.displayTargetedAttackPrompt(targets);
    }

    public void removeCardFromCurrentUser(Card card) {
        getUserForCurrentTurn().removeCard(card);
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

    public void postMessage(EventMessage message) {
        gameState.postMessage(message);
        updateEventLogDisplay();
    }
}
