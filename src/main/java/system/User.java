package system;


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

    public User(String playerName) {
        this.name = playerName;
    }

    public User(String playerName,
                boolean activeStatus,
                ArrayList<Card> playerHand) {
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

    public void addCard(Card drawnCard) {

        this.hand.add(drawnCard);
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void die() {
        this.alive = false;
    }

    public boolean checkForSpecialEffectPotential() {
        return false;
    }

    public boolean verifyEffectForCardsSelected(List<Integer> selected) {
        if (this.hand.isEmpty() && !selected.isEmpty()) {
            String msg = Messages.getMessage(Messages.EMPTY_HAND);
            throw new IllegalArgumentException(msg);
        }

        return false;
    }
}
