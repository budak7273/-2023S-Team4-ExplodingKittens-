package system.cardEffects;

import datasource.CardType;
import datasource.Messages;
import system.Card;
import system.GameState;

public class ShuffleEffect implements EffectPattern {
    @Override
    public void useEffect(GameState gameState) {
        gameState.shuffleDeck();
    }
}
