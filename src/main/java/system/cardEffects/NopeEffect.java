package system.cardEffects;

import system.GameState;

public class NopeEffect implements EffectPattern {
    @Override
    public void useEffect(GameState gameState) {
        gameState.nope();
    }
}
