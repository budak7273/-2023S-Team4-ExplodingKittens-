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
                break;
            case TARGETED_ATTACK:
                this.description =  Messages.TARGETED_ATTACK;
                break;
            case SEE_THE_FUTURE:
                this.description =  Messages.SEE_DESC;
                break;
            case NOPE:
                this.description =  Messages.NOPE_DESC;
                break;
            case SKIP:
                this.description =  Messages.SKIP_DESC;
                break;
            case FAVOR:
                this.description =  Messages.FAVOR_DESC;
                break;
            case ATTACK:
                this.description =  Messages.ATTACK_DESC;
                break;
            case DEFUSE:
                this.description =  Messages.DEFUSE_DESC;
                break;
            case SHUFFLE:
                this.description =  Messages.SHUFFLE_DESC;
                break;
            case TACO_CAT:
                this.description =  Messages.TACO_CAT_DESC;
                break;
            case BEARD_CAT:
                this.description =  Messages.BEARD_CAT_DESC;
                break;
            case FERAL_CAT:
                this.description =  Messages.FERAL_CAT_DESC;
                break;
            case CATTERMELON:
                this.description =  Messages.CATTERMELON_DESC;
                break;
            case EXPLODING_KITTEN:
                this.description =  Messages.EXPLODING_DESC;
                break;
            case HAIRY_POTATO_CAT:
                this.description =  Messages.HAIRY_POTATO_CAT_DESC;
                break;
            case DRAW_FROM_THE_BOTTOM:
                this.description =  Messages.DRAW_FROM_BOTTOM_DESC;
                break;
            case RAINBOW_RALPHING_CAT:
                this.description =  Messages.RAINBOW_CAT_DESC;
                break;
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
