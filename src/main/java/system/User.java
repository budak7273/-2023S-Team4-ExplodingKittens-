package system;


import datasource.CardType;

import datasource.Messages;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private Boolean alive = true;
    private ArrayList<Card> hand = new ArrayList<>();

    public User() {
        this.name = "";
        this.alive = true;
        this.hand = new ArrayList<>();
    }

    public User(final String playerName) {
        this.name = playerName;
    }

    public User(final String playerName,
                final boolean activeStatus,
                final ArrayList<Card> playerHand) {
        this.name = playerName;
        this.alive = activeStatus;
        ArrayList<Card> playerHandClone = new ArrayList<>();
        playerHandClone.addAll(playerHand);
        this.hand = playerHandClone;
    }

    public String getName() {

        return this.name;
    }

    public List<Card> getHand() {
        List<Card> toReturn = new ArrayList<>();
        toReturn.addAll(this.hand);
        return toReturn;
    }

    public void addCard(final Card drawnCard) {
        this.hand.add(drawnCard);
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void attemptToDie() {
        this.alive = false;
        int i;
        for (i=0; i<hand.size(); i++) {
            if (hand.get(i).getType() == CardType.DEFUSE) {
                this.alive = true;
                break;
            }
        }
        if (this.alive) {
            hand.remove(i);
        }
    }

    public boolean checkForSpecialEffectPotential() {
        return false;
    }

    public boolean verifyEffectForCardsSelected(final List<Integer> selected) {
        if (this.hand.isEmpty() && !selected.isEmpty()) {
            String msg = Messages.getMessage(Messages.EMPTY_HAND);
            throw new IllegalArgumentException(msg);
        }

        return false;
    }
}
