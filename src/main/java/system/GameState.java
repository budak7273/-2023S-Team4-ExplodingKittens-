package system;

import datasource.Messages;
import presentation.Gameboard;

import java.util.List;
import java.util.Queue;

public class GameState {
    private final Queue<User> playerQueue;
    private final Gameboard gameboard;
    private final int minPlayers = 2;
    private final int maxPlayers = 10;

    public GameState(final Queue<User> pq, final Gameboard g) {
        this.playerQueue = pq;
        this.gameboard = g;
    }

    public void transitionToNextTurn() {
        if (playerQueue.size() < minPlayers
                || playerQueue.size() > maxPlayers) {
            throw new IllegalArgumentException(
                    Messages.getMessage("IllegalPlayersMessage"));
        }
        User userForCurrentTurn = playerQueue.poll();
        if (userForCurrentTurn.isAlive()) {
            playerQueue.add(userForCurrentTurn);
        }
        while (!getUserForCurrentTurn().isAlive()) {
            playerQueue.poll();
        }

        gameboard.updateUI();
    }

    public User getUserForCurrentTurn() {
        return playerQueue.peek();
    }

    public String getUsernameForCurrentTurn() {
        return getUserForCurrentTurn().getName();
    }

    public List<Card> getDeckForCurrentTurn() {
        return getUserForCurrentTurn().getHand();
    }

    public Queue<User> getPlayerQueue() {
        return this.playerQueue;
    }


}
