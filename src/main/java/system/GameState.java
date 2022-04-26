package system;

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
                    "Illegal number of players in queue");
        }
        User userForCurrentTurn = playerQueue.poll();
        if(userForCurrentTurn.isAlive()) {
            playerQueue.add(userForCurrentTurn);
        }
        while(!getUserForCurrentTurn().isAlive()) {
            playerQueue.poll();
        }

        gameboard.updateUI();
    }

    public void dealHands(DrawDeck deck) {
        if (playerQueue.size() < 2 || playerQueue.size() > 10) {
            throw new IllegalArgumentException("Illegal number of players in queue");
        }

        int cardsToDraw = 7;
        for (int i=0; i<cardsToDraw; i++) {
            for (User player: playerQueue) {
                deck.drawInitialCard(player);
            }
        }
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
