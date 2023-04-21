package system.cardEffects;

import datasource.CardType;
import datasource.I18n;
import system.GameManager;
import system.User;
import system.messages.EventMessage;

import java.util.List;
import java.util.function.Function;

import static system.Utils.forUsers;

public class TargetedAttackEffect extends UserTargetingEffect {

    public Function<User, Void> apply;

    @Override
    public void useEffect() {
        System.out.println("HELLO!");
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

    {
        apply = this::applyToUser;
    }
}
