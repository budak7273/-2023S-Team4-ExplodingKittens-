package system.cardEffects;

import datasource.I18n;
import system.GameManager;
import system.messages.EventMessage;

import static system.Utils.forUsers;

public class DrawFromBottomEffect extends EffectPattern {

    @Override
    public void useEffect() {
        GameManager gm = getGameManager();
        boolean drawnExplodingKitten =
                getDrawDeck().drawFromBottomForUser(getCurrentUser());
        // TODO switch drawCard to return the card instead of wasExplodingKitten
        gm.postMessage(new EventMessage(
                forUsers(getCurrentUser()),
                String.format(I18n.getMessage("DrawFromBottomPublic"),
                              getCurrentUser().getName()),
                String.format(I18n.getMessage("DrawFromBottomPrivate"),
                              getCurrentUser().getLastCardInHand().getName())));
        if (drawnExplodingKitten) {
            gm.checkExplodingKitten();
        }
        gm.transitionToNextTurn();
    }
}
