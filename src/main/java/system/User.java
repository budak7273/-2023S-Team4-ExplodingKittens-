package system;


import datasource.CardType;
import datasource.Messages;

import java.util.*;

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
        this.hand = playerHand;
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

    public void attemptToDie() {
        this.alive = false;
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getType() == CardType.DEFUSE) {
                this.alive = true;
                hand.remove(i);
                break;
            }
        }
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
            for (Map.Entry<String, Integer> entry : list.entrySet()) {
                String cname = entry.getKey();
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
        String msg;
        if (selected == null) {
            msg = Messages.getMessage(Messages.MISSING_DATA);
            throw new IllegalArgumentException(msg);
        }
        if (this.hand.isEmpty() && !selected.isEmpty()) {
            msg = Messages.getMessage(Messages.EMPTY_HAND);
            throw new IllegalArgumentException(msg);
        }
        if (selected.size() > this.hand.size()) {
            msg = Messages.getMessage(Messages.BAD_CARD_SELECTION);
            throw new IllegalArgumentException(msg);
        }
        Set<Integer> set = new HashSet<>();
        set.addAll(selected);
        if (set.size() < selected.size()) {
            msg = Messages.getMessage(Messages.BAD_CARD_SELECTION);
            throw new IllegalArgumentException(msg);
        }
    }
}
