package system.cardEffects;

import datasource.CardType;
import datasource.Messages;
import system.Card;
import system.GameState;

public class SkipEffect implements EffectPattern {
    @Override
    public void useEffect(GameState gameState) {
        gameState.removeCardFromCurrentUser(new Card(CardType.SKIP, Messages.SKIP_DESC));
        gameState.transitionToNextTurn();
    }
}
