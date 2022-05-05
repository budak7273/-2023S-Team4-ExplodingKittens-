package system.cardEffects;

import system.GameState;

public class SeeTheFutureEffect implements EffectPattern {
    @Override
    public void useEffect(GameState gameState) {
        gameState.seeTheFuture();
    }
}
