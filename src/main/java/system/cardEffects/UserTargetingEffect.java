package system.cardEffects;

import system.User;

public abstract class UserTargetingEffect extends EffectPattern {
    public abstract Void applyToUser(User target);
}
