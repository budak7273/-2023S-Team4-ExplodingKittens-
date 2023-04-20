package system.cardEffects;

import system.GameManager;
import system.messages.EventMessage;

public class ShuffleEffect extends EffectPattern {

    @Override
    public void useEffect() {
        Boolean shuffled = getDrawDeck().shuffle();
        getGameManager().shuffleDeck(shuffled);
    }
}
