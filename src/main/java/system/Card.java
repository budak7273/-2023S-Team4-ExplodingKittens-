package system;

import datasource.CardType;

public class Card {
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
}
