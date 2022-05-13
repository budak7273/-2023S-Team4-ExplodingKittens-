package system;

import datasource.CardType;
import datasource.Messages;
import system.cardEffects.*;

public class Card {
    private CardType cardType;
    private Messages description;

    public Card(CardType type, Messages desc) {
        this.cardType = type;
        this.description = desc;
    }

    public String getName() {
        return this.cardType.toString();
    }

    public CardType getType() {
        return this.cardType;
    }

    public void activateEffect(GameState gameState) {
        gameState.removeCardFromCurrentUser(new Card(cardType, description));
        cardType.getEffectPattern().useEffect(gameState);
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

    public String getDesc() {
        return Messages.getMessage(this.description);
    }
}
