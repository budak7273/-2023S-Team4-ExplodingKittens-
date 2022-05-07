package system.cardEffects;

import system.GameState;

public class ShuffleEffect implements EffectPattern {
    @Override
    public void useEffect(GameState gameState) {
        gameState.shuffleDeck();
    }
}
