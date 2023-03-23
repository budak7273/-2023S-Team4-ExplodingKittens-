package system.cardEffects;

public class DrawFromBottomEffect extends EffectPattern {

    @Override
    public void useEffect() {
        boolean drawnExplodingKitten =
                drawDeck.drawFromBottomForUser(currentUser);
        currentState.checkExplodingKitten(drawnExplodingKitten);
        currentState.transitionToNextTurn();
    }
}
