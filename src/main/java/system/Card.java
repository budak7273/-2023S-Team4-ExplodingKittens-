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

    public void activateEffect(GameState gameState) {
        gameState.removeCardFromCurrentUser(new Card(cardType));
        cardType.getEffectPattern().useEffect(gameState);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Card) {
            return cardType.equals(((Card) o).cardType);
        }
       return false;
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

    public String getDesc() {
        return cardType.getDescription();
    }
}
