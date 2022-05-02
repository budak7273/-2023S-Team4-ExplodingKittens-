package system;

import datasource.CardType;

public abstract class Card {
    private CardType cardType;

    public Card(final CardType type) {
        this.cardType = type;
    }

    public String getName() {
        return this.cardType.toString();
    }

    public CardType getType() {
        return this.cardType;
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
