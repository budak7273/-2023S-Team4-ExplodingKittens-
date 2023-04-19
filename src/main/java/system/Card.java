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

    public void activateEffect(GameManager gameManager) {
        gameManager.removeCardFromCurrentUser(new Card(cardType));
        cardType.getEffectPattern().setCurrentState(gameManager);
        cardType.getEffectPattern().useEffect();
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
        return this.cardType.getIsCatCard();
    }

    public String getDesc() {
        return this.cardType.getDescription();
    }
}
