package system.cardEffects;

import datasource.CardType;
import datasource.I18n;
import system.GameManager;
import system.User;
import system.messages.EventMessage;

import java.util.function.Function;

import static system.Utils.forUsers;

public class TargetedAttackEffect extends UserTargetingEffect {

    private final Function<User, Void> apply;

    @Override
    public void useEffect() {
        getGameManager().displayTargetSelectionPrompt(CardType.TARGETED_ATTACK, apply);
    }

    @Override
    public Void applyToUser(User target) {
        GameManager gm = getGameManager();
        User attacker = gm.getUserForCurrentTurn();
        gm.postMessage(new EventMessage(
                forUsers(target),
                String.format(I18n.getMessage("TargetedAttackPublic"), attacker.getName(), target.getName()),
                String.format(I18n.getMessage("TargetedAttackPrivate"), attacker.getName())));
        gm.transitionToTurnOfUser(target);
        getCurrentState().addExtraTurn();
        return null;
    }

    // Package-private for tests to be able to retrieve this with Checkstyle still appeased with it being private
    Function<User, Void> getApply() {
        return apply;
    }

    {
        apply = this::applyToUser;
    }
}
