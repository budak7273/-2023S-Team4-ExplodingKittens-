package System;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class CardEffectTesting {
    @Test
    public void testDefuseBombEffectUse() {
        EffectPattern bombEffectPattern = new DefuseBombEffect();
        Executable executable = bombEffectPattern::useEffect;
        Assertions.assertDoesNotThrow(executable);
    }

    @Test
    public void testAttackEffectUse() {
        EffectPattern bombEffectPattern = new AttackEffect();
        Executable executable = bombEffectPattern::useEffect;
        Assertions.assertDoesNotThrow(executable);
    }
}
