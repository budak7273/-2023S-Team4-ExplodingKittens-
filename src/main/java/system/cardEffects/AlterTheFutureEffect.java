package system.cardEffects;

import system.Card;

import java.util.List;

public class AlterTheFutureEffect extends EffectPattern {

    @Override
    public void useEffect() {
        List<Card> futureCards = getDrawDeck().drawThreeCardsFromTop();
        getGameManager().alterTheFuture(futureCards);
    }
}
