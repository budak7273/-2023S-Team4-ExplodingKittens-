package system;

import datasource.CardType;
import datasource.Messages;

public class Card {
    private CardType cardType;
    private Messages description;

    public Card(CardType type) {
        this.cardType = type;
        this.setDescription();
    }
    public void setDescription() {
        switch (this.cardType) {
            case ALTER_THE_FUTURE:
                this.description =  Messages.ALTER_DESC;
            case TARGETED_ATTACK:
                this.description =  Messages.TARGETED_ATTACK;
            case SEE_THE_FUTURE:
                this.description =  Messages.SEE_DESC;
            case NOPE:
                this.description =  Messages.NOPE_DESC;
            case SKIP:
                this.description =  Messages.SKIP_DESC;
            case FAVOR:
                this.description =  Messages.FAVOR_DESC;
            case ATTACK:
                this.description =  Messages.ATTACK_DESC;
            case DEFUSE:
                this.description =  Messages.DEFUSE_DESC;
            case SHUFFLE:
                this.description =  Messages.SHUFFLE_DESC;
            case TACO_CAT:
                this.description =  Messages.TACO_CAT_DESC;
            case BEARD_CAT:
                this.description =  Messages.BEARD_CAT_DESC;
            case FERAL_CAT:
                this.description =  Messages.FERAL_CAT_DESC;
            case CATTERMELON:
                this.description =  Messages.CATTERMELON_DESC;
            case EXPLODING_KITTEN:
                this.description =  Messages.EXPLODING_DESC;
            case HAIRY_POTATO_CAT:
                this.description =  Messages.HAIRY_POTATO_CAT_DESC;
            case DRAW_FROM_THE_BOTTOM:
                this.description =  Messages.DRAW_FROM_BOTTOM_DESC;
            case RAINBOW_RALPHING_CAT:
                this.description =  Messages.RAINBOW_CAT_DESC;
            default:
                this.description =  Messages.EMPTY_DESC;
        }
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
