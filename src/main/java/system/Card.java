package system;

import datasource.CardType;

public abstract class Card {
    private final String cardName;
    public CardType cardType;

    public Card(final String name) {
        this.cardName = name;
    }

    public String getName() {
        return this.cardName;
    }
}
