package datasource;

import system.cardEffects.*;

public enum CardType {

    ATTACK(Messages.getMessage(Messages.ATTACK_CARD)),
    EXPLODING_KITTEN(Messages.getMessage(Messages.EXPLODING_KITTEN_CARD)),
    DEFUSE(Messages.getMessage(Messages.DEFUSE_CARD)),
    SKIP(Messages.getMessage(Messages.SKIP_CARD)),
    FAVOR(Messages.getMessage(Messages.FAVOR_CARD)),
    SHUFFLE(Messages.getMessage(Messages.SHUFFLE_CARD)),
    BEARD_CAT(Messages.getMessage(Messages.BEARD_CAT_CARD)),
    TACO_CAT(Messages.getMessage(Messages.TACO_CAT_CARD)),
    HAIRY_POTATO_CAT(Messages.getMessage(Messages.HAIRY_POTATO_CAT)),
    RAINBOW_RALPHING_CAT(Messages.getMessage(Messages.RAINBOW_RALPHING_CAT)),
    CATTERMELON(Messages.getMessage(Messages.CATTERMELON)),
    FERAL_CAT(Messages.getMessage(Messages.FERAL_CAT)),
    DRAW_FROM_THE_BOTTOM(Messages.getMessage(Messages.DRAW_FROM_THE_BOTTOM)),
    NOPE(Messages.getMessage(Messages.NOPE)),
    ALTER_THE_FUTURE(Messages.getMessage(Messages.ALTER_THE_FUTURE)),
    TARGETED_ATTACK(Messages.getMessage(Messages.TARGETED_ATTACK)),
    SEE_THE_FUTURE(Messages.getMessage(Messages.SEE_THE_FUTURE));

    private String displayName;
    private EffectPattern effectPattern;

    CardType(String displayNameStr) {
        this.displayName = displayNameStr;

    }

    public EffectPattern getEffectPattern() {
        switch (this) {
            case TARGETED_ATTACK:
            case ATTACK:
                this.effectPattern = new AttackEffect();
                break;
            case EXPLODING_KITTEN:

            case DEFUSE:
                this.effectPattern = new DefuseBombEffect();
                break;
            case SKIP:
                this.effectPattern = new SkipEffect();
                break;
            case FAVOR:
            case SHUFFLE:
                this.effectPattern = new ShuffleEffect();
                break;
            case BEARD_CAT:
            case TACO_CAT:
            case HAIRY_POTATO_CAT:
            case RAINBOW_RALPHING_CAT:
            case CATTERMELON:
            case FERAL_CAT:
            case DRAW_FROM_THE_BOTTOM:
                this.effectPattern = new DrawFromBottomEffect();
                break;
            case NOPE:
            case ALTER_THE_FUTURE:
                this.effectPattern = new AlterTheFutureEffect();
                break;
            case SEE_THE_FUTURE:
                this.effectPattern = new SeeTheFutureEffect();
                break;
            default:
        }

        return this.effectPattern;
    }

    @Override
    public String toString() {
        return this.displayName;
    }

}
