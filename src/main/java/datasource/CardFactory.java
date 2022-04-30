package datasource;

import system.cards.*;
import system.Card;

final class CardFactory {

    private CardFactory() {
    }

    public static Card createCardOfType(final CardType type) {
        switch (type) {
            case ATTACK:
                return new AttackCard();
            case EXPLODING_KITTEN:
                return new ExplodingCard();
            case DEFUSE:
                return new DefuseCard();
            case SKIP:
                return new SkipCard();
            case FAVOR:
                return new FavorCard();
            case SHUFFLE:
                return new ShuffleCard();
            case BEARD_CAT:
                return new BeardCatCard();
            case TACO_CAT:
                return new TacoCatCard();
            case HAIRY_POTATO_CAT:
                return new HairyPotatoCatCard();
            case RAINBOW_RALPHING_CAT:
                return new RainbowRalphingCatCard();
            case CATTERMELON:
                return new CattermelonCard();
            case FERAL_CAT:
                return new FeralCatCard();
            case DRAW_FROM_THE_BOTTOM:
                return new DrawFromTheBottomCard();
            case NOPE:
                return new NopeCard();
            case ALTER_THE_FUTURE:
                return new AlterTheFutureCard();
            case TARGETED_ATTACK:
                return new TargetedAttackCard();
            case SEE_THE_FUTURE:
                return new SeeTheFutureCard();
            default:
                throw new IllegalArgumentException(Messages
                        .getMessage("CouldNotCreateCardMessage"));
        }
    }
}
