package datasource;

import system.cardEffects.*;

public enum CardType {

    ATTACK(Messages.getMessage(Messages.ATTACK_CARD),
            new AttackEffect(),
            Messages.ATTACK_DESC),
    EXPLODING_KITTEN(Messages.getMessage(Messages.EXPLODING_KITTEN_CARD),
            null,
            Messages.EXPLODING_DESC),
    DEFUSE(Messages.getMessage(Messages.DEFUSE_CARD),
            null,
            Messages.DEFUSE_DESC),
    SKIP(Messages.getMessage(Messages.SKIP_CARD),
            new SkipEffect(),
            Messages.SKIP_DESC),
    FAVOR(Messages.getMessage(Messages.FAVOR_CARD),
            new FavorEffect(),
            Messages.FAVOR_DESC),
    SHUFFLE(Messages.getMessage(Messages.SHUFFLE_CARD),
            new ShuffleEffect(),
            Messages.SHUFFLE_DESC),
    BEARD_CAT(Messages.getMessage(Messages.BEARD_CAT_CARD),
            null,
            Messages.BEARD_CAT_DESC),
    TACO_CAT(Messages.getMessage(Messages.TACO_CAT_CARD),
            null,
            Messages.TACO_CAT_DESC),
    HAIRY_POTATO_CAT(Messages.getMessage(Messages.HAIRY_POTATO_CAT),
            null,
            Messages.HAIRY_POTATO_CAT_DESC),
    RAINBOW_RALPHING_CAT(Messages.getMessage(Messages.RAINBOW_RALPHING_CAT),
            null,
            Messages.RAINBOW_CAT_DESC),
    CATTERMELON(Messages.getMessage(Messages.CATTERMELON),
            null,
            Messages.CATTERMELON_DESC),
    FERAL_CAT(Messages.getMessage(Messages.FERAL_CAT),
            null,
            Messages.FERAL_CAT_DESC),
    DRAW_FROM_THE_BOTTOM(Messages.getMessage(Messages.DRAW_FROM_THE_BOTTOM),
            new DrawFromBottomEffect(),
            Messages.DRAW_FROM_BOTTOM_DESC),
    NOPE(Messages.getMessage(Messages.NOPE),
            null,
            Messages.NOPE_DESC),
    ALTER_THE_FUTURE(Messages.getMessage(Messages.ALTER_THE_FUTURE),
            new AlterTheFutureEffect(),
            Messages.ALTER_DESC),
    TARGETED_ATTACK(Messages.getMessage(Messages.TARGETED_ATTACK),
            new TargetedAttackEffect(),
            Messages.TARGETED_ATTACK_DESC),
    SEE_THE_FUTURE(Messages.getMessage(Messages.SEE_THE_FUTURE),
            new SeeTheFutureEffect(),
            Messages.SEE_DESC);

    private String displayName;
    private EffectPattern effectPattern;
    private Messages description;

    CardType(String displayNameStr, EffectPattern pattern, Messages desc) {
        this.displayName = displayNameStr;
        this.effectPattern = pattern;
        this.description = desc;
    }

    public EffectPattern getEffectPattern() {
        return this.effectPattern;
    }

    public String getDescription() {
        return Messages.getMessage(description);
    }

    @Override
    public String toString() {
        return this.displayName;
    }

}
