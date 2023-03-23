package system.cardEffects;

public class DrawFromBottomEffect extends EffectPattern {

    @Override
    public void useEffect() {
        boolean drawnExplodingKitten =
                getDrawDeck().drawFromBottomForUser(getCurrentUser());
        getCurrentState().checkExplodingKitten(drawnExplodingKitten);
        getCurrentState().transitionToNextTurn();
    }
}
