package system.cardEffects;

import system.GameState;

public class AlterTheFutureEffect implements EffectPattern {
    @Override
    public void useEffect(GameState gameState) {
        gameState.alterTheFuture();
    }
}
