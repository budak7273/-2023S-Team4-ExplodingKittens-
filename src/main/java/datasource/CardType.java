package datasource;

import system.cardEffects.*;

public enum CardType {

    ATTACK("Attack"),
    EXPLODING_KITTEN("Exploding Kitten"),
    DEFUSE("Defuse"),
    SKIP("Skip"),
    FAVOR("Favor"),
    SHUFFLE("Shuffle"),
    BEARD_CAT("Beard Cat"),
    TACO_CAT("Taco Cat"),
    HAIRY_POTATO_CAT("Hairy Potato Cat"),
    RAINBOW_RALPHING_CAT("Rainbow-Ralphing Cat"),
    CATTERMELON("Cattermelon"),
    FERAL_CAT("Feral Cat"),
    DRAW_FROM_THE_BOTTOM("Draw From The Bottom"),
    NOPE("Nope"),
    ALTER_THE_FUTURE("Alter The Future"),
    TARGETED_ATTACK("Targeted Attack"),
    SEE_THE_FUTURE("See The Future");

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

    @Override
    public String toString() {
        return this.displayName;
    }

}
