package system.cardEffects;

import datasource.CardType;
import datasource.Messages;
import system.Card;
import system.GameState;

public class ShuffleEffect implements EffectPattern {
    @Override
    public void useEffect(GameState gameState) {
        Card card = new Card(CardType.SHUFFLE, Messages.SHUFFLE_DESC);
        gameState.removeCardFromCurrentUser(card);
        gameState.shuffleDeck();
    }
}
