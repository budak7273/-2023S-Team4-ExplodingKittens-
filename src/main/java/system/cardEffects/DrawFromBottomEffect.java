package system.cardEffects;

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
                String.format("%s played a Draw from the Bottom",
                              getCurrentUser().getName()),
                String.format("You used a Draw from the Bottom to draw a %s",
                              getCurrentUser().getLastCardInHand().getName())));
        if (drawnExplodingKitten) {
            gm.checkExplodingKitten();
        }
        gm.transitionToNextTurn();
    }
}
