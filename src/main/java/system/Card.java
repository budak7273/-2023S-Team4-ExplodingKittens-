package system;

import datasource.CardType;

import javax.swing.Icon;

public class Card {
    private CardType cardType;
    private Icon icon;

    public Card(CardType type, Icon imageIcon) {
        this.cardType = type;
        this.icon = imageIcon;
    }
    public Card(CardType type) {
        this.cardType = type;
        this.icon = null;
    }

    public Icon getIcon() {
        return this.icon;
    }

    public String getName() {
        return this.cardType.toString();
    }

    public CardType getType() {
        return this.cardType;
    }

    public void activateEffect(GameManager gameManager) {
        gameManager.removeCardFromCurrentUser(new Card(cardType, null));
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
