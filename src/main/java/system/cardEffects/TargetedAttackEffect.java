package system.cardEffects;

import system.GameState;

public class TargetedAttackEffect implements EffectPattern {
    @Override
    public void useEffect(GameState gameState) {
        gameState.beginTargetedAttack();
    }
}
