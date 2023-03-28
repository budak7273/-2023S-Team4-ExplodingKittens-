package system.cardEffects;

public class ShuffleEffect extends EffectPattern {

    @Override
    public void useEffect() {
        Boolean shuffled = getDrawDeck().shuffle();
        getGameManager().shuffleDeck(shuffled);
    }
}
