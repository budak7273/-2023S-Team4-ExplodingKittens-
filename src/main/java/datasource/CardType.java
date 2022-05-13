package datasource;

import system.cardEffects.*;

public enum CardType {

    ATTACK(Messages.getMessage(Messages.ATTACK_CARD),
            new AttackEffect()),
    EXPLODING_KITTEN(Messages.getMessage(Messages.EXPLODING_KITTEN_CARD),
            null),
    DEFUSE(Messages.getMessage(Messages.DEFUSE_CARD),
            null),
    SKIP(Messages.getMessage(Messages.SKIP_CARD),
            new SkipEffect()),
    FAVOR(Messages.getMessage(Messages.FAVOR_CARD),
            null),
    SHUFFLE(Messages.getMessage(Messages.SHUFFLE_CARD),
            new ShuffleEffect()),
    BEARD_CAT(Messages.getMessage(Messages.BEARD_CAT_CARD),
            null),
    TACO_CAT(Messages.getMessage(Messages.TACO_CAT_CARD),
            null),
    HAIRY_POTATO_CAT(Messages.getMessage(Messages.HAIRY_POTATO_CAT),
            null),
    RAINBOW_RALPHING_CAT(Messages.getMessage(Messages.RAINBOW_RALPHING_CAT),
            null),
    CATTERMELON(Messages.getMessage(Messages.CATTERMELON),
            null),
    FERAL_CAT(Messages.getMessage(Messages.FERAL_CAT),
            null),
    DRAW_FROM_THE_BOTTOM(Messages.getMessage(Messages.DRAW_FROM_THE_BOTTOM),
            new DrawFromBottomEffect()),
    NOPE(Messages.getMessage(Messages.NOPE),
            null),
    ALTER_THE_FUTURE(Messages.getMessage(Messages.ALTER_THE_FUTURE),
            new AlterTheFutureEffect()),
    TARGETED_ATTACK(Messages.getMessage(Messages.TARGETED_ATTACK),
            null),
    SEE_THE_FUTURE(Messages.getMessage(Messages.SEE_THE_FUTURE),
            new SeeTheFutureEffect());

    private String displayName;
    private EffectPattern effectPattern;

    CardType(String displayNameStr, EffectPattern pattern) {
        this.displayName = displayNameStr;
        this.effectPattern = pattern;
    }

    public EffectPattern getEffectPattern() {
        return this.effectPattern;
    }

    @Override
    public String toString() {
        return this.displayName;
    }

}
