package System;

import Presentation.Gameboard;

import java.util.List;
import java.util.Queue;

public class GameState {
    private final Queue<User> playerQueue;
    private final Gameboard gameboard;

    public GameState(Queue<User> playerQueue, Gameboard gameboard) {
        this.playerQueue = playerQueue;
        this.gameboard = gameboard;
    }

    public void transitionToNextTurn() {
        if (playerQueue.size() < 2 || playerQueue.size() > 10) {
            throw new IllegalArgumentException("Illegal number of players in queue");
        }
        User userForCurrentTurn = playerQueue.poll();
        playerQueue.add(userForCurrentTurn);
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
}
