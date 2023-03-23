package system.cardEffects;

import system.Card;
import java.util.List;

public class SeeTheFutureEffect extends EffectPattern {

    @Override
    public void useEffect() {
        List<Card> futureCards = drawDeck.drawThreeCardsFromTop();
        currentState.seeTheFuture(futureCards);
    }
}
