package System;

import java.util.Queue;

public class GameState {
    private final Queue<User> playerQueue;

    public GameState(Queue<User> playerQueue) {
        this.playerQueue = playerQueue;
    }

    public void transitionToNextTurn() {
        if (playerQueue.size() < 2 || playerQueue.size() > 10) {
            throw new IllegalArgumentException("Illegal number of players in queue");
        }
        User userForCurrentTurn = playerQueue.poll();
        playerQueue.add(userForCurrentTurn);
    }

    public User getUserForCurrentTurn() {
        return playerQueue.peek();
    }
}
