package system;

public class SkipEffect implements EffectPattern {
    @Override
    public void useEffect(final GameState gameState) {
        gameState.transitionToNextTurn();
        gameState.transitionToNextTurn();
    }
}
