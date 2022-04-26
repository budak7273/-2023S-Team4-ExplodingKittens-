package system;

import datasource.CardType;

public abstract class Card {
    public CardType cardType;

    public Card(CardType cardType) {
        this.cardType = cardType;
    }

    public String getName() {
        return this.cardType.toString();
    }
}
