package datasource;

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

    CardType(String displayNameStr) {
        this.displayName = displayNameStr;
    }

    @Override
    public String toString() {
        return this.displayName;
    }

}
