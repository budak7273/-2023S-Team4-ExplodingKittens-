package system.cardEffects;

public class SkipEffect extends EffectPattern {

    @Override
    public void useEffect() {
        getCurrentState().transitionToNextTurn();
    }
}
