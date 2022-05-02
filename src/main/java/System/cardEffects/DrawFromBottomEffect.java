package system.cardEffects;

import system.GameState;

public class DrawFromBottomEffect implements EffectPattern {
    @Override
    public void useEffect(final GameState gameState) {
        gameState.drawFromBottom();
    }
}
