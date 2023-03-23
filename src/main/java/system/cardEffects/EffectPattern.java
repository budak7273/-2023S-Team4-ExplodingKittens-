package system.cardEffects;

import system.DrawDeck;
import system.GameManager;
import system.GameState;
import system.User;

public abstract class EffectPattern {

    protected User currentUser;
    protected GameState currentState;
    protected DrawDeck drawDeck;
    protected GameManager gameManager;

    public void useEffect() {};

    public void setCurrentState(GameState state) {
        currentState = state;
        currentUser = currentState.getUserForCurrentTurn();
        drawDeck = currentState.getDrawDeck();
    }
}
