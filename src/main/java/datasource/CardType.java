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
            case ATTACK:
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
            case TARGETED_ATTACK:
            case SEE_THE_FUTURE:
                this.effectPattern = new SeeTheFutureEffect();
            default:
        }

        return this.effectPattern;
    }
    public static Messages getDescription(CardType type) {
        switch (type){
            case ALTER_THE_FUTURE:
                return Messages.ALTER_DESC;
            case TARGETED_ATTACK:
                return Messages.TARGETED_ATTACK;
            case SEE_THE_FUTURE:
                return Messages.SEE_DESC;
            case NOPE:
                return Messages.NOPE_DESC;
            case SKIP:
                return Messages.SKIP_DESC;
            case FAVOR:
                return Messages.FAVOR_DESC;
            case ATTACK:
                return Messages.ATTACK_DESC;
            case DEFUSE:
                return Messages.DEFUSE_DESC;
            case SHUFFLE:
                return Messages.SHUFFLE_DESC;
            case TACO_CAT:
                return Messages.TACO_CAT_DESC;
            case BEARD_CAT:
                return Messages.BEARD_CAT_DESC;
            case FERAL_CAT:
                return Messages.FERAL_CAT_DESC;
            case CATTERMELON:
                return Messages.CATTERMELON_DESC;
            case EXPLODING_KITTEN:
                return Messages.EXPLODING_DESC;
            case HAIRY_POTATO_CAT:
                return Messages.HAIRY_POTATO_CAT_DESC;
            case DRAW_FROM_THE_BOTTOM:
                return Messages.DRAW_FROM_BOTTOM_DESC;
            case RAINBOW_RALPHING_CAT:
                return Messages.RAINBOW_CAT_DESC;
            default:
                return Messages.EMPTY_DESC;
        }
    }

    @Override
    public String toString() {
        return this.displayName;
    }

}
