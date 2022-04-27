package datasource;

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

    private final String displayName;

    CardType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return this.displayName;
    }

}
