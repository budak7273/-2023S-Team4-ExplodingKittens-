package system.cardEffects;

public class AttackEffect extends EffectPattern {

    @Override
    public void useEffect() {
        currentState.transitionToNextTurn();
        currentState.addExtraTurn();
    }

}
