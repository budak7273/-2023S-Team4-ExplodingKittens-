package system;

import datasource.CardType;
import system.cardEffects.*;

public class Card {
    private CardType cardType;
    private EffectPattern effectPattern;

    public Card(CardType type) {
        this.cardType = type;

        switch (type) {

            case ATTACK -> {
                this.effectPattern = new AttackEffect();
            }
            case EXPLODING_KITTEN -> {
            }
            case DEFUSE -> {
                this.effectPattern = new DefuseBombEffect();
            }
            case SKIP -> {
                this.effectPattern = new SkipEffect();
            }
            case FAVOR -> {
            }
            case SHUFFLE -> {
                this.effectPattern = new ShuffleEffect();
            }
            case BEARD_CAT -> {
            }
            case TACO_CAT -> {
            }
            case HAIRY_POTATO_CAT -> {
            }
            case RAINBOW_RALPHING_CAT -> {
            }
            case CATTERMELON -> {
            }
            case FERAL_CAT -> {
            }
            case DRAW_FROM_THE_BOTTOM -> {
                this.effectPattern = new DrawFromBottomEffect();
            }
            case NOPE -> {
            }

            case ALTER_THE_FUTURE -> {
            }
            case TARGETED_ATTACK -> {
            }
            case SEE_THE_FUTURE -> {
                this.effectPattern = new SeeTheFutureEffect();
            }
        }
    }

    public String getName() {
        return this.cardType.toString();
    }

    public CardType getType() {
        return this.cardType;
    }

    public EffectPattern getEffectPattern() {
        return effectPattern;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Card)) {
            return false;
        }
        return cardType.equals(((Card) o).cardType);
    }

    @Override
    public int hashCode() {
        return cardType.ordinal();
    }

    public boolean isCatCard() {
        if (this.cardType == CardType.HAIRY_POTATO_CAT
                || this.cardType == CardType.BEARD_CAT
                || this.cardType == CardType.CATTERMELON
                || this.cardType == CardType.TACO_CAT
                || this.cardType == CardType.RAINBOW_RALPHING_CAT
                || this.cardType == CardType.FERAL_CAT) {
            return true;
        }
        return false;
    }
}
