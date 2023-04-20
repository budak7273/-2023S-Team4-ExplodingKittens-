package system;


import datasource.CardType;
import datasource.I18n;

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

    public Card getCardFromHand(int index) {
        return this.hand.get(index);
    }

    public String getName() {
        return this.name;
    }

    public List<Card> getHand() {
        List<Card> toReturn = new ArrayList<>();
        toReturn.addAll(this.hand);
        return toReturn;
    }

    public Integer getHandCount() {
        return this.hand.size();
    }

    public void addCard(Card drawnCard) {
        this.hand.add(drawnCard);
    }

    public void removeCard(Card cardToRemove) {
        this.hand.remove(cardToRemove);
    }

    public Card getLastCardInHand() {
        return this.getHand().get(this.getHandCount() - 1);
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

    public boolean attemptToNope() {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getType() == CardType.NOPE) {
                hand.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean checkForCatCardEffects() {
        int feralCount = 0;
        int otherCatCount = 0;
        HashMap<String, Integer> catCardsInHand = new HashMap<>();

        for (Card card : this.hand) {
            if (card.getType() == CardType.FERAL_CAT) {
                feralCount++;
            } else if (card.isCatCard()) {
                otherCatCount++;
                String cname = card.getType().toString();
                int count = catCardsInHand.getOrDefault(cname, 0);
                catCardsInHand.put(cname, count + 1);
            }
        }

        return verifyCatCardEffectCount(feralCount, otherCatCount, catCardsInHand);
    }

    private boolean verifyCatCardEffectCount(int feralCount, int otherCatCount,
                                            HashMap<String, Integer> catCardsInHand) {
        if ((feralCount >= 1 && otherCatCount >= 1) || feralCount >= 2) {
            return true;
        } else if (otherCatCount < 2) {
            return false;
        } else {
            for (Map.Entry<String, Integer> entry : catCardsInHand.entrySet()) {
                String cname = entry.getKey();
                if (catCardsInHand.get(cname) > 1) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean verifyEffectForCardsSelected(final List<Card> selected) {
        this.verifyCardsSelected(selected);
        Card first = selected.get(0);

        for (Card c : selected) {
            if (!c.isCatCard()) {
                return false;
            }

            CardType type1 = first.getType();
            CardType type2 = c.getType();
            if (type1 != type2 && type1 != CardType.FERAL_CAT
                    && type2 != CardType.FERAL_CAT) {
                return false;
            }
        }

        return true;
    }

    public boolean isEmptyHand() {
        return this.hand.isEmpty();
    }

    public void verifyCardsSelected(final List<Card> selected) {
        if (selected == null) {
            throw new IllegalArgumentException(I18n.getMessage("MissingDataMessage"));
        }
        if (this.hand.isEmpty() && !selected.isEmpty()) {
            throw new IllegalArgumentException(I18n.getMessage("EmptyHandMessage"));
        }
        if (selected.size() > this.hand.size()) {
            throw new IllegalArgumentException(I18n.getMessage("BadCardSelectionMessage"));
        }
    }

    public boolean checkCatPairMatch(Card card1, Card card2) {
        return verifyEffectForCardsSelected(Arrays.asList(card1, card2));
    }
}
