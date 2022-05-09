package system.cardEffects;

import system.GameState;

public class AttackEffect implements EffectPattern {

    public void useEffect(GameState gameState) {
        gameState.transitionToNextTurn();
        gameState.addExtraTurn();
    }

}
