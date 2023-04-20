package system;

import system.messages.EventLog;
import system.messages.EventMessage;

import java.util.*;

public class GameState {
    private Queue<User> playerQueue;
    private final DrawDeck drawDeck;
    private int extraTurnsForCurrentUser = 0;
    private int cardExecutionState = -1;
    private final EventLog eventLog;

    public GameState(Queue<User> pq, DrawDeck deck) {
        this.playerQueue = new LinkedList<>(pq);
        this.drawDeck = deck;
        this.eventLog = new EventLog();
    }

    public User getUserForCurrentTurn() {
        return playerQueue.peek();
    }

    public String getEventLogForCurrentTurn() {
        return eventLog.getTextRepresentationAs(getUserForCurrentTurn());
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
        List<User> targets = new ArrayList<>(playerQueue);
        targets.remove(getUserForCurrentTurn());
        return targets;
    }
    public void postMessage(EventMessage message) {
        eventLog.addMessage(message);
    }

}
