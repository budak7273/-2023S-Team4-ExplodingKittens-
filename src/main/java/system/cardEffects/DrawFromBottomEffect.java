package system.cardEffects;

public class DrawFromBottomEffect extends EffectPattern {

    @Override
    public void useEffect() {
        boolean drawnExplodingKitten =
                getDrawDeck().drawFromBottomForUser(getCurrentUser());
        if (drawnExplodingKitten) {
            getGameManager().checkExplodingKitten();
        }
        getGameManager().transitionToNextTurn();
    }
}
