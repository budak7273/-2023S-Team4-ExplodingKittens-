package system.cardEffects;

import system.DrawDeck;
import system.GameManager;
import system.GameState;
import system.User;

public abstract class EffectPattern {

    private User currentUser;
    private GameState currentState;
    private DrawDeck drawDeck;
    private GameManager gameManager;

    public void useEffect() { };

    public void setCurrentState(GameState state) {
        currentState = state;
        currentUser = currentState.getUserForCurrentTurn();
        drawDeck = currentState.getDrawDeck();
    }

    protected User getCurrentUser() {
        return currentUser;
    }

    protected GameState getCurrentState() {
        return currentState;
    }

    protected DrawDeck getDrawDeck() {
        return drawDeck;
    }
}
