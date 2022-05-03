package system;

import datasource.CardType;

public abstract class Card {
    private CardType cardType;

    public Card(CardType type) {
        this.cardType = type;
    }

    public String getName() {
        return this.cardType.toString();
    }

    public CardType getType() {
        return this.cardType;
    }
}
