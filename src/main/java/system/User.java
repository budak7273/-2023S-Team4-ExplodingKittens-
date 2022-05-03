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
        int feralCount = 0;
        int otherCount = 0;
        HashMap<String, Integer> list = new HashMap<>();

        for (Card card : this.hand) {
            if (card.getType() == CardType.FERAL_CAT) {
                feralCount++;
            } else if (card.isCatCard()) {
                otherCount++;
                String cname = card.getType().toString();
                int count = list.getOrDefault(cname, 0);
                list.put(cname, count + 1);
            }
        }

        if ((feralCount >= 1 && otherCount >= 1) || feralCount >= 2) {
            return true;
        } else if (otherCount < 2) {
            return false;
        } else {
            for (String cname : list.keySet()) {
                if (list.get(cname) > 1) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean verifyEffectForCardsSelected(final List<Integer> selected) {
        this.verifyCardsSelected(selected);
        Card first = hand.get(selected.get(0));

        for (Integer i : selected) {
            if (!hand.get(i).isCatCard()) {
                return false;
            }
            CardType type1 = first.getType();
            CardType type2 = hand.get(i).getType();
            if (type1 != type2 && type1 != CardType.FERAL_CAT
                    && type2 != CardType.FERAL_CAT) {
                return false;
            }
        }

        return true;
    }

    public void verifyCardsSelected(final List<Integer> selected) {
        if (selected == null) {
            throw new IllegalArgumentException(
                    "Null list should never happens.");
        }
        if (this.hand.isEmpty() && !selected.isEmpty()) {
            String msg = Messages.getMessage(Messages.EMPTY_HAND);
            throw new IllegalArgumentException(msg);
        }
        if (selected.size() > this.hand.size()) {
            throw new IllegalArgumentException(
                    "You should never select more number of cards than "
                            +
                            "what you have. Something is wrong.");
        }
        Set<Integer> set = new HashSet<>();
        set.addAll(selected);
        if (set.size() < selected.size()) {
            throw new IllegalArgumentException(
                    "You cannot select the same card more than once.");
        }
    }
}
