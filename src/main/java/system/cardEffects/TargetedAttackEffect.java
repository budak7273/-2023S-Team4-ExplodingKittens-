package system.cardEffects;

import system.User;
import java.util.List;

public class TargetedAttackEffect extends EffectPattern {

    @Override
    public void useEffect() {
        List<User> targets = currentState.getTargetsForCardEffects();
        currentState.triggerDisplayOfTargetedAttackPrompt(targets);
    }
}
