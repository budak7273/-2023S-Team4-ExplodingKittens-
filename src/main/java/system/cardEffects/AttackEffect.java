package system.cardEffects;

public class AttackEffect extends EffectPattern {

    @Override
    public void useEffect() {
        getGameManager().executeAttack();
    }

}
