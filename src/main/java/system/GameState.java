package system;

import java.util.*;

public class GameState {
    private Queue<User> playerQueue;
    private final DrawDeck drawDeck;
    private int extraTurnsForCurrentUser = 0;
    private int cardExecutionState = -1;

    public GameState(Queue<User> pq, DrawDeck deck) {
        Queue<User> pqCopy = new LinkedList<>();
        pqCopy.addAll(pq);
        this.playerQueue = pqCopy;
        this.drawDeck = deck;
    }

    public User getUserForCurrentTurn() {
        return playerQueue.peek();
    }

    public int getDeckSizeForCurrentTurn() {
        return drawDeck.getDeckSize();
    }

    public Queue<User> getPlayerQueue() {
        return playerQueue;
    }

    public void setPlayerQueue(Queue<User> pq) {
        this.playerQueue = pq;
    }

    public DrawDeck getDrawDeck() {
        return this.drawDeck;
    }

    public void setCardExecutionState(int state) {
        this.cardExecutionState = state;
    }

    public int getCardExecutionState() {
        return this.cardExecutionState;
    }

    public void addExtraTurn() {
        extraTurnsForCurrentUser++;
    }

    public void removeExtraTurn() {
        extraTurnsForCurrentUser--;
    }

    public int getExtraTurnCountForCurrentUser() {
        return extraTurnsForCurrentUser;
    }

    public List<User> getTargetsForCardEffects() {
        List<User> targets = new ArrayList<>();
        targets.addAll(playerQueue);
        targets.remove(getUserForCurrentTurn());
        return targets;
    }

}
