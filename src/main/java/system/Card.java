package system;

import datasource.CardType;

public class Card {
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Card)) return false;
        return cardType.equals(((Card) o).cardType);
    }
}
