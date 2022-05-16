package system.cardEffects;

import system.GameState;

public class FavorEffect implements EffectPattern {

    @Override
    public void useEffect(GameState gameState) {
        gameState.stealTheFavor();
    }
}
