package system.cardEffects;

public class ShuffleEffect extends EffectPattern {

    @Override
    public void useEffect() {
        Boolean shuffled = drawDeck.shuffle();
        currentState.shuffleDeck(shuffled);
    }
}
