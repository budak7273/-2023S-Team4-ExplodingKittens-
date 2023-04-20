package system.cardEffects;

import system.Card;

import java.util.List;

public class SeeTheEndEffect extends EffectPattern {

    @Override
    public void useEffect() {
        List<Card> bottomCards = getDrawDeck().drawThreeCardsFromBottom();
        getGameManager().executeSeeTheEnd(bottomCards);
    }
}
